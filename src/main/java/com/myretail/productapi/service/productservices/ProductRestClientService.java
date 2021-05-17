package com.myretail.productapi.service.productservices;

import com.myretail.productapi.service.serviceexceptions.APIRequestException;

/**
 * Service for fetching names of products.
 *
 * Created by shreyasshivashankar on 14/05/2021.
 */
public interface ProductRestClientService {
    /**
     * Method to get the name of the product given the id.
     *
     * @param productId product id
     * @return the name of the product.
     */
    String getProductNameFromAPI(String productId) throws APIRequestException;
}
