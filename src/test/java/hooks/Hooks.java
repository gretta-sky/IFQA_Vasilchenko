package hooks;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.cucumber.java.Before;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.utilsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static utils.utilsProperties.getBaseUrl;
import static utils.utilsProperties.getExpStatus;

public class Hooks {
    private static final String BASE_URL = getBaseUrl();
    private static final ContentType CONTENT_TYPE = ContentType.JSON;
    private static final LogDetail LOG_DETAIL = LogDetail.ALL;
    private static final int EXPECTED_STATUS = getExpStatus();
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private static boolean initialized = false;

    @Before("@rickandmorty")
    public void setUp() {
        if (!initialized) {
            configureRestAssured();
            initialized = true;
            logger.info("Rick and Morty API настроен");
        }
    }

    private void configureRestAssured() {
        String baseUrl = BASE_URL;

        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(CONTENT_TYPE)
                .log(LOG_DETAIL)
                .build();

        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectContentType(CONTENT_TYPE)
                .expectStatusCode(EXPECTED_STATUS)
                .log(LOG_DETAIL)
                .build();

        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification = responseSpec;

        logger.info("RestAssured настроен для: {}", baseUrl);

    }

    @Before("@rickandmorty")
    public static void setUpAllure() {
        AllureSelenide allureSelenide = new AllureSelenide()
                .screenshots(utilsProperties.getScreenshots())
                .savePageSource(utilsProperties.getPageSource())
                .includeSelenideSteps(utilsProperties.getSelenideSteps());
        SelenideLogger.addListener("AllureSelenide", allureSelenide);
    }
}