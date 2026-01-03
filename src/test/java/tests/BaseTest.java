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
    private static final String BASE_URI = "https://rickandmortyapi.com/api";
    private static final ContentType CONTENT_TYPE = ContentType.JSON;
    private static final LogDetail LOG_DETAIL = LogDetail.ALL;
    private static final int EXPECTED_STATUS = 200;

    @BeforeAll
    public static void setUp() {
        RestAssured.requestSpecification = createRequestSpec();
        RestAssured.responseSpecification = createResponseSpec();
    }

    private static RequestSpecification createRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(CONTENT_TYPE)
                .log(LOG_DETAIL)
                .build();
    }

    private static ResponseSpecification createResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType(CONTENT_TYPE)
                .expectStatusCode(EXPECTED_STATUS)
                .log(LOG_DETAIL)
                .build();
    }
}