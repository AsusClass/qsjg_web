package utils;

import Dao.MySqlManager;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HandleSQL {

    /**
     * 查询：
     *
     * @param sql 查询sql
     */

    public static List<Map> QueryUseSql(String sql) {
        List<Map> res = new ArrayList<>();
        try {
            Statement statement = MySqlManager.connect().createStatement();
            res = ProcessResultSet.getDataList(statement.executeQuery(sql)); //结果集转成List<Map>
        } catch (Exception e) {
            Log.e(e);
        }
        return res;
    }

    /**
     * 查询最新一条记录
     *
     * @param table 表名
     */
    public static Map queryLateRecord(String table) {
        Map resData = null;
        try {
            String sql = String.format("select * from %s order by id desc limit 0,1", table);
            resData = HandleSQL.QueryUseSql(sql).get(0);
        } catch (Exception e) {
            Log.e(e);
        }
        return resData;
    }

    /**
     * 更新数据库  增 改 删
     */
    public static int Update(String sql) {
        int flag = -1;
        try {
            Statement statement = MySqlManager.connect().createStatement();
            flag = statement.executeUpdate(sql);
        } catch (Exception e) {
            Log.e(e);
        }
        return flag;
    }

}
