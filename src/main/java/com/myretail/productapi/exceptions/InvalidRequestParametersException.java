package com.myretail.productapi.exceptions;

/**
 * Custom exception that is thrown when the request parameters are invalid.
 *
 * Created by shreyasshivashankar on 14/05/2021.
 */
public class InvalidRequestParametersException extends RuntimeException {
    public InvalidRequestParametersException(String message) {
        super(message);
    }
}
