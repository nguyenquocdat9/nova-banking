package org.nova.common.exception;

import org.nova.customer.exception.CustomerEmailAlreadyExistsException;
import org.nova.customer.exception.CustomerNotFoundException;
import org.nova.customer.exception.CustomerPhoneAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerEmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleCustomerEmailAlreadyExistsException(CustomerEmailAlreadyExistsException ex) {

        return new ApiError(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(CustomerPhoneAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleCustomerPhoneAlreadyExistsException(CustomerPhoneAlreadyExistsException ex) {

        return new ApiError(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach((fieldError) -> {
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                });

        return new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                errors
        );
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

}
