package uz.handihub.productms.exceptions;

import org.springframework.http.HttpStatus;

public class ExistsException extends CustomException{
    private static final HttpStatus STATUS_CODE = HttpStatus.CONFLICT;

    public ExistsException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return STATUS_CODE;
    }
}
