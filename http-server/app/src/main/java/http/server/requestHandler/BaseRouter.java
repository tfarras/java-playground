package http.server.requestHandler;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import http.server.exceptions.HttpException;
import http.server.exceptions.MethodNotAllowedException;
import http.server.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseRouter implements HttpHandler {
    private final MethodRoutes routes = new MethodRoutes();
    protected Controller[] controllers;

    public BaseRouter(Controller[] controllers) {
        this.controllers = controllers;
    }

    public abstract String getPath();

    public void handle(HttpExchange exchange) throws MethodNotAllowedException, NotFoundException {
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        try {
            String stringMethod = exchange.getRequestMethod().toUpperCase();
            String path = exchange.getRequestURI().getPath();
            HttpMethod httpMethod = HttpMethod.valueOf(stringMethod);

            Map<HttpMethod, Handler> routesMap = null;
            Map<String, String> params = null;

            for (var route : routes.entrySet()) {
                params = RouteMatcher.matchPath(route.getKey(), path);

                if (params != null) {
                    routesMap = route.getValue();
                    break;
                }
            }

            if (routesMap == null) {
                this.handleNotFound(exchange);

                return;
            }

            Handler handler = routesMap.get(httpMethod);

            if (handler != null) {
                handler.accept(exchange, null);

                return;
            }

            handleMethodNotAllowed(exchange);
        } catch (HttpException e) {
            exceptionHandler.handleHttpException(exchange, e);
        } catch (RuntimeException e) {
            System.out.println(e.toString());

            Response.error(exchange, 500, "Internal Server Error");
        }
    }

    public void registerRouter(HttpServer server) {
        HttpContext context = server.createContext(getPath());
        context.setHandler(this);

        for (Controller controller : controllers) {
            controller.register(this);
        }
    }

    public void addRoute(
            HttpMethod httpMethod,
            String path,
            Handler handler
    ) {
        routes
                .computeIfAbsent(getPath() + path, (k) -> new HashMap<>())
                .put(httpMethod, handler);
    }

    public void get(String path, Handler handler) {
        addRoute(HttpMethod.GET, path, handler);
    }

    protected void handleMethodNotAllowed(HttpExchange exchange) {
        throw new MethodNotAllowedException("Method not allowed");
    }

    protected void handleNotFound(HttpExchange exchange) {
        throw new NotFoundException("Not found");
    }
}
