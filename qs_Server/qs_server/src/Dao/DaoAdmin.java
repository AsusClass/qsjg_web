package Dao;

import Bean.Admin;
import utils.HandleSQL;

import java.util.List;
import java.util.Map;

public class DaoAdmin {
    public static  int insert(Admin admin){

        String sql = String.format("insert into admin values(0,'%s','%s')",
                admin.getUser(),admin.getPwd());

        return HandleSQL.Update(sql);
    }



    public static  Map queryPwd(String user){
        String sql = String.format("select * from admin where user='%s'",user);
        List<Map> res = HandleSQL.QueryUseSql(sql);

        return res.size()>0?res.get(0):null;
    }


    public static int changePwd(String user, String pwd){
        String sql = String.format("update admin set pwd='%s' where user='%s'", pwd,user);
        System.out.println(sql);
        return  HandleSQL.Update(sql);
    }

}
