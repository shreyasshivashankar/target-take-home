package com.myretail.productapi.exceptions;

/**
 * Custom exception that is thrown when the product with a given id is not found.
 *
 * Created by shreyasshivashankar on 14/05/2021.
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
