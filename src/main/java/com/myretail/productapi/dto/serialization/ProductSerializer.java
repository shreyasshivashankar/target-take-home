package com.myretail.productapi.dto.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.myretail.productapi.dto.Product;

import java.io.IOException;

public class ProductSerializer extends JsonSerializer<Product>  {

    @Override
    public void serialize(Product product, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", product.getProductId());
        jsonGenerator.writeStringField("name", product.getName());
        jsonGenerator.writeObjectField("current_price", product.getPrice());
        jsonGenerator.writeEndObject();
    }
}
