package com.light.invoices.exception;

import com.light.invoices.controllers.response.ResponseApi;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseApi<Void> handleException(Exception e) {
        return new ResponseApi<>(500, "An unexpected error occurred");
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseApi<Void> handleNotFoundException(NotFoundException e) {
        return new ResponseApi<>(404, e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseApi<Void> handleBusinessException(BusinessException e) {
        return new ResponseApi<>(e.getStatusCode(), e.getMessage());
    }

}
