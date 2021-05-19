package com.myretail.productapi.service.productservicesimpl;

import com.myretail.productapi.dbclient.repository.PriceRepository;
import com.myretail.productapi.dto.Price;
import com.myretail.productapi.dto.Product;
import com.myretail.productapi.service.productservices.ProductRestClientService;
import com.myretail.productapi.service.productservices.ProductService;
import com.myretail.productapi.exceptions.UpdatePriceException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private PriceRepository priceRepository;

    private ProductRestClientService productRestClientService;

    public ProductServiceImpl(@NonNull PriceRepository priceRepository, @NonNull ProductRestClientService productNameService) {
        this.priceRepository = priceRepository;
        this.productRestClientService = productNameService;
    }

    @Override
    public Product getProduct(String productId) {
        String productName = productRestClientService.getProductNameFromAPI(productId);

        Price price = priceRepository.getProductPrice(productId);

        return new Product.Builder()
                .id(productId)
                .name(productName)
                .price(price)
                .build();
    }

    @Override
    public Product updatePrice(Product product) {
        Boolean updatePriceState = priceRepository.updatePrice(product.getProductId(), product.getPrice());
        if (!updatePriceState) {
            throw new UpdatePriceException(String.format("Unable to update the price for product with ID: %s", product.getProductId()));
        }
        return product;
    }

    @Override
    public void isValidProduct(String productId) {
        productRestClientService.getProductNameFromAPI(productId);
    }
}
