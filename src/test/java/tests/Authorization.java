package tests;

import hooks.WebHooks;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import pages.LoginPage;
import static org.junit.jupiter.api.Assertions.*;
import static utils.utilsProperties.getPassword;
import static utils.utilsProperties.getUsername;

@DisplayName("Авторизация")
public class Authorization extends WebHooks {
    @Test
    @Epic("Web интерфейс")
    @Feature("Авторизация")
    @Story("Успешная авторизация")
    @Description("Проверка успешного входа с валидными данными")
    @Tag("ID-1")
    void testSuccessfulAuthorization() {
        LoginPage loginPage = openLoginPage();
        loginPage.login(getUsername(), getPassword());
        assertTrue(loginPage.isUserLoggedIn(),
                "После авторизации должен отображаться аватар пользователя");
    }
}