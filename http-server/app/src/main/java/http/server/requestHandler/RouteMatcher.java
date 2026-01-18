package http.server.requestHandler;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class RouteMatcher {
    private RouteMatcher() {
    }

    public static Map<String, String> matchPath(String template, String actualPath) {
        String storedPath = normalizePath(template);
        String receivedPath = normalizePath(actualPath);

        String[] storedPathParts = split(storedPath);
        String[] receivedPathParts = split(receivedPath);

        if (storedPathParts.length != receivedPathParts.length) {
            return null;
        }

        Map<String, String> params = new HashMap<>();

        for (int i = 0; i < storedPathParts.length; i++) {
            String storedPathPart = storedPathParts[i];
            String receivedPathPart = receivedPathParts[i];

            if (isParam(storedPathPart)) {
                String name = storedPathPart.substring(1, storedPathPart.length() - 1);
                params.put(name, urlDecode(receivedPathPart));
            } else if (!storedPathPart.equals(receivedPathPart)) {
                return null;
            }
        }
        return params;
    }

    private static String[] split(String path) {
        if (path.equals("/")) {
            return new String[0];
        }

        return Arrays.stream(path.split("/"))
                .filter(string -> !string.isBlank())
                .toArray(String[]::new);
    }

    private static String normalizePath(String path) {
        String normalisedPath = path;

        if (normalisedPath == null || normalisedPath.isEmpty()) {
            return "/";
        }

        if (!normalisedPath.startsWith("/")) {
            normalisedPath = "/" + normalisedPath;
        }

        if (normalisedPath.endsWith("/") && normalisedPath.length() > 1) {
            normalisedPath = normalisedPath.substring(0, normalisedPath.length() - 1);
        }

        return normalisedPath;
    }

    private static String urlDecode(String s) {
        return URLDecoder.decode(s, StandardCharsets.UTF_8);
    }

    private static boolean isParam(String part) {
        return part.startsWith("{") && part.endsWith("}") && part.length() > 2;
    }
}
