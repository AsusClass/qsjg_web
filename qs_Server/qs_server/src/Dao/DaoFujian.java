package Dao;

import Bean.Fujian;
import utils.HandleSQL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoFujian {

    /**
     * 插入一条附件记录
     * 返回插入记录的id
     */
    public static int insert(Fujian fujian) {
        int flag = -1;
        String sql = String.format("insert into fujian values(0,'%s','%s','%s',%f,'%s',%d,'%s')",
                fujian.getName(),
                fujian.getExt(),
                fujian.getUrl(),
                fujian.getSize(),
                fujian.getType(),
                fujian.getDownload_times(),
                fujian.getDate()
        );
        flag = HandleSQL.Update(sql);
        if (flag == -1) {
            return -1;
        }
        Map lastRecord = HandleSQL.queryLateRecord("fujian");
        return lastRecord != null ? (int) lastRecord.get("id") : -1;
    }


    /**
     * 根据id查询
     * 返回 Map
     */
    public static Map queryById(int id) {

        Map resData = new HashMap();
        String sql = String.format("select * from fujian where id = %d", id);

        resData = HandleSQL.QueryUseSql( sql).get(0);


        return resData;
    }


    /**
     * 根据多个id查询
     * 返回一个List<Map>
     */
    public static List<Map> queryByIds(List<Integer> ids) {
        List<Map> resData = new ArrayList<>();

        for (int id : ids) {
            Map imgData = queryById(id);
            resData.add(imgData);
        }

        return resData;
    }

    /**
     * 根据id下载次数加1
     */
    public  static  int downloadAddOnce(int id){
        int flag = -1;  //操作成功标记

        Map file = queryById(id); //文件
        int downloadTimes = Integer.parseInt(file.get("download_times").toString()) + 1;//下载次数+1
        String sql = String.format("update fujian set download_times = %d where id = %d",downloadTimes,id);
        flag = HandleSQL.Update(sql);
        return flag;
    }
}
