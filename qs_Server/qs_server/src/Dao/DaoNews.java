package Dao;

import Bean.News;
import utils.DataUtil;
import utils.HandleSQL;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoNews {

    public static Connection connection = MySqlManager.connect(); //获取一个连接对象

    /**
     * 插入一条新闻数据
     */
    public static int insert_news(News news) {

        String sql = String.format("insert into news values(0,'%s','%s','%s','%s',%d,'%s','%s','%s')",
                news.getN_type(),
                news.getN_title(),
                news.getN_author(),
                news.getN_date(),
                news.getLooked_times(),
                news.getN_content(),
                news.getN_imgs(),
                news.getFu_jian());

        show_message("插入数据到news表...");
        show_message(sql);
        int flag ; //插入成功与否
        flag = HandleSQL.Update(sql);
        if (flag != 0) {
            show_message("插入数据成功！！");
        } else {
            show_message("插入失败！！");
        }

        return flag;
    }

    /**
     * 根据id删除新闻
     */
    public static int delNewsById(int del_id){
        int flag;
        String sql = String.format("delete from news where n_id=%d", del_id);
        flag = HandleSQL.Update(sql);

        return  flag;
    }
    /**
     * 修改新闻数据
     */

    public static  int changeNews(Map change_info){
        int change_id = Integer.parseInt(change_info.get("news_id").toString());  //待修改新闻id
        String type = change_info.get("type").toString();
        String title = change_info.get("title").toString();
        String author = change_info.get("author").toString();
        String date = change_info.get("date").toString();

        String sql = String.format("update news set n_type='%s',n_title='%s',n_author='%s',n_date='%s' where n_id=%d",
                type,title,author,date,change_id);
        System.out.println(sql);
        return  HandleSQL.Update(sql);
    }



    /**
     * 首页  查询4条最近的新闻3 和两条最近法政策法规类 2  and 8条最新的行业动态(type=8)
     */
    public static Map queryForShouYe() {

        Map res_Data = new HashMap();  //返回三条新闻列表  和两条政策列表

        String sql_xw = "select * from news where n_type = '3' order by n_id desc limit 0,4";  //查询新插入的三条新闻
        String sql_zc = "select * from news where n_type = '2' order by n_id  desc limit 0,2";  //查询新插入的两条政策法规数据
        String sql_gg = "select * from news where n_type = '8' order by n_id  desc limit 0,8";    //查询新插入的8条公告公示数据
        System.out.println("查询新插入的三条新闻".concat(sql_xw));
        try {
            List<Map> xw_list;
            List<Map> zc_list;
            List<Map> gg_list;

            xw_list = HandleSQL.QueryUseSql(sql_xw);
            zc_list = HandleSQL.QueryUseSql(sql_zc);
            gg_list = HandleSQL.QueryUseSql(sql_gg);

            xw_list = newsList2usefulData(xw_list); // 把新闻中的图片编号列表转为Map字典列表
            zc_list = newsList2usefulData(zc_list);
            gg_list = newsList2usefulData(gg_list);

            res_Data.put("xw", xw_list);
            res_Data.put("zc", zc_list);
            res_Data.put("hydt", gg_list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res_Data;
    }

    /**
     * 查询指定类型数据的第几页 type:不分类0  公司简介 1   政策法规 2 新闻动态 3    公告发布 5   人才招聘 6  联系我们 7
     */
    public static List<Map> queryNewsByPage(int type, int page) {  //每页10条


        List<Map> res = new ArrayList();  //返回数据列表
        int startRow = (page - 1) * 15;  //查询数据的开始行数 0开始
        String sql;
        if (type==0){  //0表示不分类
            sql = String.format("select * from news  order by n_id desc limit  %d,%d", startRow, 15);
        }else {
            sql = String.format("select * from news where n_type='%s' order by n_id desc limit %d,%d",
                    Integer.toString(type), startRow, 10);
        }
        show_message("查询指定页数据...");
        show_message(sql);

        try {
            res = HandleSQL.QueryUseSql(sql);
        } catch (Exception e) {
            show_message("查询指定页数据Error:" + e.toString());
        }

        return res;
    }


    /**
     * 将新闻列表 的imgs字段全替换成img数据  fu_jian编号列表转为字典列表
     * imgs:[1,2,3]  -> imgs:[{name:xx,url:xx},...]
     */
    public static List<Map> newsList2usefulData(List<Map> News) {
        List<Map> res = new ArrayList<>();

        for (Map item : News) { //遍历每条新闻
            List imgIds = DataUtil.str2list(item.get("n_imgs").toString());
            List imgs;
            List fujians;
            if (imgIds.size()==0){
                 imgs = new ArrayList(); //图数据
            }else {
                 imgs = DaoImg.queryByIds(imgIds); //图数据
            }

            List fujianIds = DataUtil.str2list(item.get("fu_jian").toString());
            if (fujianIds.size()==0){
                fujians=new ArrayList();
            }else {
                fujians = DaoFujian.queryByIds(fujianIds); //附件
            }


            item.put("n_imgs", imgs);
            item.put("fu_jian", fujians);

            res.add(item);
        }
        return res;
    }


    /**
     * 通过新闻id查询
     */
    public static Map queryById(String news_id) {


        Map res = new HashMap();
        String sql = String.format("select * from news where n_id='%s'", news_id);
        try {
            List<Map> news_list = HandleSQL.QueryUseSql(sql);
            if (news_list.isEmpty()){
                return  res;
            }
            int id = Integer.parseInt(news_list.get(0).get("n_id").toString());
            int look_times = Integer.parseInt(news_list.get(0).get("looked_times").toString());
            look_times++;
            update(id,"looked_times",look_times);
            res = newsList2usefulData(news_list).get(0);  //查询新闻详情
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
    /**
     * 通过type查询最新的一篇
     */
    public static Map queryByType(String type){
        Map res = new HashMap();
        String sql = String.format("select * from news where n_type='%s' order by n_id desc limit 1", type);
        try {
            List<Map> news_list = HandleSQL.QueryUseSql(sql);
            if (news_list.isEmpty()){
                return  res;
            }
            int id = Integer.parseInt(news_list.get(0).get("n_id").toString());
            int look_time = Integer.parseInt(news_list.get(0).get("looked_times").toString());
            look_time++;
            update(id,"looked_times",look_time);
            res = newsList2usefulData(news_list).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;

    }




    /**
     * 根据id修改某个字段
     */
    public static  int update(int id , String key,Object value){
        int flag = -1;
        String sql;
        if (value.getClass().toString().equals("String")){
            sql = String.format("update news set %s = '%s' where n_id = %d",key,value,id);
        }else {
            sql = String.format("update news set %s = %d where n_id = %d",key,value,id);
        }
        flag = HandleSQL.Update(sql);

        return  flag;
    }


    /**
     * 打印...
     */
    public static void show_message(String message) {
        System.out.println(message);
    }
}
