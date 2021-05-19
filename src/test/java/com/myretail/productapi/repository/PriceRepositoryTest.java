//package com.myretail.productapi.repository;
//
//import com.mongodb.client.*;
//import com.mongodb.client.result.UpdateResult;
//import com.myretail.productapi.dbclient.repository.repositoryimpl.PriceRepositoryImpl;
//import com.myretail.productapi.dto.Currency;
//import com.myretail.productapi.dto.Price;
//import org.bson.Document;
//import org.bson.conversions.Bson;
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.when;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//
//@SpringBootTest
//public class PriceRepositoryTest {
//
//    @InjectMocks
//    private PriceRepositoryImpl priceRepository;
//
//    @MockBean
//    private MongoClient mongoClient;
//
//    @MockBean
//    private MongoDatabase mockDatabase;
//
//    @MockBean
//    private MongoCollection<Document> mockCollection;
//
//    @MockBean
//    private FindIterable<Document> mockFindIterable;
//
//    @MockBean
//    private MongoCursor<Document> mockCursor;
//
//    @MockBean
//    private Document productDocument;
//
//    @MockBean
//    private UpdateResult updateResult;
//
//    private static final String PRODUCT_ID_PROPERTY = "id";
//    private static final String PRICE_PROPERTY = "current_price";
//    private static final String VALUE_PROPERTY = "value";
//    private static final String CURRENCY_PROPERTY = "currency_code";
//
//    private static final String DB_NAME = "test_db";
//    private static final String COLLECTION_NAME = "test_collection";
//    private static final String PRODUCT_ID = "12345";
//    private static final Price PRICE = new Price(99.99, Currency.USD);
//    private static final Document PRICE_DOCUMENT =
//            new Document(VALUE_PROPERTY, PRICE.getValue().doubleValue())
//                    .append(CURRENCY_PROPERTY, PRICE.getCurrency().name());
//
//    @Before
//    public void initMocks() {
//        ReflectionTestUtils.setField(priceRepository, "dbName", DB_NAME);
//        ReflectionTestUtils.setField(priceRepository, "collectionName", COLLECTION_NAME);
//
//        when(mongoClient.getDatabase(anyString())).thenReturn(mockDatabase);
//        when(mockDatabase.getCollection(anyString())).thenReturn(mockCollection);
//        when(mockCollection.find(any(Bson.class))).thenReturn(mockFindIterable);
//        when(mockFindIterable.iterator()).thenReturn(mockCursor);
//
//        when(mockCollection.updateOne(any(Bson.class), any(Bson.class))).thenReturn(updateResult);
//    }
//
//    @Test
//    public void testGetPriceValid() {
//        when(mockCursor.hasNext()).thenReturn(true);
//        when(mockCursor.next()).thenReturn(productDocument);
//        when(productDocument.get(PRICE_PROPERTY, Document.class)).thenReturn(PRICE_DOCUMENT);
//
//        Price price = priceRepository.getProductPrice(PRODUCT_ID);
//        assertEquals(price, PRICE);
//    }
//
//    @Test
//    public void testGetPriceForMissingProductReturnsNull() {
//        when(mockCursor.hasNext()).thenReturn(false);
//
//        Price price = priceRepository.getProductPrice(PRODUCT_ID);
//        assertNull(price);
//    }
//
//    @Test
//    public void testUpdatePrice() {
//        when(updateResult.getMatchedCount()).thenReturn(1L);
//        Boolean result = priceRepository.updatePrice(PRODUCT_ID, PRICE);
//        assertTrue(result);
//    }
//
//    @Test
//    public void testUpdatePriceNoMatchingProduct() {
//        when(updateResult.getMatchedCount()).thenReturn(0L);
//        Boolean result = priceRepository.updatePrice(PRODUCT_ID, PRICE);
//        assertFalse(result);
//    }
//}
