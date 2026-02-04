package http.server.root;

import http.server.requestHandler.BaseRouter;
import http.server.requestHandler.Controller;

public class RootRouter extends BaseRouter {
    public RootRouter() {
        super(new Controller[]{});
    }

    @Override
    public String getPath() {
        return "/";
    }
}
