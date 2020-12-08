package HttpServer;
import Bean.Admin;
import Bean.AdminToken;
import Bean.News;
import Config.Cnf;
import Dao.DaoAdmin;
import Dao.DaoAdminToken;
import Dao.DaoFujian;
import Dao.DaoNews;
import com.alibaba.fastjson.JSONObject;
import utils.Annotation.FileUpload;
import utils.Annotation.GET;
import utils.Annotation.POST;
import utils.DataUtil;
import utils.Log;
import utils.TokenProccessor;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class ReponseApi {

    /**
     * 插入一条新闻
     * POST  /news/add
     */
    @POST(path = "/news/add")
    public static Object doInsertNews(Map data) {
        int isOk = -1;
        try {
            String n_type = data.get("typeId").toString(); //获取新闻类型代号
            String title = data.get("title").toString();
            String n_author = data.get("author").toString();
            String n_date = DataUtil.getDate();
            int looked_times = 0;
            String n_content = data.get("content").toString();
            String n_imgs = data.get("imgs").toString();
            String fu_jian = data.get("fujian").toString();
            News news = new News(n_type, title, n_author, n_date, looked_times, n_content, n_imgs, fu_jian);
            isOk = DaoNews.insert_news(news);  //插入数据库
        } catch (Exception e) {
            Log.e(e);
        }

        Map res_data = new HashMap();
        if (isOk > 0) {
            res_data.put("isOk", 1);
        } else {
            res_data = null;
        }


        return res_data;
    }


    /**
     * 删除单条新闻
     */
    @POST(path = "/news/delete")
    public static Object doDeleteNews(Map data){
        int del_id = Integer.parseInt(data.get("news_id").toString());  //获取需要删除的新闻的id

        int flag = DaoNews.delNewsById(del_id);

        if (flag>0){
            return "success";  //删除成功
        }else {
            return "failed";  //删除失败
        }

    }
    /**
     * 修改新闻请求
     */
    @POST(path = "/news/change")
    public static Object doChangeNews(Map data){

       int flag =  DaoNews.changeNews(data);

        if (flag>0){
            return "success";  //删除成功
        }else {
            return "failed";  //删除失败
        }
    }



    /**
     * 插入图片
     */

    @FileUpload(path = "/img/add", storeFolder =  Cnf.server_img_save_root )//"/data/prd/qs/res/imgs/")
    public static Object doInsertImgs(String localPath) {
        Map resp = null;
        //如果localPath不为null，说明文件已成功存储至本地
        if (localPath != null) {
            File tempFile = new File(localPath);  //根据路径创建文件对象
            resp = new HashMap() {{
                put("url",  DataUtil.generateUrl(tempFile, Cnf.IMG_ROOT_PATH));
                put("fileName", (tempFile.getName().split("_",2)[1]));
                put("id", DataUtil.insertFileRecordToDB(tempFile));
            }};
        }
        return resp;
    }

    /**
     * 插入附件
     */
    @FileUpload(path = "/file/add", storeFolder = Cnf.server_fujian_save_root) //Cnf.server_fujian_save_root
    public static Object doInsertFiles(String localPath) {
        Map resp = null;
        //如果localPath不为null，说明文件已成功存储至本地
        if (localPath != null) {
            File tempFile = new File(localPath);
            resp = new HashMap() {{
                put("url", DataUtil.generateUrl(tempFile,Cnf.FUJIAN_ROOT_PATH));
                put("fileName", tempFile.getName().split("_",2)[1]);
                put("id", DataUtil.insertFileRecordToDB(tempFile));
            }};
        }

        return resp;
    }

    /**
     * 获取新闻列表
     * GET /list
     * type = 1
     * page = 1
     *
     * @param params 请求参数集合
     */
    @GET(path = "/list")
    public static Object queryNewsByPage(Map params) {
        try {
            // 请求新闻类型
            int typeNum = Integer.parseInt(params.get("type").toString());

            // 请求页码
            String reqPage = params.get("page").toString();
            // 根据新闻类型和页码查询数据
            List<Map> newsList = DaoNews.queryNewsByPage(typeNum, Integer.parseInt(reqPage));
            //输出转换后的数据

            JSONObject resData = new JSONObject();
            resData.put("type", typeNum);
            List<JSONObject> data = DataUtil.mapListToJSONList(newsList,
                    new HashMap<String, String>() {{
                        put("id", "n_id");
                        put("title", "n_title");
                        put("date", "n_date");
                        put("looked_times", "looked_times");
                    }});
            resData.put("count", data.size());
            resData.put("list", data);
            return resData;
        } catch (Exception e) {
            Log.e(e);
        }
        return null;
    }

    /**
     * 请求首页
     * GET /
     */
    @GET(path = "/")
    public static Object queryHome(Map map) {
        try {

            Map res = DaoNews.queryForShouYe();  //查询首页新闻数据  xw:[{}...]  zc:[{},...]  gg: [{},...]

            //筛选出新闻列表 并把图片列表筛选
            List<Map> xwList_map = DataUtil.handleImgsOfNewsList((List<Map>) res.get("xw"));
            List<JSONObject> xwList = DataUtil.mapListToJSONList(xwList_map,new HashMap<String, String>(){{
                put("id", "n_id");
                put("title","n_title");
                put("imgs","n_imgs");
            }});


            //过滤政策类列表
            List<JSONObject> zcList = DataUtil.mapListToJSONList((List<Map>) (res.get("zc")),new HashMap<String, String>(){{
                put("id", "n_id");
                put("title", "n_title");
                put("content", "n_content");
            }});


            // 过滤公告类列表

            List<JSONObject> gg_list = DataUtil.mapListToJSONList((List<Map>) (res.get("hydt")),new HashMap<String, String>(){{
                put("id", "n_id");
                put("title", "n_title");
                put("date", "n_date");
            }});

            //结果合成输出
            return new JSONObject() {{
                put("xw", xwList);
                put("zc", zcList);
                put("hydt", gg_list);
            }};
        } catch (Exception e) {
            Log.e(e);
        }
        return null;
    }


    /**
     * 根据请求携带的新闻id获取完整的信息
     */
    @GET(path = "/content")
    public static Object queryNewsById(Map<String, String> params) {
        JSONObject res = null;
        try {
            String reqNewsId = params.get("id");  //请求的新闻id
            Map sql_data = DaoNews.queryById(reqNewsId);  //查询数据库
            res = DataUtil.mapTOJSON(sql_data,
                    new HashMap<String, String>() {{
                        put("id", "n_id");
                        put("type", "n_type");
                        put("title", "n_title");
                        put("author", "n_author");
                        put("date", "n_date");
                        put("looked_times", "looked_times");
                        put("content", "n_content");
                        put("imgs", "n_imgs");
                        put("fujian", "fu_jian");
                        put("type","n_type");
                    }});
        } catch (Exception e) {
            Log.e(e);
        }
        return res;
    }

    /**
     *根据type获取最新的一篇文章
     * @param map
     * @return
     */
    @GET(path = "/recentnews")
    public static Object queryNewsByType(Map<String,String> params){

        JSONObject res = null;
        try {
            String reqNewsType = params.get("type");  //获取请求的新闻类型
            Map sql_data = DaoNews.queryByType(reqNewsType);  //查询数据库
            res = DataUtil.mapTOJSON(sql_data,
                    new HashMap<String, String>() {{
                        put("id", "n_id");
                        put("type", "n_type");
                        put("title", "n_title");
                        put("author", "n_author");
                        put("date", "n_date");
                        put("looked_times", "looked_times");
                        put("content", "n_content");
                        put("imgs", "n_imgs");
                        put("fujian", "fu_jian");
                        put("type","n_type");
                    }});
        } catch (Exception e) {
            Log.e(e);
        }
        return res;
    }


    /**
     * 下载文件计数请求
     */
    @GET(path = "/file/count")
    public static Object fileDownLoadCount(Map map){
        int fileId = Integer.parseInt(map.get("id").toString());  //下载文件所属id
        int state = DaoFujian.downloadAddOnce(fileId);//下载次数+1
        String response = null;

        if (state>0){
            response = "isOK";
        }

        return response;
    }


    /**
     * 登录请求
     */
    @POST(path= "/manage/login")
    public static  Object doLogin(Map map){
        String  user = map.get("user").toString();
        String pwd = map.get("pwd").toString();

        JSONObject res = null;  //响应

        String token = null;
        String  true_pwd = null;
        long current_date = 0;


        Map admin = DaoAdmin.queryPwd(user);
        if (admin!=null){
            int admin_id =  (int)admin.get("id");
            true_pwd = admin.get("pwd").toString(); //查询数据库密码
            current_date = System.currentTimeMillis();

            if (pwd.equals(true_pwd)){
                res = new JSONObject();

                token = TokenProccessor.makeToken(pwd.concat(Long.toString(current_date))); //密码加当前时间生成token

                res.put("token",token);

                // 临时token 存储
                AdminToken adminToken = new AdminToken(admin_id,token,current_date);
                DaoAdminToken.insertToken(adminToken);
            }

        }



        return res;
    }

    /**
     *  check token
     * @param map
     * @return
     */
    @POST(path = "/token/check")
    public static Object doCheckToekn(Map map){

        return "token is right";
    }



    @POST(path = "/admin/pwd/update")
    public static Object updatePwd(Map map){

        String user = map.get("user").toString();
        String old_pwd = map.get("old_pwd").toString();
        String new_pwd = map.get("new_pwd").toString();

        String true_pwd = DaoAdmin.queryPwd(user).get("pwd").toString();  //根据用户名获得数据库的密码  密码一致方可修改

        int flag;
        if (old_pwd.equals(true_pwd)){
            flag = DaoAdmin.changePwd(user,new_pwd);
        }else {
            flag = -1;
        }

        return flag>0?"success":null;
    }





}