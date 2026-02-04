package http.server.param;

import http.server.requestHandler.BaseRouter;
import http.server.requestHandler.Controller;

public class ParamRouter extends BaseRouter {
    public ParamRouter() {
        super(new Controller[]{new ParamController()});
    }

    @Override
    public String getPath() {
        return "/param";
    }
}
