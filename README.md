## Target Technical Assessment Case Study - MyRetail RESTful service:

myRetail is a rapidly growing company with HQ in Richmond, VA and over 200 stores across the east coast. myRetail wants to make its internal data available to any number of client devices, from myRetail.com to native mobile apps. 
The goal for this exercise is to create an end-to-end Proof-of-Concept for a products API, which will aggregate product data from multiple sources and return it as JSON to the caller. 
Your goal is to create a RESTful service that can retrieve product and price details by ID. The URL structure is up to you to define, but try to follow some sort of logical convention.

Build an application that performs the following actions: 
•	Responds to an HTTP GET request at /products/{id} and delivers product data as JSON (where {id} will be a number.

•	Example product IDs: 13860428, 54456119, 13264003, 12954218) 

•	Example response: {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}

•	Performs an HTTP GET to retrieve the product name from an external API. (For this exercise the data will come from redsky.target.com, but let’s just pretend this is an internal resource hosted by myRetail) 

•	Example: https://redsky.target.com/v3/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics&key=candidate

•	Reads pricing information from a NoSQL data store and combines it with the product id and name from the HTTP request into a single response. 

BONUS: Accepts an HTTP PUT request at the same path (/products/{id}), containing a JSON request body similar to the GET response, and updates the product’s price in the data store. 

## Implementation:

### Project Requirements:

* Java 11
* Spring Boot 2.5.0.RELEASE
* Maven version 4.0.0
* MongoDB version 4.4.6
* Mokito/Junit

### How to run this project?

1. Install mongoDb on your local machine and start the mongo server.
2. Create a new database myretail in mongo. Create a collection product.
3. Clone the project using git or any other tools.
4. Goto project home directory where pom.xml is present and run the below maven command to build the project.

```
mvn clean install
```
5. After a successful maven build, a jar file(productapi-0.0.1-SNAPSHOT.jar) will be generated in the target folder. Now execute the below command to run this application using springboot.
```
java -jar target/productapi-0.0.1-SNAPSHOT.jar
```
6. Server will start on port 3002 with context path '/api/v1' indicating first version of the API.

### API Documentation

```
Method     Request          Body                Description
 GET     /products/{id}     N/A            To get product details
 PUT     /products/{id}    Product         To update the pricing information of the product
 ```
 
 #### GET ProductDetails
 ```
 GET localhost:3002/api/v1/products/12345
 
 Response: 200OK
 
 {
    "id": "12345",
    "name": "The Big Lebowski (Blu-ray)",
    "current_price": {
        "value": "99.99",
        "currency_code": "USD"
    }
}
```
#### Update ProductPrice
```
PUT localhost:3002/api/v1/products/12345

RequestBody:
{
    "id": "12345",
    "name": "The Big Lebowski (Blu-ray)",
    "current_price": {
        "value": "100.99",
        "currency_code": "USD"
    }
}

ResponseStatus: 200OK
ResponseBody:
{
    "id": "12345",
    "name": "The Big Lebowski (Blu-ray)",
    "current_price": {
        "value": "100.49",
        "currency_code": "USD"
    }
}
```
### Exception Handling

* Throws APIRequestException if unable to fetch name of the product whose id does not exist in teh redsky API.
* Throws AuthorisationException if you are updating product price without the access token. 
* Throws InvalidRequestParametersException if the request parameters for the PUT request are not valid.
* Throws UpdatePriceException if failed to update the price of a product in the NoSQL store.

### Exception Response Handlers
Given the custom exception handling, I have also implemented exception response handlers that handle these exceptions 
and respond with appropriate response codes and message details for the client/user.

### Future Improvements
* I would integrate better OAuth implementation so that any client can register himself/herself and generate a timed token for API access.
* There are still some custom bad requests scenarios that can be handled more accurately to display appropriate message to the client.
* Currently the API functions only if the collections and databases are created to retrieve the pricing information for a product. We could potentially 
modify the code to create these entities if not already existing.
* If we expect lot of incoming requests per second, for our system to scale well, we could remove the dependency on the redsky API and store the name information
in the NoSQL itself. This will reduce the number of API calls.