package utils;

import com.codeborne.selenide.WebDriverRunner;

public class BrowserMax {
    public static void maximizeToFullScreen() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            try {
                WebDriverRunner.getWebDriver().manage().window().maximize();
            } catch (Exception e) {
                System.out.println("Использован стандартный метод максимизации");
                WebDriverRunner.getWebDriver().manage().window().maximize();
            }
        }
    }}