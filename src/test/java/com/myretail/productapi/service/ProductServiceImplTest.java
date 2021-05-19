package com.myretail.productapi.service;

import com.myretail.productapi.dbclient.repository.PriceRepository;
import com.myretail.productapi.dto.Currency;
import com.myretail.productapi.dto.Price;
import com.myretail.productapi.dto.Product;
import com.myretail.productapi.service.productservices.ProductRestClientService;
import com.myretail.productapi.service.productservicesimpl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private ProductRestClientService productRestClientService;

    private static final String PRODUCT_ID = "102.34";
    private static final String PRODUCT_NAME = "Really Big Jigsaw Puzzle";
    private static final Price PRICE = new Price(Double.parseDouble(PRODUCT_ID), Currency.USD);
    private static final Product TEST_PRODUCT = new Product.Builder()
            .id(PRODUCT_ID)
            .name(PRODUCT_NAME)
            .price(PRICE)
            .build();

    @Test
    public void testGetProduct() throws Exception {
        when(priceRepository.getProductPrice(PRODUCT_ID)).thenReturn(PRICE);
        when(productRestClientService.getProductNameFromAPI(PRODUCT_ID)).thenReturn(PRODUCT_NAME);

        Product product = productService.getProduct(PRODUCT_ID);
        assertEquals(product, TEST_PRODUCT);
    }
}
