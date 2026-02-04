package http.server.body;

import http.server.requestHandler.BaseRouter;
import http.server.requestHandler.Controller;

public class BodyRouter extends BaseRouter {
    public BodyRouter() {
        super(new Controller[]{new BodyController()});
    }

    @Override
    public String getPath() {
        return "/body";
    }
}
