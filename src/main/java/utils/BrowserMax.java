package utils;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;

public class BrowserMax {
    public static void maximizeToFullScreen() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            try {
                WebDriverRunner.getWebDriver().manage().window().maximize();
            } catch (Exception e) {
                Configuration.browserSize = "1920x1080";
            }
        }
    }
}