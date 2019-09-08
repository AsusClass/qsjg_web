package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import Dao.DaoAdminToken;
import sun.misc.BASE64Encoder;

/**
 * 生成Token的工具类
 * @author zhous
 * @since 2018-2-23 13:59:27
 *
 */
public class TokenProccessor {



    /**
     * 生成Token
     * @return
     */
    public static String makeToken(String token) {

        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] =  md.digest(token.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  验证token 合法性
     * @param req_token
     * @return
     */
    public static boolean checkRequsetToken(String req_token){

        Map queryToekn = DaoAdminToken.queryToken(req_token);  // 根据请求携带的token 查询临时token表
        String sql_token = queryToekn.getOrDefault("token", "not exist").toString(); // 查询数据库token结果
        long token_date = Long.parseLong(queryToekn.getOrDefault("date", 0).toString());  //token 时间
        long current_date = System.currentTimeMillis();  //当前时间
        long token_life = current_date - token_date;  // token 生命周期

        return (req_token.equals(sql_token) && token_life <= 86400000);  //token 存在 且 创建时间在1天以内
    }




}
