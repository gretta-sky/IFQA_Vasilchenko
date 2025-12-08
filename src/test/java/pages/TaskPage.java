package pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class TaskPage {
    private final SelenideElement statusField = $x(
            "//span[contains(@class, 'jira-issue-status-lozenge')]"
    );
    private final SelenideElement versionField = $x(
            "//span[@id='fixVersions-field']"
    );
    public String getStatus() {
        return statusField.shouldBe(visible).getText().trim();
    }
    public String getAffectedVersion() {
        return versionField.shouldBe(visible).getText().trim();
    }
}