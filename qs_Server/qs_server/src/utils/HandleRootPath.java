package utils;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class HandleRootPath {

    /**
     * 获取请求路径的参数
     * 字典形式返回
     */
    public static Map getParmsFromPath(URI uri) {
        Map params = new HashMap();
        try {

            String data = uri.getQuery();//提取？号后面的参数内容
            if (data==null){
                return params;
            }
            //提取K=V集合
            String[] keyAndValue = data.split("&");
            //循环取出K-V
            for (String item : keyAndValue) {
                String[] key_value = DataUtil.splitByDengHao(item);
                if (key_value.length == 2) {
                    params.put(key_value[0], key_value[1]);
                }
            }
        } catch (Exception e) {
            Log.e(e);
        }
        return params;
    }




}
