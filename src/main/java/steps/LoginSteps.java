package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import pages.LoginPage;
import pages.ProjectPage;

import static org.junit.jupiter.api.Assertions.*;
import static utils.utilsProperties.*;

public class LoginSteps {
    private LoginPage loginPage;
    private ProjectPage projectPage;

    @Given("я открываю страницу логина")
    public void openLoginPage() {
        loginPage = new LoginPage().open();
    }

    @When("я вхожу с валидными учетными данными")
    public void loginWithValidCredentials() {
        loginPage = new LoginPage().open();
        projectPage = loginPage.login(getUsername(), getPassword());
    }

    @When("я вхожу с логином {string} и паролем {string}")
    public void loginWithCredentials(String username, String password) {
        loginPage = new LoginPage().open();
        if (username.isEmpty() || password.isEmpty()) {
            loginPage.loginWithInvalidCreds(username, password);
        } else if (getUsername().equals(username) && getPassword().equals(password)) {
            projectPage = loginPage.login(username, password);
        } else {
            loginPage.loginWithInvalidCreds(username, password);
        }
    }

    @Then("я должен быть авторизован в системе")
    public void verifyUserIsLoggedIn() {
        assertTrue(loginPage.isUserLoggedIn(),
                "Пользователь должен быть авторизован");
    }

    @Then("я не должен быть авторизован в системе")
    public void verifyUserIsNotLoggedIn() {
        assertFalse(loginPage.isUserLoggedIn(),
                "Пользователь не должен быть авторизован при ошибке");
    }

    @Then("я должен увидеть сообщение об ошибке")
    public void verifyErrorMessageIsDisplayed() {
        assertTrue(loginPage.isErrorMessageDisplayed(),
                "Должно отображаться сообщение об ошибке");
    }

    @And("должен отображаться аватар пользователя")
    public void verifyUserAvatarIsDisplayed() {
        assertTrue(loginPage.isUserLoggedIn(),
                "После авторизации должен отображаться аватар пользователя");
    }
}