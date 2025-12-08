package tests;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Test;
import pages.ProjectPage;
import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class CompleteBugTest extends BaseTest {

    @Test
    void completeBugScenario() {
        ProjectPage projectPage = executePreviousTests();
        String bugTitle = "баг " + System.currentTimeMillis();
        String bugKey = createBugWithDetails(bugTitle);
        searchAndProcessBug(bugTitle, bugKey);

    }
    private ProjectPage executePreviousTests() {
        ProjectPage projectPage = loginAndOpenProject();
        assertTrue(title().contains("Test") || WebDriverRunner.url().contains("TEST"),
                "Авторизация или открытие проекта не удалось");

        int initialCount = projectPage.getTasksCount();
        assertTrue(initialCount > 0, "В проекте нет задач");

        String taskName = "Test Task " + System.currentTimeMillis();
        projectPage.createNewTask(taskName);
        sleep(2000);
        refresh();
        sleep(3000);
        int newCount = projectPage.getTasksCount();
        assertEquals(initialCount + 1, newCount,
                "Счетчик не увеличился после создания задачи");
        return projectPage;
    }
        private String createBugWithDetails(String bugTitle) {
            $x("//a[@id='create_link']").shouldBe(visible).click();
            $x("//input[@id='summary' and @name='summary']").setValue(bugTitle);

            setDescriptionWithVisualCheck("баг");
            selectVersion("Version 2.0");
            $x("//textarea[@id='labels-textarea']").setValue("tratata");
            setEnvironmentWithVisualCheck("баг");
            selectVersions("Version 2.0");
            selectTaskFromDropdown();
            SelectEpic();
            selectSeverityS0();

            $x("//input[@id='create-issue-submit']").shouldBe(visible, Duration.ofSeconds(5)).click();

            String successMessage = $x("//div[contains(@class, 'aui-message-success')]").shouldBe(visible, Duration.ofSeconds(5)).getText();
            return extractBugKey(successMessage);
        }

    private void setDescriptionWithVisualCheck(String text) {
        String descriptionVisualButton =
                "//div[@id='description-wiki-edit']//button[text()='Визуальный']";
        boolean isActive = $x(descriptionVisualButton + "[@aria-pressed='true']").exists();

        if (!isActive) {
            $x(descriptionVisualButton).click();
        }
        assertTrue($x(descriptionVisualButton + "[@aria-pressed='true']").exists(),
                "Кнопка 'Визуальный' в описании не активна!");
        $x("//div[@id='description-wiki-edit']//iframe").shouldBe(visible);
        switchTo().frame(0);
        $("body").setValue(text);
        switchTo().defaultContent();
    }

    private void selectVersion(String version) {
        SelenideElement versionSelect = $("select#fixVersions");
        versionSelect.selectOptionByValue("10001");
    }
    private void selectVersions(String version) {
        SelenideElement versionSelect = $("select#versions");
        versionSelect.selectOptionByValue("10001");
    }

    private void setEnvironmentWithVisualCheck(String text) {
        String environmentVisualButton =
                "//div[@id='environment-wiki-edit']//button[text()='Визуальный']";
        boolean isActive = $x(environmentVisualButton + "[@aria-pressed='true']").exists();
        if (!isActive) {
            $x(environmentVisualButton).click();
        }

        assertTrue($x(environmentVisualButton + "[@aria-pressed='true']").exists(),
                "Кнопка 'Визуальный' в окружении не активна");
        $x("//div[@id='environment-wiki-edit']//iframe").shouldBe(visible);
        switchTo().frame($$("iframe").last());
        $("body").setValue(text);
        switchTo().defaultContent();
    }
    private void selectTaskFromDropdown() {
        String dropdownIconXpath = "//div[@id='issuelinks-issues-multi-select']//span[contains(@class, 'drop-menu')]";
        $x(dropdownIconXpath)
                .shouldBe(visible)
                .click();
    }
    private void SelectEpic() {
        String dropdownSelectXpath = "//input[@id='customfield_10100-field' and @role='combobox']";
        $x(dropdownSelectXpath)
                .shouldBe(visible)
                .click();
    }
        private void selectSeverityS0() {

            SelenideElement severitySelect = $("select#customfield_10400");
            severitySelect.selectOptionByValue("10104");
    }
        private String extractBugKey(String message) {
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("([A-Z]+-\\d+)");
            java.util.regex.Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                String bugKey = matcher.group(1);
                return bugKey;
            }
            return "TEST-NEW";
        }
        private void searchAndProcessBug(String bugTitle, String bugKey) {
            String xpath = "//div[@id='full-issue-navigator']//a[@href='/issues/']";
            $x(xpath).shouldBe(visible, Duration.ofSeconds(5)).click();
        $x("//input[@id='quickSearchInput']")
                .shouldBe(visible)
                .setValue(bugTitle)
                .pressEnter();
            String currentBugKey = $x("//a[@id='key-val']").shouldBe(visible).getText();
            assertEquals(bugKey, currentBugKey, "Открыта не та задача!");
            executeWorkflow();
        }
        private void executeWorkflow() {
            clickWorkflowByXPath();
            clickInProgressByXPath();
            clickBusinessProcessDone();
            refresh();
            String finalStatus = $x("//span[contains(@class, 'jira-issue-status-lozenge')]")
                    .shouldBe(visible, Duration.ofSeconds(5))
                    .getText();

            assertTrue(finalStatus.contains("ГОТОВО") ||
                            finalStatus.contains("ВЫПОЛНЕНО") ||
                            finalStatus.contains("CLOSED") ||
                            finalStatus.contains("RESOLVED") ||
                            finalStatus.contains("DONE"),
                    "Задача не была закрыта. Текущий статус: " + finalStatus);
        }
    private void clickWorkflowByXPath() {
        $x("//a[@id='action_id_11' and contains(@class, 'issueaction-workflow-transition')]")
                .shouldBe(visible, Duration.ofSeconds(5))
                .click();
    }
    private void clickInProgressByXPath() {
        $x("//a[@id='action_id_21' and contains(@class, 'issueaction-workflow-transition')]")
                .shouldBe(visible, Duration.ofSeconds(5))
                .click();
    }
    private void clickBusinessProcessDone() {
        $x("//a[@id='opsbar-transitions_more']")
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();
        $x("//*[@id='action_id_31']")
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();
      }
    }





