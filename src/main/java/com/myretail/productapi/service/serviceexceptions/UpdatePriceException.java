package com.myretail.productapi.service.serviceexceptions;

/**
 * Custom exception that is thrown when a product price updation fails.
 *
 * Created by shreyasshivashankar on 14/05/2021.
 */
public class UpdatePriceException extends RuntimeException {
    public UpdatePriceException(String message) {
        super(message);
    }
}
