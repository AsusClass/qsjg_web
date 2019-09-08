package utils.Annotation;


import HttpServer.Router;
import utils.ClassUtil;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 处理自定义的Http请求路由注解
 */
public class AnnotationTools {
    public static void setup(Class clazz) {
        //扫描包下所有类
        List<Class<?>> clazzs = ClassUtil.queryAllClassByPackage(clazz.getPackage());
        for (Class c : clazzs) {
            //获取类下的所有方法
            Method[] methods = c.getDeclaredMethods();
            for (Method m : methods) {
                //判定是否存在指定的注解
                if (m.isAnnotationPresent(GET.class)) {
                    //设置访问权限
                    m.setAccessible(true);
                    //获取方法上自定义的注解
                    GET get = m.getAnnotation(GET.class);
                    //拿到注解的数据，绑定路由
                    Router.get(get.path(), m);
                } else if (m.isAnnotationPresent(FileUpload.class)) {
                    m.setAccessible(true);
                    FileUpload post = m.getAnnotation(FileUpload.class);
                    Router.post(post.path(), m);
                } else if (m.isAnnotationPresent(POST.class)) {
                    //POST注解
                    m.setAccessible(true);
                    POST post = m.getAnnotation(POST.class);
                    Router.post(post.path(), m);
                }
            }
        }
    }
}