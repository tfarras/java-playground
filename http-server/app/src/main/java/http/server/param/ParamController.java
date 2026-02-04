package http.server.param;

import http.server.requestHandler.BaseRouter;
import http.server.requestHandler.Controller;
import http.server.requestHandler.Response;

public class ParamController extends Controller {
    private final ParamService paramService = new ParamService();

    @Override
    public void register(BaseRouter router) {
        router.get("/{id}", ((httpExchange, params) -> {
            var response = paramService.handleParams(params);

            Response.json(httpExchange, 200, response);
        }));
    }
}
