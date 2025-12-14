package tests;

import api.AuthApi;
import models.Registration;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthApiTest extends BaseTestAuth {

    private static Registration validCredentials;
    private static String savedToken;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void loadCredentials() throws IOException {
        File credentialsFile = new File("src/test/java/resources/login.json");
        validCredentials = objectMapper.readValue(credentialsFile, Registration.class);
    }

    @Test
    @Order(1)
    @DisplayName("1.Регистрация")
    void testRegistration() {
        Response response = AuthApi.register(validCredentials);

        assertEquals(200, response.statusCode(),
                "Статус код должен быть 200");

        String responseBody = response.getBody().asString();
        assertEquals("success register", responseBody,
                "Тело ответа должно содержать success register");
    }

    @Test
    @Order(2)
    @DisplayName("2.Авторизация: негативные")
    void testLoginNegativeScenarios() {
        Registration wrongUsername = new Registration(
                "wrong_" + validCredentials.getUsername(),
                validCredentials.getPassword()
        );
        Response responseWrongUsername = AuthApi.login(wrongUsername);
        assertEquals(401, responseWrongUsername.statusCode(),
                "Статус код должен быть 401 при неверном имени");

        assertEquals("not found", responseWrongUsername.getBody().asString(),
                "Тело ответа должно содержать not found");

        Registration wrongPassword = new Registration(
                validCredentials.getUsername(),
                "wrong_" + validCredentials.getPassword()
        );
        Response responseWrongPassword = AuthApi.login(wrongPassword);
        assertEquals(401, responseWrongPassword.statusCode(),
                "Статус код должен быть 401 при неверном пароле");

        assertEquals("not right pass", responseWrongPassword.getBody().asString(),
                "Тело ответа должно содержать not right pass");
    }

    @Test
    @Order(3)
    @DisplayName("3.Авторизация: успешный сценарий")
    void testLoginSuccess() {
        Response response = AuthApi.login(validCredentials);
        assertEquals(200, response.statusCode(),
                "Статус код должен быть 200 при успешной авторизации");

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.startsWith("token : "),
                "Ответ должен начинаться с 'token : '");

        String tokenWithPrefix = responseBody.substring("token : ".length()).trim();
        try {
            savedToken = UUID.fromString(tokenWithPrefix).toString();
        } catch (IllegalArgumentException e) {
            fail("Токен должен быть валидным. Получено: " + tokenWithPrefix);
        }
    }

    @Test
    @Order(4)
    @DisplayName("4.Выход: негативный сценарий")
    void testLogoutNegative() {
        Response response = AuthApi.logoutWithInvalidUUID();

        assertEquals(401, response.statusCode(),
                "Статус код должен быть 401 при неверном токене");
        assertEquals("not found", response.getBody().asString(),
                "Тело ответа должно содержать not found");
    }

    @Test
    @Order(5)
    @DisplayName("5.Выход: успешный")
    void testLogoutSuccess() {
        assertNotNull(savedToken, "Токен должен быть сохранен в ордер3");
        Response response = AuthApi.logout(savedToken);

        assertEquals(200, response.statusCode(),
                "Статус код должен быть 200 при успешном выходе");
        assertEquals("success logout", response.getBody().asString(),
                "Тело ответа должно содержать success logout");
    }
}