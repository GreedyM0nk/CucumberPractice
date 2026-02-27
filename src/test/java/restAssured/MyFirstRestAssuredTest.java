
package restAssured;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

public class MyFirstRestAssuredTest {

    @BeforeClass
    public static void setUp() {
        // Disable SSL certificate validation globally for testing
        RestAssured.useRelaxedHTTPSValidation();
    }

    private static final String API_URL = "https://demo.guru99.com/V4/sinkministatement.php";

    @Test
    public void getResponseBody(){

        given()
                .queryParam("CUSTOMER_ID","68195")
                .queryParam("PASSWORD","1234!")
                .queryParam("Account_No","1")
                .when()
                .get(API_URL)
                .then()
                .log()
                .body();
    }

    @Test
    public void getResponseStatus(){
        int statusCode = given()
                .queryParam("CUSTOMER_ID","68195")
                .queryParam("PASSWORD","1234!")
                .queryParam("Account_No","1")
                .when()
                .get(API_URL)
                .getStatusCode();

        System.out.println("The response status is " + statusCode);

        given()
                .queryParam("CUSTOMER_ID","68195")
                .queryParam("PASSWORD","1234!")
                .queryParam("Account_No","1")
                .when()
                .get(API_URL)
                .then()
                .assertThat()
                .statusCode(200);
    }
}