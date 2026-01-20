package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import pages.LoginPage;
import pages.ProjectPage;
import utils.BrowserMax;
import static com.codeborne.selenide.Selenide.open;
import static utils.utilsProperties.*;

public class BaseTest {
    @BeforeAll
    static void setup() {
        String driverPath = System.getProperty("webdriver.chrome.driver");
        Configuration.browser = "chrome";
        Configuration.timeout = 15000;
        Configuration.baseUrl = getBaseUrl();
    }

    @BeforeAll
    public static void setUpAllure() {
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(false)
                        .savePageSource(true)
                        .includeSelenideSteps(true)
        );
    }
    @BeforeEach
    void setupEach() {
        open("about:blank");
        BrowserMax.maximizeToFullScreen();
    }
    protected LoginPage openLoginPage() {
        return new LoginPage().open();
    }
    protected ProjectPage loginAndOpenProject() {
        return openLoginPage()
                .login(getUsername(), getPassword())
                .openProject(get("projectName"));
    }
}