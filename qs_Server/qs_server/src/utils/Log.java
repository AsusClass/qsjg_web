package utils;

import Config.Cnf;

/**
 * 全局日志输出管理
 * <p>
 * 例如:
 * Log.i("xxx类名","这是数据");
 */
public class Log {

    /**
     * 输出日志信息，追踪运行过程中的数据
     *
     * @param tag 输出标记，辅助日志跟踪
     * @param msg 输出详细内容
     */
    public static void i(String tag, String msg) {
        if (Cnf.DEBUG) {
            if (tag == null || tag.isEmpty()) {
                log(msg);
            } else {
                log(String.format("%s-->%s", tag, msg));
            }
        }
    }

    /**
     * 输出异常日志数据
     *
     * @param e 异常信息
     */
    public static void e(Exception e) {
        i(null, e.getMessage());
    }

    /**
     * 日志输出至终端
     *
     * @param msg 已格式化的待输出日志
     */
    private static void log(String msg) {
        System.out.println(msg);
    }
}