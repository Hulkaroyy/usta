package uz.handihub.productms.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.handihub.productms.exceptions.CustomException;
import uz.handihub.productms.service.dto.ErrorDTO;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(createError(ex, ex.getStatusCode()), ex.getStatusCode());
    }

    private ErrorDTO createError(RuntimeException ex, HttpStatus httpStatus){
        return ErrorDTO
                .builder()
                .error(ex.getMessage())
                .status(httpStatus.value())
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
