package zad2.exception;

public abstract class AppException extends RuntimeException {

    private AppException(String message) {
        super(message);
    }

    public static class Internal extends AppException {
        public Internal(Throwable e) {
            super("Internal error! Cause: %s".formatted(e.getMessage()));
        }
    }

    public static class NotFound extends AppException {
        public NotFound(String message) {
            super(message);
        }
    }

    public static class ExternalAPI extends AppException {
        public ExternalAPI(int statusCode) {
            super("Got error response status code %d!".formatted(statusCode));
        }
    }
}
