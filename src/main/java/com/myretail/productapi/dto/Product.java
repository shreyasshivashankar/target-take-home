package com.myretail.productapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.myretail.productapi.dto.serialization.ProductSerializer;
import com.myretail.productapi.service.serviceexceptions.InvalidRequestParametersException;

import java.util.Objects;

/**
 * POJO representing a product.
 *
 * Created by shreyasshivashankar on 14/05/2021.
 */

@JsonSerialize(using = ProductSerializer.class)
@JsonDeserialize(builder = Product.Builder.class)
public class Product {

    private String id;

    private String name;

    private Price currentPrice;

    private Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.currentPrice = builder.currentPrice;
        validate();
    }

    /**
     * Getter method to return productId.
     * @return Id of the product.
     */
    public String getProductId() {
        return id;
    }

    /**
     * Getter method to get the name of the product.
     * @return Name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method to get the price of the product.
     * @return Price of the product represented by price object.
     */
    public Price getPrice() {
        return currentPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(currentPrice, product.currentPrice);
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", current_price=" + currentPrice.toString() + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, currentPrice);
    }

    /**
     * Builder class to build a product object.
     */
    public static final class Builder {
        private String id;
        private String name;
        private Price currentPrice;

        @JsonProperty("id")
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        @JsonProperty("name")
        public Builder name(String name) {
            this.name = name;
            return this;
        }


        @JsonProperty("current_price")
        public Builder price(Price price) {
            this.currentPrice = price;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }

    public boolean validate() {
        if (id == null || !currentPrice.validate()) {
            throw new InvalidRequestParametersException("Invalid request parameters for product with id: " + id);
        }
        return true;
    }
}