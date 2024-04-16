package uz.handihub.productms.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidArgumentException extends CustomException {
    private static final HttpStatus STATUS_CODE = HttpStatus.BAD_REQUEST;

    public InvalidArgumentException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return STATUS_CODE;
    }
}
