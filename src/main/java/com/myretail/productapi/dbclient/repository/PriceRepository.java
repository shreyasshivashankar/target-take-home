package com.myretail.productapi.dbclient.repository;

import com.myretail.productapi.dto.Price;
import org.springframework.stereotype.Component;

/**
 * Repository for fetching the price details from MongoDB.
 *
 * Created by shreyasshivashankar on 14/05/2021.
 */
public interface PriceRepository {
    /**
     * Fetch a price for a given product
     *
     * @param productId the product id
     * @return a price or null if the price cannot be found
     */
    Price getProductPrice(String productId);

    /**
     * Update the price of a product
     *
     * @param productId product id
     * @param price price
     * @return SUCCESS if the update succeeded else FAILURE
     */
    Boolean updatePrice(String productId, Price price);
}
