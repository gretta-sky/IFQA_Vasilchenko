package hooks;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.utilsProperties;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import static utils.utilsProperties.*;

public class HooksAuthApi {
    private static final Logger logger = LoggerFactory.getLogger(HooksAuthApi.class);
    private static boolean initialized = false;
    private static final String BASE_URI = getBaseUri();
    private static final ContentType CONTENT_TYPE = ContentType.JSON;
    private static final LogDetail LOG_DETAIL = LogDetail.ALL;
    private static final int EXPECTED_STATUS = getExpStatus();

    @Before("@auth")
    public void beforeAuthScenario(Scenario scenario) {
        Allure.step("Начало сценария авторизации: " + scenario.getName());
        if (!initialized) {
            initializeRestAssured();
            initialized = true;
        }
        logger.info("Запуск сценария авторизации: {}", scenario.getName());
        checkServerAvailability();
    }

    @After("@auth")
    public void afterAuthScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            Allure.step("Сценарий авторизации упал: " + scenario.getName());
            logger.error("Сценарий авторизации упал: {}", scenario.getName());
        } else {
            Allure.step("Сценарий авторизации пройден: " + scenario.getName());
            logger.info("Сценарий авторизации пройден: {}", scenario.getName());
        }
    }

    private void initializeRestAssured() {
        String baseUri = BASE_URI;

        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(CONTENT_TYPE)
                .log(LOG_DETAIL)
                .build();

        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .log(LOG_DETAIL)
                .build();

        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification = responseSpec;

        RestAssured.config = RestAssured.config()
                .httpClient(io.restassured.config.HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 10000)
                        .setParam("http.socket.timeout", 10000));
        logger.info("RestAssured настроен для Auth API: {}", baseUri);
        Allure.step("RestAssured настроен для Auth API: " + baseUri);
    }

    private void checkServerAvailability() {
        try {
            RestAssured.given()
                    .when()
                    .get("/")
                    .then()
                    .statusCode(EXPECTED_STATUS);
            logger.info("Auth сервер доступен");
            Allure.step("Auth сервер доступен");
        } catch (Exception e) {
            logger.warn("Auth сервер недоступен: {}", e.getMessage());
            Allure.step("Auth сервер недоступен.");
        }
    }

    @BeforeAll
    public static void setUpAllure() {
        AllureSelenide allureSelenide = new AllureSelenide()
                .screenshots(utilsProperties.getScreenshots())
                .savePageSource(utilsProperties.getPageSource())
                .includeSelenideSteps(utilsProperties.getSelenideSteps());
        SelenideLogger.addListener("AllureSelenide", allureSelenide);
    }
}