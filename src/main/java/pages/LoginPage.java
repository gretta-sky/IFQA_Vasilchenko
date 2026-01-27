package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    private final SelenideElement usernameInput = $x("//input[@id='login-form-username']").as("Логин");
    private final SelenideElement passwordInput = $x("//input[@id='login-form-password']").as("Пароль");
    private final SelenideElement loginButton = $x("//input[@id='login-form-submit']").as("Подтвердить");
    private final SelenideElement fullname = $x("//a[@id='header-details-user-fullname']").as("Пользователь");

    @Step("Открыть страницу авторизации")
    public LoginPage open() {
        Selenide.open("/login.jsp");
        return this;
    }

    @Step("Ввести данные")
    public ProjectPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        fullname.shouldBe(visible, Duration.ofSeconds(5));
        return new ProjectPage();
    }

    @Step("Ввести логин")
    private void enterUsername(String username) {
        usernameInput.shouldBe(visible).setValue(username);
        io.qameta.allure.Allure.getLifecycle().updateStep(step ->
                step.setName("Ввести логин: ***"));
    }

    @Step("Ввести пароль")
    private void enterPassword(String password) {
        passwordInput.setValue(password);
        io.qameta.allure.Allure.getLifecycle().updateStep(step ->
                step.setName("Ввести пароль: ***"));
    }

    @Step("Авторизоваться")
    private void clickLogin() {
        loginButton.click();
    }

    @Step("Проверить авторизацию")
    public boolean isUserLoggedIn() {
        return fullname.exists();
    }
}