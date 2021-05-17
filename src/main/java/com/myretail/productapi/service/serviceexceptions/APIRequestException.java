package com.myretail.productapi.service.serviceexceptions;

/**
 * Custom exception that is thrown when the API request to fetch the product name fails.
 *
 * Created by shreyasshivashankar on 14/05/2021.
 */
public class APIRequestException extends RuntimeException {
    public APIRequestException(String message, Exception e) {
        super(message);
    }
}
