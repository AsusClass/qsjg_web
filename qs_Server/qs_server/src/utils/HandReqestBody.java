package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class HandReqestBody {

    /**
     * 获取body请求体数据
     * 以
     */

    public static Map getJsonData(InputStream req) {
        Map jsonData = new HashMap();
        StringBuilder sb = new StringBuilder();
        try {
            //提取post请求体中的参数
            InputStreamReader inputStreamReader = new InputStreamReader(req); //输入流读取对象
            BufferedReader reader = new BufferedReader(inputStreamReader); //读取工具
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                sb.append(line);


            }
            System.out.println(sb.toString());
            //将提取的参数转换成K-V
            //name=peter&age=1
            jsonData = HandleRootPath.getParmsFromPath(new URI("?".concat(sb.toString())));
        } catch (Exception e) {
            Log.e(e);
        }

        return jsonData;
    }


    /**处理 文件传输 form-data
     *
     */
    public static HashMap<String, String> getFormData(InputStream req){

     String[] sb = new String[10000];
        try {
            //提取post请求体中的参数
            InputStreamReader inputStreamReader = new InputStreamReader(req); //输入流读取对象
            BufferedReader reader = new BufferedReader(inputStreamReader); //读取工具
            String line;

            int count = 0;
            while ((line = reader.readLine()) != null) {

                count ++;
                sb[count-1] = line;
                System.out.println(line);

                if(count==4){
                    break;
                }
            }




        } catch (Exception e) {
            Log.e(e);
        }


     // 分离参数
        String Key = sb[1].split(";")[1].split("=")[1].replaceAll("\"","").replace("\n","").replace("\r","");
        String value = sb[3].replace("\n","").replace("\r","");
        System.out.print("start");
        System.out.print(Key);
        System.out.print(value);
        System.out.print("end");
        HashMap res = new HashMap<String,String>();
        res.put(Key,value);
        return res;

    }
}
