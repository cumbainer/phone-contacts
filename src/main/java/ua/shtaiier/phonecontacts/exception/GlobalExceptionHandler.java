package ua.shtaiier.phonecontacts.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ContactNotFoundException.class)
    public String handleContactNotFoundException(ContactNotFoundException exception){

        //todo implement
        return "";
    }
}
