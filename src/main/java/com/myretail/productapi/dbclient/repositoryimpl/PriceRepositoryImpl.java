package com.myretail.productapi.dbclient.repositoryimpl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.myretail.productapi.dbclient.repository.PriceRepository;
import com.myretail.productapi.dto.Currency;
import com.myretail.productapi.dto.Price;
import com.myretail.productapi.exceptions.InvalidRequestParametersException;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;

/**
 * This class implements {@link com.myretail.productapi.dbclient.repository.PriceRepository}.
 */
@Repository
public class PriceRepositoryImpl implements PriceRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MongoClient mongoClient;

    @Value("${mongo.db}")
    private String dbName;

    @Value("${mongo.collection}")
    private String collectionName;

    /**
     * MongoDB property names for product price.
     */
    private static final String PRODUCT_ID_PROPERTY = "id";
    private static final String PRICE_PROPERTY = "current_price";
    private static final String VALUE_PROPERTY = "value";
    private static final String CURRENCY_PROPERTY = "currency_code";

    /**
     * Constructor.
     * @param mongoClient mongodb client.
     */
    public PriceRepositoryImpl(@NonNull MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public Price getProductPrice(String productId) {
        Price price = null;

        MongoCollection<Document> collection = getCollection();
        MongoCursor<Document> documents = collection.find(eq(PRODUCT_ID_PROPERTY, productId)).iterator();

        if (documents.hasNext()) {
            Document priceDoc = documents.next().get(PRICE_PROPERTY, Document.class);
            price = new Price(priceDoc.getDouble(VALUE_PROPERTY),
                    Currency.valueOf(priceDoc.getString(CURRENCY_PROPERTY)));
        }
        if (price == null) {
            logger.info("Failed to fetch pricing details for product with id: " + productId);
            throw new InvalidRequestParametersException("No pricing information available for product with id: " + productId);
        }
        logger.info("Successfully fetched pricing details for product with id: " + productId + ", Pricing details: " + price.toString());
        return price;
    }

    @Override
    public Boolean updatePrice(String productId, Price price) {
        MongoCollection<Document> collection = getCollection();
        Document priceDoc = new Document(VALUE_PROPERTY, price.getValue())
                .append(CURRENCY_PROPERTY, price.getCurrency().name());
        UpdateResult result = collection.updateOne(eq(PRODUCT_ID_PROPERTY, productId),
                new Document("$set", new Document(PRICE_PROPERTY, priceDoc)));
        if (result.getMatchedCount() == 0) {
            logger.info("Inserted pricing details for product with id: " + productId + " Pricing details: " + price.toString());
            InsertOneResult resultInsert = collection.insertOne(new Document(PRICE_PROPERTY, priceDoc).append(PRODUCT_ID_PROPERTY, productId));
            return true;
        }
        logger.info("Updated pricing details for product with id: " + productId + " Pricing details: " + price.toString());
        return true;
    }

    /**
     * helper method to fetch the product collection from the db.
     *
     * @return the product collection.
     */
    private MongoCollection<Document> getCollection() {
        MongoDatabase db = mongoClient.getDatabase(dbName);
        return db.getCollection(collectionName);
    }
}