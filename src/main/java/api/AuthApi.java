package api;

import io.restassured.response.Response;
import models.Registration;
import static io.restassured.RestAssured.given;

public class AuthApi {

    private static final String BASE_PATH = "/api";

    public static Response register(Registration request) {
        return given()
                .contentType("application/json")
                .body(request)
                .when()
                .post(BASE_PATH + "/register");
    }

    public static Response login(Registration request) {
        return given()
                .contentType("application/json")
                .body(request)
                .when()
                .post(BASE_PATH + "/login");
    }

    public static Response logout(String token) {
        return given()
                .header("Authorization", token)
                .when()
                .get(BASE_PATH + "/logout");
    }

    public static Response logoutWithInvalidUUID() {
        String randomUUID = java.util.UUID.randomUUID().toString();
        return given()
                .header("Authorization", randomUUID)
                .when()
                .get(BASE_PATH + "/logout");
    }
}