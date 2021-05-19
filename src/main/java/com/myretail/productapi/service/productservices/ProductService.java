package com.myretail.productapi.service.productservices;

import com.myretail.productapi.dto.Product;
import com.myretail.productapi.exceptions.ProductNotFoundException;

public interface ProductService {
    /**
     * Method to get a product given productId.
     *
     * @param productId id of the product.
     * @return product object.
     * @throws ProductNotFoundException if no valid product is found
     */
    Product getProduct(String productId) throws Exception;

    /**
     * Updates only the price of a given product.
     *
     * @param product the product for which price has to be updated.
     * @return updated product.
     */
    Product updatePrice(Product product);

    /**
     * Method to check if a product with valid productId exists.
     *
     * @param productId id of the product.
     * @return product object.
     */
    void isValidProduct(String productId);
}
