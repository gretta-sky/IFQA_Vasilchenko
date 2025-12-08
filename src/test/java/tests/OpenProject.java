package tests;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Test;
import pages.ProjectPage;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenProject extends BaseTest {

    @Test
    void testOpenProject() {
        ProjectPage projectPage = loginAndOpenProject();
        String pageTitle = WebDriverRunner.getWebDriver().getTitle();
        assertTrue(pageTitle.contains("Test") ||
                        pageTitle.contains("TEST"),
                "Заголовок: " + pageTitle);
    }
}