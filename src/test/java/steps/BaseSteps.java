package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class BaseSteps {
    @Given("открыта главная страница")
    public void openMainPage() {
        open("/");
    }

    @Then("URL должен содержать {string}")
    public void verifyUrlContains(String expectedText) {
        String currentUrl = WebDriverRunner.url();
        assertTrue(currentUrl.contains(expectedText),
                "URL должен содержать '" + expectedText + "', но был: " + currentUrl);
    }

    @Then("заголовок страницы должен содержать {string}")
    public void verifyTitleContains(String expectedText) {
        String title = title();
        assertTrue(title.contains(expectedText),
                "Заголовок должен содержать '" + expectedText + "', но был: " + title);
    }

    @Given("я нахожусь на странице {string}")
    public void onPage(String pageUrl) {
        open(pageUrl);
    }

    @Given("я нахожусь на странице проекта")
    public void onProjectPage() {
        String url = WebDriverRunner.url();
        assertTrue(url.contains("browse") || url.contains("TEST"),
                "Должны находиться на странице проекта, но URL: " + url);
    }
}