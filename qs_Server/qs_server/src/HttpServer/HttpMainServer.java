package HttpServer;

import Config.Cnf;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;
import utils.Annotation.AnnotationTools;
import utils.Log;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HttpMainServer implements HttpHandler {

    public static void main(String[] args) {
        HttpServerProvider provider = HttpServerProvider.provider();
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(Cnf.BIND_ADDRESS, Cnf.BIND_PORT);//监听端口
            HttpServer httpServer = provider.createHttpServer(socketAddress, 100);
            httpServer.createContext("/", new HttpMainServer()); //设置路径
            httpServer.setExecutor(Executors.newCachedThreadPool());
            httpServer.start();
            //初始化注解工具
            AnnotationTools.setup(HttpMainServer.class);
            Log.i(null,
                    String.format("服务器已启动，绑定IP地址:%s,监听端口:%s",
                            Cnf.BIND_ADDRESS, Cnf.BIND_PORT));
        } catch (Exception e) {
            Log.e(e);
        }
    }

    @Override
    public void handle(HttpExchange httpExchange) {
        //路由分发
        Router.dispatch(httpExchange);
    }
}