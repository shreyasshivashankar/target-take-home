package com.myretail.productapi.controller;

import com.myretail.productapi.dbclient.repository.PriceRepository;
import com.myretail.productapi.service.productservices.ProductRestClientService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public class ProductControllerTestConfig {
    @Bean
    @Primary
    public PriceRepository priceRepository() {
        return Mockito.mock(PriceRepository.class);
    }

    @Bean
    @Primary
    public ProductRestClientService productNameService() {
        return Mockito.mock(ProductRestClientService.class);
    }
}
