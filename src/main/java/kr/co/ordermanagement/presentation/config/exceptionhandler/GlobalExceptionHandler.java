package kr.co.ordermanagement.presentation.config.exceptionhandler;

import kr.co.ordermanagement.domain.exception.LackOfProductAmountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LackOfProductAmountException.class)
    public ResponseEntity<String> handleLackOfProductAmountException(LackOfProductAmountException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
