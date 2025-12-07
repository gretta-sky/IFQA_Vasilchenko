package utils;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;

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

