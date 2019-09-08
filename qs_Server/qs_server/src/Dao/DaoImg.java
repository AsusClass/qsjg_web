package Dao;

import Bean.Img;
import utils.HandleSQL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DaoImg {
    public static Connection connection = MySqlManager.connect();


    /**
     * 插入一条记录
     */
    public static int insert(Img img) {
        int flag = -1;
        String sql = String.format("insert into img values(0,'%s','%s','%s',%f,'%s')",
                img.getExt(),
                img.getName(),
                img.getUrl(),
                img.getSize(),
                img.getDate()
        );

        try {
            flag = HandleSQL.Update(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return (int) HandleSQL.queryLateRecord("img").get("id");
    }


    /**
     * 根据id查询
     * 返回 Map
     */
    public static Map queryById(int id) {

        Map resData;
        String sql = String.format("select * from img where id = %d", id);

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
     * 查询最近的一条记录  id 最大
     */
    public static Map queryLateImg(){
        String sql = "select * from img order by id desc limit 0,1";
        List<Map> datas = HandleSQL.QueryUseSql(sql);

        return datas.get(0);
    }


}
