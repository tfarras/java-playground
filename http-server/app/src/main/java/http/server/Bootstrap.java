package http.server;

import com.sun.net.httpserver.HttpServer;
import http.server.requestHandler.BaseRouter;
import http.server.testController.TestRouter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class Bootstrap {
    public static final Logger logger = Logger.getLogger(Bootstrap.class.getName());
    private static final BaseRouter[] routers = new BaseRouter[]{
            new TestRouter(),
    };

    public static void  main(String[] args) {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.start();

            logger.info("Server has started and listening on port 8000");
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        this.registerRequestHandlers(server);

        server.start();
    }

    public void registerRequestHandlers(HttpServer server) {
        for(BaseRouter router : routers) {
            router.registerRouter(server);

            logger.info("Registered router " + router.getPath());
        }
    }
}