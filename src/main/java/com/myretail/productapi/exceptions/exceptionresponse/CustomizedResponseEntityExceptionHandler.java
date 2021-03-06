package com.myretail.productapi.exceptions.exceptionresponse;

import java.util.Date;

import com.myretail.productapi.exceptions.APIRequestException;
import com.myretail.productapi.exceptions.AuthorizationException;
import com.myretail.productapi.exceptions.InvalidRequestParametersException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(APIRequestException.class)
    public final ResponseEntity<ExceptionResponse> handleApiRequestException(APIRequestException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false),HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestParametersException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidRequestParametersException(InvalidRequestParametersException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false),HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationException.class)
    public final ResponseEntity<ExceptionResponse> handleAuthorizationException(AuthorizationException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false),HttpStatus.UNAUTHORIZED.getReasonPhrase());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }
}