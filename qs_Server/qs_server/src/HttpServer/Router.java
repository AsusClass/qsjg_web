package HttpServer;

import Bean.AdminToken;
import Bean.ResultWrapper;
import Dao.DaoAdminToken;
import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import utils.Annotation.FileUpload;
import utils.*;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局路由管理
 * <p>
 * 绑定路由与具体业务代码
 * <p>
 * 例如:
 * 绑定get请求访问路由/xxx，跳转执行 aaa.c方法
 * Router.get("/xxx",aaa.class,"c")
 * <p>
 * 分发具体路由请求到业务代码，内部会区分请求方式，获取请求参数，进行结果调用
 * Router.dispatch(httpExchange)
 */
@SuppressWarnings("unchecked")
public class Router {
    private final static String REQ_GET = "get";
    private final static String REQ_POST = "post";

    private static HashMap<String, Method> routers = new HashMap<>();

    /**
     * 绑定http中get请求路由
     *
     * @param path   路由路径
     * @param method 业务处理代码具体方法
     */
    public static void get(String path, Method method) {
        routers.put(REQ_GET.concat(path), method);
    }

    /**
     * 绑定http中post请求路由
     *
     * @param path   路由路径
     * @param method 业务处理代码具体方法
     */
    public static void post(String path, Method method) {
        routers.put(REQ_POST.concat(path), method);
    }

    /**
     * 分发请求进行处理
     */
    static void dispatch(HttpExchange httpExchange) {
        // 拦截头部信息，填充统一头部数据
        interceptHead(httpExchange);
        // 获取访问的地址
        URI requestUrl = httpExchange.getRequestURI();
        // 获取路由
        String reqPath = requestUrl.getPath();
        // 获取请求方法 [get,post]
        String reqMethod = httpExchange.getRequestMethod().toLowerCase();
        // 获取路由+请求方法
        String routerKey = reqMethod.concat(reqPath);
        //标记是否是文件上传
        boolean isFileUpload = false;

        if (routers.containsKey(routerKey)) {

            //提取路由中缓存的业务方法
            Method method = routers.get(routerKey);
            //临时K-V存储请求参数
            Map params = new HashMap<>();


            //提取请求的信息
           InputStream http_body = httpExchange.getRequestBody();
            if (reqMethod.equals(REQ_GET)) { // GET请求
                //提取GET路由中携带的参数
                //abc.com?name=pater
                params = HandleRootPath.getParmsFromPath(requestUrl);
            } else if (reqMethod.equals(REQ_POST)) { //POST请求
                //针对post请求标记是表单还是文件传输
                isFileUpload = !isFormRequest(httpExchange.getRequestHeaders().getFirst("Content-type"));
                if (!isFileUpload) {
                    //如果是表单请求，提取POST请求中表单数据
                    params = HandReqestBody.getJsonData(http_body);



                }
            }

            String req_token = params.getOrDefault("token","not exist").toString();  // post请求携带的token




                try {
                    if (!isFileUpload) { //表单数据提交
                        if (reqMethod.equals(REQ_GET) | requestUrl.toString().equals("/manage/login") | TokenProccessor.checkRequsetToken(req_token)) {
                            //获取业务代码处理的结果
                            Object result = method.invoke(method.getDeclaringClass(), params);
                            //填充待返回的数据模型
                            ResultWrapper res = new ResultWrapper(result == null ? 400 : 200, result);
                            //响应处理结果
                            sendData(httpExchange, JSON.toJSONString(res));
                        }else {
                            sendData(httpExchange, JSON.toJSONString(new ResultWrapper(402, null)));  //无token 非法操作
                        }
                    } else { //处理文件上传
                        //获取文件上传业务处理注解
                        FileUpload fileUpload = method.getAnnotation(FileUpload.class);
                        //保存表单文件至本地
                        File storeFile = FileUtil.storeUploadFile(http_body, fileUpload.storeFolder());
                        //临时变量保存即将返回给前端的数据
                        Object resp = null;
                        //storeFile不为空，表示文件在本地存储成功
                        if (storeFile != null) {

                            resp = method.invoke(method.getDeclaringClass(), storeFile.getPath()); //保存到本地后准备响应数据
                        }
                        sendData(httpExchange, JSON.toJSONString(new ResultWrapper(resp == null ? 400 : 200, resp))); // 响应400可能是token出错 或者保存失败
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    Log.e(e);
                }





        } else {
            //路由不存在，响应404
            sendData(httpExchange, JSON.toJSONString(new ResultWrapper(404, null)));
        }
    }

    /**
     * 判断post请求是否是表单还是文件
     *
     * @param contentType http协议的Content-Type
     */
    private static boolean isFormRequest(String contentType) {
        return !contentType.contains("multipart/form-data");
    }

    /**
     * 拦截头部信息，填充需要的数据
     *
     * @param httpExchange 数据通道
     */
    private static void interceptHead(HttpExchange httpExchange) {
        //获取响应头
        Headers responseHeder = httpExchange.getResponseHeaders();
        //设置跨域
        responseHeder.set("Access-Control-Allow-Origin", "*");
        //头部设置数据格式
        responseHeder.set("Content-Type", "application/json;charset=utf-8");
        responseHeder.set("Access-Control-Allow-Headers", "Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE");

    }

    /**
     * 发送数据
     */
    private static void sendData(HttpExchange httpExchange, String response) {
        OutputStream responseBody = null;
        OutputStreamWriter writer = null;
        try {
            //获取响应数据的长度
            int contentLength = response.getBytes(StandardCharsets.UTF_8).length;
            //设置响应头部
            httpExchange.sendResponseHeaders(200, contentLength);
            //获取http请求输出流对象
            responseBody = httpExchange.getResponseBody();
            //创建输出流写入对象
            writer = new OutputStreamWriter(responseBody, StandardCharsets.UTF_8);
            //将响应内容写入输出流writer
            writer.write(response);
        } catch (Exception e) {
            Log.e(e);
        } finally {
            //关闭所有相关流
            DataUtil.close(writer);
            DataUtil.close(responseBody);
        }
    }

}