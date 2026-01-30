package steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Registration;
import api.AuthApi;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static utils.utilsProperties.*;

public class AuthApiSteps {

    private Registration validCredentials;
    private String savedToken;
    private Response lastResponse;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Step("Инициализация тестов авторизации")
    @Before("@auth")
    public void setup() {
    }

    @Step("Используем API авторизации")
    @Given("Я использую API авторизации")
    public void iUseAuthApi() {
    }

    @Step("Загружаем валидные учетные данные из файла")
    @Given("Я загружаю валидные учетные данные из файла")
    public void iLoadValidCredentialsFromFile() throws IOException {
        File credentialsFile = new File("src/test/java/resources/login.json");
        validCredentials = objectMapper.readValue(credentialsFile, Registration.class);
    }

    @Step("Отправляем запрос на регистрацию с валидными учетными данными")
    @When("Я отправляю запрос на регистрацию с валидными учетными данными")
    public void iSendRegistrationRequestWithValidCredentials() {
        lastResponse = AuthApi.register(validCredentials);
    }

    @Step("Проверка статус кода")
    @Then("Статус код ответа должен быть неуспешным")
    public void statusCodeShouldBeNeg() {
        int expectedStatusCodeNeg = getExpStatusNeg();
        int actualStatusCode = lastResponse.statusCode();
        assertEquals(expectedStatusCodeNeg, actualStatusCode,
                "Ожидался статус код " + expectedStatusCodeNeg + ", но получен " + actualStatusCode);
    }

    @Step("Проверка статус кода")
    @Then("Статус код ответа должен быть успешным")
    public void statusCodeShouldBe() {
        int expectedStatusCode = getExpStatus();
        int actualStatusCode = lastResponse.statusCode();
        assertEquals(expectedStatusCode, actualStatusCode,
                "Ожидался статус код " + expectedStatusCode + ", но получен " + actualStatusCode);
    }

    @Step("Проверка тела ответа")
    @Then("Тело ответа должно содержать успех")
    public void responseBodyShouldContainSuccess() {
        String expectedTextSuccess = getExpBodySuccess();
        String actualBody = lastResponse.getBody().asString();
        assertTrue(actualBody.contains(expectedTextSuccess),
                "Ожидалось, что тело ответа содержит '" + expectedTextSuccess + "', но получено: " + actualBody);
    }

    @Step("Проверка тела ответа")
    @Then("Тело ответа должно содержать не найдено")
    public void responseBodyShouldContainNeg() {
        String expectedTextNeg = getExpBodeNeg();
        String actualBody = lastResponse.getBody().asString();
        assertTrue(actualBody.contains(expectedTextNeg),
                "Ожидалось, что тело ответа содержит '" + expectedTextNeg + "', но получено: " + actualBody);
    }

    @Step("Предусловие: пользователь успешно зарегистрирован")
    @Given("Пользователь успешно зарегистрирован")
    public void userSuccessfullyRegistered() {
        int expectedStatus = getExpStatus();
        lastResponse = AuthApi.register(validCredentials);
        assertEquals(expectedStatus, lastResponse.statusCode(),
                "Регистрация должна быть успешной перед тестом авторизации");
    }

    @Step("Отправка запроса на авторизацию с валидными данными")
    @When("Я отправляю запрос на авторизацию с валидными учетными данными")
    public void iSendLoginRequestWithValidCredentials() {
        lastResponse = AuthApi.login(validCredentials);
    }

    @Step("Отправка запроса на авторизацию с неверным логином")
    @When("Я отправляю запрос на авторизацию с неверным логином")
    public void iSendLoginRequestWithWrongUsername() {
        Registration wrongUsername = new Registration(
                "wrong_" + validCredentials.getUsername(),
                validCredentials.getPassword()
        );
        lastResponse = AuthApi.login(wrongUsername);
    }

    @Step("Отправка запроса на авторизацию с неверным паролем")
    @When("Я отправляю запрос на авторизацию с неверным паролем")
    public void iSendLoginRequestWithWrongPassword() {
        Registration wrongPassword = new Registration(
                validCredentials.getUsername(),
                "wrong_" + validCredentials.getPassword()
        );
        lastResponse = AuthApi.login(wrongPassword);
    }

    @Step("Проверка валидности токена в ответе")
    @Then("Ответ должен содержать валидный токен")
    public void responseShouldContainValidToken() {
        String responseBody = lastResponse.getBody().asString();
        assertTrue(responseBody.startsWith("token : "),
                "Ответ должен начинаться с 'token : '");
    }

    @Step("Сохранение токена для последующих тестов")
    @And("Токен должен быть сохранен для последующих тестов")
    public void tokenShouldBeSavedForFutureTests() {
        String responseBody = lastResponse.getBody().asString();
        String tokenWithPrefix = responseBody.substring("token : ".length()).trim();

        try {
            savedToken = UUID.fromString(tokenWithPrefix).toString();
        } catch (IllegalArgumentException e) {
            fail("Токен должен быть валидным UUID. Получено: " + tokenWithPrefix);
        }
    }

    @Step("Предусловие: пользователь успешно авторизован")
    @Given("Пользователь успешно авторизован")
    public void userSuccessfullyLoggedIn() {
        int expectedStatus = getExpStatus();
        lastResponse = AuthApi.login(validCredentials);
        assertEquals(expectedStatus, lastResponse.statusCode(),
                "Авторизация должна быть успешной перед тестом выхода");
        String responseBody = lastResponse.getBody().asString();
        String tokenWithPrefix = responseBody.substring("token : ".length()).trim();
        savedToken = tokenWithPrefix;
    }

    @Step("Токен сохранен")
    @Given("Токен сохранен")
    public void tokenIsSaved() {
        assertNotNull(savedToken, "Токен должен быть сохранен");
    }

    @Step("Отправка запроса на выход с валидным токеном")
    @When("Я отправляю запрос на выход с валидным токеном")
    public void iSendLogoutRequestWithValidToken() {
        lastResponse = AuthApi.logout(savedToken);
    }

    @Step("Отправка запроса на выход с неверным токеном")
    @When("Я отправляю запрос на выход с неверным токеном")
    public void iSendLogoutRequestWithInvalidToken() {
        lastResponse = AuthApi.logoutWithInvalidUUID();
    }

    @Step("Сохранение токена из ответа")
    @And("Сохраняю токен из ответа")
    public void saveTokenFromResponse() {
        String responseBody = lastResponse.getBody().asString();
        if (responseBody.startsWith("token : ")) {
            savedToken = responseBody.substring("token : ".length()).trim();
        }
    }

    @Step("Проверка валидности UUID токена")
    @And("Проверяю что токен является валидным UUID")
    public void verifyTokenIsValidUUID() {
        assertNotNull(savedToken, "Токен не должен быть null");
        try {
            UUID.fromString(savedToken);
        } catch (IllegalArgumentException e) {
            fail("Токен не является валидным UUID: " + savedToken);
        }
    }

    @Step("Я отправляю запрос на авторизацию с неверными данными")
    @When("Я отправляю запрос на авторизацию с {string}")
    public void iSendLoginRequestWithDataType(String dataType) {
        switch (dataType) {
            case "неверным логином":
                iSendLoginRequestWithWrongUsername();
                break;
            case "неверным паролем":
                iSendLoginRequestWithWrongPassword();
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип данных: " + dataType);

        }
    }

    @Step("Проверка тела ответа на наличие текста")
    @Then("Тело ответа должно содержать текст {string}")
    public void responseBodyShouldContainText(String expectedText) {
        String actualBody = lastResponse.getBody().asString();
        assertEquals(expectedText, actualBody,
                "Ожидалось: '" + expectedText + "', но получено: '" + actualBody + "'");
    }
}