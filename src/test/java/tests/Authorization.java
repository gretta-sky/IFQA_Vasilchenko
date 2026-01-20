package tests;

import org.junit.jupiter.api.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;
import static utils.utilsProperties.getPassword;
import static utils.utilsProperties.getUsername;

@Epic("Web интерфейс")
@Feature("Авторизация")
public class Authorization extends BaseTest {

    @Test
    @Epic("Web интерфейс")
    @Feature("Авторизация")
    @Story("Успешная авторизация")
    @Description("Проверка успешного входа с валидными данными")
    void testSuccessfulAuthorization() {
        LoginPage loginPage = openLoginPage();
        loginPage.login(getUsername(), getPassword());
        assertTrue(loginPage.isUserLoggedIn(),
                "После авторизации должен отображаться аватар пользователя");
    }
}