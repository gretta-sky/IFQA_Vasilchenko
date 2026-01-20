package tests;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import pages.ProjectPage;
import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class CompleteBugTest extends BaseTest {

    private final SelenideElement projectsMenu = $x("//a[@id='browse_link']").as("Меню 'Проекты'");
    private final SelenideElement viewAllProjectsLink = $x("//a[contains(text(), 'View all projects') or contains(text(), 'Просмотр всех проектов')]").as("Ссылка 'View all projects'");
    private final SelenideElement Jira = $x("//img[@alt='Jira']").as("Пле поиска проектов");
    private final SelenideElement foundTest = $x("//a[@original-title='Test']");
    private final SelenideElement CreateLink = $x("//a[@id='create_link']");
    private final SelenideElement Summary = $x("//input[@id='summary' and @name='summary']");
    private final SelenideElement Textarea = $x("//textarea[@id='labels-textarea']");
    private final SelenideElement IssueSubmit = $x("//input[@id='create-issue-submit']");
    private final SelenideElement MessageSuccess = $x("//div[contains(@class, 'aui-message-success')]");
    private final SelenideElement WikiEdit = $x("//div[@id='description-wiki-edit']//iframe");
    private final SelenideElement EnvironmentWikiEdit = $x("//div[@id='environment-wiki-edit']//iframe");
    private final SelenideElement dropdownIconXpath = $x("//div[@id='issuelinks-issues-multi-select']//span[contains(@class, 'drop-menu')]");
    private final SelenideElement dropdownSelectXpath = $x("//input[@id='customfield_10100-field' and @role='combobox']");
    private final SelenideElement IssueNavigator = $x("//div[@id='full-issue-navigator']//a[@href='/issues/']");
    private final SelenideElement quickSearchInput = $x("//input[@id='quickSearchInput']");
    private final SelenideElement issueActionWorkflow = $x("//a[@id='action_id_21' and contains(@class, 'issueaction-workflow-transition')]");

    @Test
    @Epic("Web интерфейс")
    @Feature("Полный автотест")
    @Story("Успешный автотест")
    @Description("Проверка полного создания бага")
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

        Jira.shouldBe(visible, Duration.ofSeconds(5)).click();
        projectsMenu.shouldBe(visible, Duration.ofSeconds(5)).click();
        viewAllProjectsLink.shouldBe(visible, Duration.ofSeconds(5)).click();
        foundTest.shouldBe(visible, Duration.ofSeconds(5)).click();

        int newCount = projectPage.getTasksCount();
        assertEquals(initialCount + 1, newCount,
                "Счетчик не увеличился после создания задачи");
        return projectPage;
    }
        private String createBugWithDetails(String bugTitle) {
            CreateLink.shouldBe(visible).click();
            Summary.setValue(bugTitle);
            setDescriptionWithVisualCheck("баг");
            selectVersion("Version 2.0");
            Textarea.setValue("tratata");
            setEnvironmentWithVisualCheck("баг");
            selectVersions("Version 2.0");
            selectTaskFromDropdown();
            SelectEpic();
            selectSeverityS0();
            IssueSubmit.shouldBe(visible, Duration.ofSeconds(5)).click();
            String successMessage = MessageSuccess.shouldBe(visible, Duration.ofSeconds(5)).getText();
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
        WikiEdit.shouldBe(visible);
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
        EnvironmentWikiEdit.shouldBe(visible);
        switchTo().frame($$("iframe").last());
        $("body").setValue(text);
        switchTo().defaultContent();
    }
    private void selectTaskFromDropdown() {
        dropdownIconXpath.shouldBe(visible)
                .click();
    }
    private void SelectEpic() {
        dropdownSelectXpath.shouldBe(visible)
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
            IssueNavigator.shouldBe(visible, Duration.ofSeconds(5)).click();
            quickSearchInput
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
        issueActionWorkflow
                .shouldBe(visible, Duration.ofSeconds(5))
                .click();
    }
    private void clickBusinessProcessDone() {
        $x("//a[@id='opsbar-transitions_more' and contains(@class, 'aui-button') and contains(@class, 'aui-dropdown2-trigger')]")
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();
        $x("//a[contains(@href, 'action=31') and @role='menuitem']")
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();
    }
}




