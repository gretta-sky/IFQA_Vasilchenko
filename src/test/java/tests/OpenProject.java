package tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import pages.ProjectPage;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenProject extends BaseTest {

    @Test
    @Epic("Web интерфейс")
    @Feature("Открытие проекта")
    @Story("Успешное открытие проекта")
    @Description("Проверка успешного входа и открытия проекта")
    void testOpenProject() {
        ProjectPage projectPage = loginAndOpenProject();
        String pageTitle = WebDriverRunner.getWebDriver().getTitle();
        assertTrue(pageTitle.contains("Test") ||
                        pageTitle.contains("TEST") ||
                        pageTitle.contains("Jira"),
                "Заголовок: " + pageTitle);
    }
}