package com.myretail.productapi.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.myretail.productapi.dto.serialization.PriceSerializer;

import java.util.Objects;

/**
 * POJO representing price of a product.
 *
 * Created by shreyasshivashankar on 14/05/2021.
 */
public class Price {

    @JsonSerialize(using = PriceSerializer.class)
    private final Double value;

    private final Currency currencyCode;

    /**
     * Constructor to construct price pojo from JSON request parameters.
     * @param value double price value.
     * @param currency enum currency code.
     */
    @JsonCreator
    public Price(@JsonProperty("value") Double value, @JsonProperty("currency_code") Currency currency) {
        this.value = value;
        this.currencyCode = currency;
    }

    /**
     * Getter method to get the price value.
     * @return double price value.
     */
    public Double getValue() {
        return value;
    }

    /**
     * Getter method to get the currency code.
     * @return enum currency code.
     */
    public Currency getCurrency() {
        return currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(value, price.value) &&
                currencyCode == price.currencyCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currencyCode);
    }

    @Override
    public String toString() {
        return " value: " + value + ", currency_code=" + currencyCode;
    }

    /**
     * Method to validate the populated member fields.
     * @return validation result.
     */
    public boolean validate() {
        if (value == null || currencyCode == null) {
            return false;
        }
        return true;
    }
}
