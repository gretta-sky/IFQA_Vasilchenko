package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.LoginPage;
import pages.ProjectPage;
import utils.BrowserMax;
import utils.utilsProperties;

import java.io.File;

import static com.codeborne.selenide.Selenide.open;
import static utils.utilsProperties.*;

public class BaseTest {
    @BeforeAll
    static void setup() {
        Configuration.browser = "chrome";
        Configuration.timeout = 15000;
        Configuration.baseUrl = getBaseUrl();
        String customChromeDriverPath = "webdriver.chrome.driver";
        ChromeOptions options = new ChromeOptions();
        Configuration.browserCapabilities = options;
        File chromeDriverFile = new File(customChromeDriverPath);
        if (chromeDriverFile.exists()) {
            try {
                System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());
                ChromeDriver driver = new ChromeDriver(options);
                WebDriverRunner.setWebDriver(driver);
            } catch (Exception e) {
                WebDriverManager.chromedriver().setup();
            }
        }
    }
    @BeforeAll
    public static void setUpAllure() {
        AllureSelenide allureSelenide = new AllureSelenide()
                .screenshots(utilsProperties.getBoolean("allure.screenshots"))
                .savePageSource(utilsProperties.getBoolean("allure.savePageSource"))
                .includeSelenideSteps(utilsProperties.getBoolean("allure.includeSelenideSteps"));
        SelenideLogger.addListener("AllureSelenide", allureSelenide);
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