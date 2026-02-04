package http.server.requestHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import http.server.exceptions.UnprocessableEntityException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Request<BodyType> {
    public final HttpExchange exchange;

    public Map<String, String> params = new HashMap<>();
    public BodyType body;

    public static <BodyType> Request<BodyType> create(HttpExchange exchange) {
        return new Request<BodyType>(exchange);
    }

    private Request(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public Request<BodyType> setParams(Map<String, String> params) {
        this.params = params;

        return this;
    }

    public Request<BodyType> setBody() {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = exchange.getRequestBody()) {
            return mapper.readValue(is, BodyType);
        } catch (IOException e) {
            throw new UnprocessableEntityException(e.getMessage());
        }
    }
}
