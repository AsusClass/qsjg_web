package Dao;

import Bean.AdminToken;
import utils.HandleSQL;
import utils.ProcessResultSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DaoAdminToken {

    /**
     * 插入token  返回1 0
     * @param token
     * @return
     */
    public static int insertToken(AdminToken token){

        deleteToken(token.getU_id()); // 删除之前的token

        String sql = String.format("insert into admin_token values(0, %d, '%s', %d)",
                token.getU_id(), token.getToken(), token.getDate());
        System.out.println("插入临时token------"+sql);
        return  HandleSQL.Update(sql);


    }








    /**
     * 查询token 是否存在  返回该条记录字典  没有则返回空字典
     * @param token
     * @return
     */
    public static Map queryToken(String token){
        List<Map> res;
        String sql = String.format("select * from admin_token where token='%s'", token);

        res = HandleSQL.QueryUseSql(sql);

        if (res.size() == 0) return new HashMap();  //查询为空


        return  res.get(0);   // 查询到了token  返回该条记录
    }


    public static int deleteToken(int u_id){

        String sql = String.format("delete from admin_token where u_id=%d",u_id);
        return HandleSQL.Update(sql);
    }




}
