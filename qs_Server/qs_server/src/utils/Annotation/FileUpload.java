package utils.Annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FileUpload {

    //上传请求路径
    String path();

    //本地存储目录
    String storeFolder();

}