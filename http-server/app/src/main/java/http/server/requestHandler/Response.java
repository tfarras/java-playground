package http.server.requestHandler;

import com.sun.net.httpserver.HttpExchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public final class Response {

    private static final ObjectMapper mapper = new ObjectMapper()
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    private Response() {}

    public static void json(HttpExchange exchange, int httpCode, Object data) {
        try {
            byte[] json = mapper.writeValueAsBytes(data);

            send(exchange, httpCode, json);
        } catch (Exception e) {
            error(exchange, 500, e.getMessage());
        }
    }

    public static void error(HttpExchange exchange, int status, String message) {
        json(exchange, status, Map.of("error", message));
    }

    private static void send(
            HttpExchange exchange,
            int status,
            byte[] body
    ) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, body.length);

        try (exchange; OutputStream os = exchange.getResponseBody()) {
            os.write(body);
        }
    }

}
