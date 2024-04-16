package uz.handihub.productms.exceptions;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private static final HttpStatus STATUS_CODE = HttpStatus.INTERNAL_SERVER_ERROR;
    private final String cause;

    public CustomException(String message, String cause) {
        super(message);
        this.cause = cause;
    }

    public CustomException(String message) {
        super(message);
        this.cause = null;
    }

    public HttpStatus getStatusCode() { return STATUS_CODE; }

    public String getCauses() {
        return this.cause;
    }

}
