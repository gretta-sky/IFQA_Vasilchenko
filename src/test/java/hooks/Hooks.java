package hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static utils.utilsProperties.getBaseUrl;
import static utils.BrowserMax.maximizeToFullScreen;

public class Hooks {

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.baseUrl = getBaseUrl();
        Configuration.timeout = Duration.ofSeconds(15).toMillis();
        Configuration.browserSize = "1920x1080";
        Configuration.headless = false;
        Configuration.holdBrowserOpen = false;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        Configuration.browserCapabilities = options;
    }

    @Before
    public void openBlankPage() {
        Selenide.open("about:blank");
        System.out.println("Opened blank page");
    }

    @Before
    public void maximizeBrowser() {
        System.out.println("Maximizing browser window...");
        maximizeToFullScreen();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (WebDriverRunner.hasWebDriverStarted()) {
            Selenide.closeWebDriver();
        }
    }

    @After("@cleanup")
    public void cleanup() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }
}