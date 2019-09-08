package utils.Annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * http的GET请求注解
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface GET {
    //请求路由
    String path();
}
