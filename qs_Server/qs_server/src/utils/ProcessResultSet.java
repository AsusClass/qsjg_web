package utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessResultSet {


    public static List getDataList(ResultSet rs) {
        /*
        将数据集 解析成字典列表
         */
        List list = new ArrayList();
        try {
            ResultSetMetaData md = rs.getMetaData();  //获取键名
            int columnCount = md.getColumnCount();//获取列的数量

            while (rs.next()) {
                Map rowData = new HashMap();//声明Map
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
                }
                list.add(rowData);
            }
        } catch (Exception e) {
            Log.e(e);
        }
        return list;
    }

}
