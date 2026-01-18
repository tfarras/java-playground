package http.server.requestHandler;

import com.sun.net.httpserver.HttpExchange;

import java.util.Map;
import java.util.function.BiConsumer;

public interface Handler extends BiConsumer<HttpExchange, Map<String, String>> {
}
