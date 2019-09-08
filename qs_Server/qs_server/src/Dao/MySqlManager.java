package Dao;

import Config.Cnf;
import utils.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * MySQL数据库管理连接
 */
public class MySqlManager {
    static {
        /**
         * 类加载时即加载数据库驱动
         */
        try {
            Class.forName(Cnf.MYSQL_JDBC_DRIVER);
        } catch (Exception e) {
            Log.e(e);
        }
    }

    private static Connection conn = null;

    /**
     * 获取与数据的连接对象
     */
    public static Connection connect() {
        if (conn == null) {
            synchronized (MySqlManager.class) {
                if (conn == null) {
                    try {
                        conn = DriverManager.getConnection(Cnf.MYSQL_URL,
                                Cnf.MYSQL_USER, Cnf.MYSQL_PASSWORD);
                    } catch (Exception e) {
                        Log.e(e);
                    }
                }
            }
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     */
    static void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                Log.e(e);
            }
        }
    }
}