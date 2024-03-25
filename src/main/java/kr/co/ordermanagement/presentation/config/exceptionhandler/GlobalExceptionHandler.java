package kr.co.ordermanagement.presentation.config.exceptionhandler;

import kr.co.ordermanagement.domain.exception.CancellableStateException;
import kr.co.ordermanagement.domain.exception.LackOfProductAmountException;
import kr.co.ordermanagement.domain.exception.OrderStateNotFoundException;
import kr.co.ordermanagement.presentation.dto.error.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LackOfProductAmountException.class)
    public ResponseEntity<ErrorResponseDto> handleLackOfProductAmountException(LackOfProductAmountException ex){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OrderStateNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderStateNotFoundException(OrderStateNotFoundException ex){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CancellableStateException.class)
    public ResponseEntity<ErrorResponseDto> handleCancellableStateException(CancellableStateException ex){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getMessage());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
