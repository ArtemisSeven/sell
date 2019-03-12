package cyx.sell.handler;

import cyx.sell.exception.ResponseBankException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResponseBankExceptionHandler {
    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)//通过这个注解使得返回的httpStatus=403
    public void handleResponseBankException(){

    }
}
