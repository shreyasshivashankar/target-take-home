package com.myretail.productapi.controller;

import com.myretail.productapi.dto.Product;
import com.myretail.productapi.service.productservices.ProductService;
import com.myretail.productapi.service.serviceexceptions.APIRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
public class ProductController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ProductService productService;

    public ProductController(@NonNull ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Product getProduct(@PathVariable String id) throws Exception {
        return productService.getProduct(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    Product updatePrice(@PathVariable String id, @RequestBody Product product) {
        if (product == null) {
            throw new APIRequestException("Invalid request body parameters. Check all the fields again!", null);
        }
        if (id.compareTo(product.getProductId()) != 0) {
            throw new APIRequestException("The product id in the request parameter and the request body are mismatching.", null);
        }
        try {
            productService.isValidProduct(id);
        } catch (Exception e) {
            throw new APIRequestException("Product with id: " + id + " does not exist", null);
        }
        return productService.updatePrice(product);
    }
}
