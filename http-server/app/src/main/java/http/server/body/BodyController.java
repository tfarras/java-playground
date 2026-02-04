package http.server.body;

import http.server.exceptions.UnprocessableEntityException;
import http.server.requestHandler.BaseRouter;
import http.server.requestHandler.Controller;
import http.server.requestHandler.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class BodyController extends Controller {
    private final BodyService bodyService = new BodyService();

    @Override
    public void register(BaseRouter router) {
        router.post("/", ((exchange, stringStringMap) -> {
            InputStream inputStream = exchange.getRequestBody();
            Object body = null;
            try {
                var stream = new ObjectInputStream(inputStream);
                body = stream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new UnprocessableEntityException(e.getMessage());
            }
            var data = bodyService.handleBody(body);

            Response.json(exchange, 201, data);
        }));
    }
}
