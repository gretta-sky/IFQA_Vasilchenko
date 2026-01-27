package tests;

import com.codeborne.selenide.WebDriverRunner;
import hooks.WebHooks;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProjectPage;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Открытие проекта")
public class OpenProject extends WebHooks {
    @Test
    @Epic("Web интерфейс")
    @Feature("Открытие проекта")
    @Story("Успешное открытие проекта")
    @Description("Проверка успешного входа и открытия проекта")
    @Tag("ID-4")
    void testOpenProject() {
        ProjectPage projectPage = loginAndOpenProject();
        String pageTitle = WebDriverRunner.getWebDriver().getTitle();
        assertTrue(pageTitle.contains("Test") ||
                        pageTitle.contains("TEST") ||
                        pageTitle.contains("Jira"),
                "Заголовок: " + pageTitle);
    }
}