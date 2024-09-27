package edu.example.learner.exception;

public class LoginTaskException extends RuntimeException {

    private final int statusCode;

    public LoginTaskException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
