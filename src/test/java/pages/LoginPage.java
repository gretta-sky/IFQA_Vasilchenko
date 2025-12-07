package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    private final SelenideElement usernameInput = $x("//input[@id='login-form-username']").as("Логин");
    private final SelenideElement passwordInput = $x("//input[@id='login-form-password']").as("Пароль");
    private final SelenideElement loginButton = $x("//input[@id='login-form-submit']").as("Подтвердить");
    private final SelenideElement errorMessage = $x("//div[contains(@class, 'aui-message-error')]").as("Ошибка пароля или логина");

    public LoginPage open() {
        Selenide.open("/login.jsp");
        return this;
    }

    public ProjectPage login(String username, String password) {
        usernameInput.shouldBe(visible).setValue(username);
        passwordInput.setValue(password);
        loginButton.click();

        $x("//a[@id='header-details-user-fullname']").shouldBe(visible);
        return new ProjectPage();
    }

    public LoginPage loginWithInvalidCreds(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();
        errorMessage.shouldBe(visible);
        return this;
    }
    public boolean isUserLoggedIn() {
        return $("a#header-details-user-fullname").exists();
    }
}