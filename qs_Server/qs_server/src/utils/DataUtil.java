package utils;
import Bean.Fujian;
import Bean.Img;
import Dao.DaoFujian;
import Dao.DaoImg;
import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.util.*;

/**
 * 数据处理工具
 */
public class DataUtil {
    /**
     * 集合映射 --- 修改映射的键
     *
     * @param src    待映射的集合输入
     * @param mapReg 映射规则
     */
    public static List<JSONObject> mapListToJSONList(List<Map> src,
                                                     Map<String, String> mapReg) {
        List<JSONObject> resultSets = new ArrayList<>();
        for (Map item : src) {
            JSONObject jsonObject = new JSONObject();
            for (Object o : mapReg.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                jsonObject.put(entry.getKey().toString(), item.get(entry.getValue()));
            }
            resultSets.add(jsonObject);
        }
        return resultSets;
    }


    /**
     * 字典映射  字典转JSON
     */
    public static JSONObject mapTOJSON(Map src, Map<String, String> mapReg) {

        JSONObject resultSets = new JSONObject();
        for (Object o : mapReg.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            resultSets.put(entry.getKey().toString(), src.get(entry.getValue()));
        }

        return resultSets;
    }


    /**
     * 列表字符串转列表  "[1,2,3]" ->  [1,2,3]
     */


    public static List<Integer> str2list(String str) {
        List resData = new ArrayList();

        str = str.replace("[", "");
        str = str.replace("]", "");
        if (str.equals("")){
            return resData;
        }
        String[] sts = str.split(",");

        for (String i : sts) {
            resData.add(Integer.parseInt(i));
        }

        return resData;
    }

    /**
     * 关闭所有流，防止内存泄漏
     */
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            Log.e(e);
        }
    }


    /**
     * 获取当前日期 年 月 日 分 秒
     *
     * @return String
     */
    public static String getDate() {
        int y, m, d, h, mi, s;
        Calendar cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH) + 1;
        d = cal.get(Calendar.DATE);
        h = cal.get(Calendar.HOUR_OF_DAY);
        mi = cal.get(Calendar.MINUTE);
        s = cal.get(Calendar.SECOND);


        return y + "年" + m + "月" + d + "日" + h + "时" + mi + "分" + s + "秒";
    }


    /**
     * 判断扩展名是否图片
     */
    public static boolean isImg(String ext) {
        boolean is_img = false;
        String[] img_exts = {"jpg", "png", "gif","bmp","gif","jpeg"}; //图片扩展名
        ext = ext.trim().toLowerCase();

        if (Arrays.asList(img_exts).contains(ext)) { //文件是图片
            is_img = true;
        }

        return is_img;
    }


    /**
     * 按第一个= 把字符串分为两部分
     * @param str 输入字符串
     * @return  字符串数组
     */
    public static String[] splitByDengHao(String str){

        int deng_index = 0;

        deng_index = str.indexOf("="); //第一次出现=号的位置

        char[] str_char = str.toCharArray();
        char[] key = new char[deng_index];
        char[] value = new char[str_char.length-deng_index-1];
        System.arraycopy(str_char,0,key,0,key.length);
        System.arraycopy(str_char,deng_index+1,value,0,value.length);

        String[] res = new String[2];
        res[0] = String.valueOf(key);
        res[1] = String.valueOf(value);

        return res;
    }


    /**
     *将新闻列表中的图片字段简化(不需要这么多)
     */
    public static List handleImgsOfNewsList(List<Map> sqlData){

        for(int j=0;j<sqlData.size();j++){  //遍历新闻列表

            List<JSONObject> newImgs = mapListToJSONList((List<Map>) (sqlData.get(j).get("n_imgs")),new HashMap<String, String>(){
                {
                    put("name", "name");
                    put("url", "url");
                    put("id","id");
                }
            });

            sqlData.get(j).put("n_imgs", newImgs);  //替换成筛选后的图片列表
        }

        return sqlData;
    }



    /**
     * 根据服务器本地存储的路径生成url
     *
     * @param tempFile 待处理的文件
     * @param root  文件(包括图片)的Nigixs映射路径
     * @return 返回映射的url
     */
    public static String generateUrl(File tempFile,String root) {
        return root.concat(tempFile.getName());
    }





    /**
     * 根据现有本地文件，在数据库中生成一条纪录，并返回该纪录的id
     *
     * @param tempFile 待生成纪录的文件
     * @return 操作成功的文件在数据库中id
     */
    public static int insertFileRecordToDB(File tempFile) {
        System.out.println(tempFile.getName());
        System.out.println(tempFile.getName().split("_",2)[1]);
        String fileExt = FileUtil.getExt(tempFile.getName());
        int flag =-1; //插入成功标记
        if (DataUtil.isImg(fileExt)){  //文件是图片
            Img img = new Img(fileExt,
                    tempFile.getName().split("_",2)[1],

                    DataUtil.generateUrl(tempFile,"/qs/imgs/"),
                    tempFile.length(),
                    DataUtil.getDate()
            );
            flag = DaoImg.insert(img);

        }else {  //文件是文档
            Fujian fujian = new Fujian(tempFile.getName().split("_",2)[1],
                    fileExt,
                    DataUtil.generateUrl(tempFile,"/qs/files/"),
                    tempFile.length(),
                    fileExt,
                    0,
                    DataUtil.getDate()

            );
            flag =  DaoFujian.insert(fujian);
        }


        return flag;
    }


}
