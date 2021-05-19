package com.myretail.productapi.dto.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.myretail.productapi.dto.Price;
import com.myretail.productapi.dto.Product;

import java.io.IOException;

public class PriceSerializer extends JsonSerializer<Double> {
    @Override
    public void serialize(Double value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        try {
            jsonGenerator.writeString(value.toString());
        } catch (IOException e) {
            throw new IOException("Unable to parse the double value!");
        }
    }
}
