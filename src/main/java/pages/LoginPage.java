package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    private final SelenideElement usernameInput = $x("//input[@id='login-form-username']").as("Логин");
    private final SelenideElement passwordInput = $x("//input[@id='login-form-password']").as("Пароль");
    private final SelenideElement loginButton = $x("//input[@id='login-form-submit']").as("Подтвердить");
    private final SelenideElement errorMessage = $x("//div[contains(@class, 'aui-message-error')]").as("Ошибка пароля или логина");
    private final SelenideElement fullname = $x("//a[@id='header-details-user-fullname']").as("Пользователь");

    public LoginPage open() {
        Selenide.open("/login.jsp");
        return this;
    }
    public ProjectPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        fullname.shouldBe(visible, Duration.ofSeconds(5));
        return new ProjectPage();
    }
    private void enterUsername(String username) {
        usernameInput.shouldBe(visible).setValue(username);
        io.qameta.allure.Allure.getLifecycle().updateStep(step ->
                step.setName("Ввести логин: ***"));
    }
    private void enterPassword(String password) {
        passwordInput.setValue(password);
        io.qameta.allure.Allure.getLifecycle().updateStep(step ->
                step.setName("Ввести пароль: ***"));
    }
    private void clickLogin() {
        loginButton.click();
    }
    public LoginPage loginWithInvalidCreds(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        loginButton.click();
        errorMessage.shouldBe(visible, Duration.ofSeconds(5));
        return this;
    }
    public boolean isUserLoggedIn() {
        return fullname.exists();
    }
}