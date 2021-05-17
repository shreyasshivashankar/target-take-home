package com.myretail.productapi.service.serviceexceptions;

/**
 * Custom exception that is thrown when there is not pricing information registered for the product in the NoSQL store.
 *
 * Created by shreyasshivashankar on 14/05/2021.
 */
public class PriceNullException extends RuntimeException {
    public PriceNullException(String message) {
        super(message);
    }

}
