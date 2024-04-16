package uz.handihub.productms.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException {
    private static final HttpStatus STATUS_CODE = HttpStatus.NOT_FOUND;

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return STATUS_CODE;
    }
}
