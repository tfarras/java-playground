package http.server.testController;

import http.server.requestHandler.BaseRouter;
import http.server.requestHandler.Controller;

public class TestRouter extends BaseRouter {
    public TestRouter() {
        super(new Controller[]{new TestController()});
    }

    @Override
    public String getPath() {
        return "/test";
    }
}
