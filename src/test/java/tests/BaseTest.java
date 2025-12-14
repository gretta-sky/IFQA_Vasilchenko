package tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.requestSpecification = getBaseRequestSpec();
        RestAssured.responseSpecification = getBaseResponseSpec();
    }

    private static RequestSpecification getBaseRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://rickandmortyapi.com/api")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    private static ResponseSpecification getBaseResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .log(LogDetail.ALL)
                .build();
    }
}