package http.server.exceptions;

public class UnprocessableEntityException extends HttpException {
    public UnprocessableEntityException(String message) {
        super(message, 422);
    }
}
