package http.server.exceptions;

public class MethodNotAllowedException extends HttpException {
    public MethodNotAllowedException(String message) {
        super(message, 405);
    }
}
