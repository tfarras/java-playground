package http.server.exceptions;

public class NotFoundException extends HttpException {
    public NotFoundException(String message) {
        super(message, 404);
    }
}
