package com.myretail.productapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.productapi.dbclient.repository.PriceRepository;
import com.myretail.productapi.dto.Currency;
import com.myretail.productapi.dto.Price;
import com.myretail.productapi.dto.Product;
import com.myretail.productapi.exceptions.APIRequestException;
import com.myretail.productapi.exceptions.InvalidRequestParametersException;
import com.myretail.productapi.service.productservices.ProductRestClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceRepository priceRepository;

    @MockBean
    private ProductRestClientService productRestClientService;

    private static final String PRODUCT_ID = "12345";
    private static final String PRODUCT_NAME = "test_name";
    private static final Double PRICE_VALUE = 99.99;
    private static final Currency CURRENCY = Currency.USD;
    private static final Price PRICE = new Price(PRICE_VALUE, CURRENCY);
    private static final Product TEST_PRODUCT = new Product.Builder()
            .id(PRODUCT_ID)
            .name(PRODUCT_NAME)
            .price(PRICE)
            .build();

    @Test
    public void testGetProduct() throws Exception {
        when(priceRepository.getProductPrice(PRODUCT_ID)).thenReturn(PRICE);
        when(productRestClientService.getProductNameFromAPI(PRODUCT_ID)).thenReturn(PRODUCT_NAME);

        this.mockMvc.perform(get("/products/" + PRODUCT_ID)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(PRODUCT_ID))
                .andExpect(jsonPath("$.name").value(PRODUCT_NAME))
                .andExpect(jsonPath("$.current_price.currency_code").value(CURRENCY.name()))
                .andExpect(jsonPath("$.current_price.value").value(PRICE_VALUE));
    }

    @Test
    public void testGetProductNoPricingInformation() throws Exception {
        when(priceRepository.getProductPrice(PRODUCT_ID)).thenThrow(new InvalidRequestParametersException("No pricing information available for product with id: " + PRODUCT_ID));

        this.mockMvc.perform(get("/products/" + PRODUCT_ID)).andDo(print()).andExpect(status().isNotAcceptable());
    }

    @Test
    public void testGetProductInvalidId() throws Exception {
        when(priceRepository.getProductPrice(PRODUCT_ID)).thenReturn(PRICE);
        when(productRestClientService.getProductNameFromAPI(PRODUCT_ID)).thenThrow(new APIRequestException("Failed to fetch product name from https://redsky.target.com/v3/pdp/tcin. ", null));

        this.mockMvc.perform(get("/products/" + PRODUCT_ID)).andDo(print()).andExpect(status().isNotAcceptable());
    }

    @Test
    public void testUpdatePrice() throws Exception {
        when(priceRepository.updatePrice(PRODUCT_ID, PRICE)).thenReturn(Boolean.TRUE);
        when(productRestClientService.getProductNameFromAPI(PRODUCT_ID)).thenReturn(PRODUCT_NAME);
        String productJson = "{\"id\": \"12345\",\"name\": \"test_name\",\"current_price\": {\"value\": \"99.99\",\"currency_code\": \"USD\"}}";
        this.mockMvc.perform(put("/products/" + PRODUCT_ID)
                .header("access_token", "test_token_uid_encrypt")
                .content(productJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testUpdatePriceNullProduct() throws Exception {
        this.mockMvc.perform(put("/products/" + PRODUCT_ID)
                .header("access_token", "test_token_uid_encrypt")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdatePriceIdMismatch() throws Exception {
        String productJson = "{\"id\": \"invalid\",\"name\": \"test_name\",\"current_price\": {\"value\": \"99.99\",\"currency_code\": \"USD\"}}";
        this.mockMvc.perform(put("/products/" + PRODUCT_ID)
                .header("access_token", "test_token_uid_encrypt")
                .content(productJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNotAcceptable());
    }

    @Test
    public void testUpdatePriceUnauthorised() throws Exception {
        String productJson = "{\"id\": \"invalid\",\"name\": \"test_name\",\"current_price\": {\"value\": \"99.99\",\"currency_code\": \"USD\"}}";
        this.mockMvc.perform(put("/products/" + PRODUCT_ID)
                .header("access_token", "")
                .content(productJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isUnauthorized());
    }
}
