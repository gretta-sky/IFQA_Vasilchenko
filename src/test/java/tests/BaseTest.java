package tests;


import com.codeborne.selenide.Configuration;
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
        Configuration.browser = "chrome";
        Configuration.timeout = 15000;
        Configuration.baseUrl = getBaseUrl();

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
