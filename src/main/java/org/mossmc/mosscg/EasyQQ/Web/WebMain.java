package org.mossmc.mosscg.EasyQQ.Web;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;
import org.mossmc.mosscg.EasyQQ.BasicInfo;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class WebMain {
    public static HttpServer server;
    public static HttpServerProvider provider;
    public static void initWeb() {
        try {
            WebHandler.failedData.put("status",false);
            WebHandler.successData.put("status",true);
            provider = HttpServerProvider.provider();
            server = provider.createHttpServer(new InetSocketAddress(BasicInfo.config.getInteger("httpPort")),0);
            server.createContext("/API",new WebHandler());
            server.setExecutor(Executors.newCachedThreadPool());
            server.start();
            BasicInfo.logger.sendInfo("WebAPI已启动于本地端口"+BasicInfo.config.getInteger("httpPort")+"！");
        } catch (Exception e) {
            BasicInfo.logger.sendException(e);
        }

    }
}
