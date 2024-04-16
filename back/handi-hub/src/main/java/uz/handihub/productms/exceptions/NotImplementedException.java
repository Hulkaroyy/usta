package uz.handihub.productms.exceptions;

import org.springframework.http.HttpStatus;

public class NotImplementedException extends CustomException {

    private static final HttpStatus STATUS_CODE = HttpStatus.INTERNAL_SERVER_ERROR;

    public NotImplementedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return STATUS_CODE;
    }
}
