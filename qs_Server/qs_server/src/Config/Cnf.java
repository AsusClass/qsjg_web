package Config;

/**
 * 全局配置项
 */
public class Cnf {
    /**
     * 服务器启动监听的ip地址
     */
    public final static String BIND_ADDRESS = "127.0.0.1";

    /**
     * 服务器启动监听端口 ，可选[5555，8080]
     */
    public final static int BIND_PORT = 1234;

    /**
     * 全局debug模式标记，可根据此选项控制日志输出等
     */
    public final static boolean DEBUG = true;

    /**
     * MySQL用户账户
     */
    public final static String MYSQL_USER = "root";

    /**
     * MySQL用户密码
     */
    public final static String MYSQL_PASSWORD = "root";

    /**
     * MySQL连接地址（可直连服务器数据库）
     */
    public final static String MYSQL_URL = "jdbc:mysql://47.100.192.151/qs?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT&useSSL=false";

    /**
     * MySQL驱动
     */
    public final static String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * nginx图片路径
     */

    public final static String IMG_ROOT_PATH = "/qs/imgs/";

    /**
     * nginx附件路径
     */

    public final static String FUJIAN_ROOT_PATH = "/qs/files/";

    /**
     * 服务器本地图片保存路径
     */
    public final static String server_img_save_root = "/data/prd/qs/res/imgs/";

    /**
     * 本地测试图片保存路径
     */
    public final static  String test_img_save_root="/Users/lichongxi/code/java/lqlWebProject/lqlServer/test/imgs/";
}