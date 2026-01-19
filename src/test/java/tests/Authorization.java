package tests;

import org.junit.jupiter.api.Test;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;
import static utils.utilsProperties.getPassword;
import static utils.utilsProperties.getUsername;

public class Authorization extends BaseTest {

    @Test
    void testSuccessfulAuthorization() {
        LoginPage loginPage = openLoginPage();
        loginPage.login(getUsername(), getPassword());
        assertTrue(loginPage.isUserLoggedIn(),
                "После авторизации должен отображаться аватар пользователя");
    }
}