package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    private final SelenideElement usernameInput = $x("//input[@id='login-form-username']").as("Логин");
    private final SelenideElement passwordInput = $x("//input[@id='login-form-password']").as("Пароль");
    private final SelenideElement loginButton = $x("//input[@id='login-form-submit']").as("Подтвердить");
    private final SelenideElement errorMessage = $x("//div[contains(@class, 'aui-message-error')]").as("Ошибка пароля или логина");
    private final SelenideElement userAvatar = $("a#header-details-user-fullname").as("Аватар пользователя");

    public LoginPage open() {
        Selenide.open("/login.jsp");
        return this;
    }
    public ProjectPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        userAvatar.shouldBe(visible, Duration.ofSeconds(10));
        return new ProjectPage();
    }
    public LoginPage loginWithInvalidCreds(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        errorMessage.shouldBe(visible, Duration.ofSeconds(10));
        return this;
    }

    public LoginPage enterUsername(String username) {
        usernameInput.shouldBe(visible).setValue(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    public void clickLogin() {
        loginButton.click();
    }
    public boolean isUserLoggedIn() {
        try {
            return userAvatar.exists() && userAvatar.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return errorMessage.exists() && errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessageText() {
        return errorMessage.getText();
    }
}