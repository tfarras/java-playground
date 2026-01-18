package http.server.requestHandler;

import com.sun.net.httpserver.HttpExchange;
import http.server.exceptions.HttpException;

public class ExceptionHandler {
    public void handleHttpException(HttpExchange exchange, HttpException httpException) {
        Response.error(exchange, httpException.errorCode, httpException.getMessage());
    }
}
