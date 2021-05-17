package com.myretail.productapi.dto;

/**
 * Currency enum representing the currency type for a product price.
 *
 * Created by shreyasshivashankar on 14/05/2021.
 */
public enum Currency {

    EUR("EUR"),
    USD("USD");

    private final String code;

    Currency(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
