package http.server.exceptions;

public class HttpException extends RuntimeException {
    public int errorCode;
    public HttpException(String message, int errorCode) {
        super(message);

        this.errorCode = errorCode;
    }
}
