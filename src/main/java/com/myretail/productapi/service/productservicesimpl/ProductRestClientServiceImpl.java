package com.myretail.productapi.service.productservicesimpl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.productapi.service.productservices.ProductRestClientService;
import com.myretail.productapi.exceptions.APIRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

@Service
public class ProductRestClientServiceImpl implements ProductRestClientService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Value("${redsky.target.service}")
    private String requestBaseURl;

    private static final String REQUEST_URL_PARAMS = "excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics&key=candidate";

    @Override
    public String getProductNameFromAPI(String productId) throws APIRequestException {
        String requestUrl = buildRequestUrl(productId);
        ResponseEntity<String> jsonObject;
        String name;
        try {
            jsonObject = restTemplate.getForEntity(requestUrl, String.class);
            name = extractName(jsonObject.getBody());
        } catch (Exception e) {
            logger.info("Unable to fetch product name for product with id: " + productId + " from redsky API");
            throw new APIRequestException("Failed to fetch product name from https://redsky.target.com/v3/pdp/tcin. ", e);
        }
        logger.info("Successfully fetched product name for product with id: " + productId);
        return name;
    }

    /**
     * @param productId of the product.
     * @return API url.
     */
    private String buildRequestUrl(String productId) {
        return requestBaseURl + "/" + productId + "?" + REQUEST_URL_PARAMS;
    }

    /**
     * @param jsonObject JSON response object.
     * @return productName
     * @throws JsonParseException   throws when unable to parse the request json.
     * @throws JsonMappingException throws when unable to map the required field from the request json.
     * @throws IOException          This method extracts product title from the productInfo json object.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private String extractName(String jsonObject) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Map> objectMap = mapper.readValue(jsonObject, Map.class);
        Map<String, Map> productMap = objectMap.get("product");
        Map<String, Map> itemMap = productMap.get("item");
        Map<String, String> prodDescriptionMap = itemMap.get(("product_description"));
        return prodDescriptionMap.get("title");
    }

}
