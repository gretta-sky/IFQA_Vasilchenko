package tests;


import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import pages.LoginPage;
import pages.ProjectPage;

import static utils.utilsProperties.*;

public class BaseTest {

    @BeforeAll
    static void setup() {
        Configuration.browser = "chrome";
        Configuration.timeout = 15000;
        Configuration.baseUrl = getBaseUrl();
        Configuration.browserSize = "max";
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
