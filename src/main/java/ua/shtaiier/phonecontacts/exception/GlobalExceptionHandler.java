package ua.shtaiier.phonecontacts.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ContactNotFoundException.class)
    public String handleContactNotFoundException(ContactNotFoundException exception){
        return exception.getMessage();
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handleBindException(BindException ex) {
        String errorMessage = ex.getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Invalid input");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
