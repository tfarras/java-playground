package http.server.testController;

import http.server.requestHandler.BaseRouter;
import http.server.requestHandler.Controller;
import http.server.requestHandler.Response;

public class TestController extends Controller {
    @Override
    public void register(BaseRouter router) {
        router.get("/", ((httpExchange, params) -> {
            Response.json(httpExchange, 200, "JSON response");
        }));
    }
}
