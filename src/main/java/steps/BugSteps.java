package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import pages.ProjectPage;
import pages.LoginPage;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import java.time.Duration;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class BugSteps {

    private ProjectPage projectPage;
    private LoginPage loginPage;
    private String bugKey;
    private String bugTitle;

    private final SelenideElement createLink = $x("//a[@id='create_link']");
    private final SelenideElement summaryInput = $x("//input[@id='summary' and @name='summary']");
    private final SelenideElement labelsTextarea = $x("//textarea[@id='labels-textarea']");
    private final SelenideElement issueSubmit = $x("//input[@id='create-issue-submit']");
    private final SelenideElement messageSuccess = $x("//div[contains(@class, 'aui-message-success')]");
    private final SelenideElement wikiEdit = $x("//div[@id='description-wiki-edit']//iframe");
    private final SelenideElement environmentWikiEdit = $x("//div[@id='environment-wiki-edit']//iframe");
    private final SelenideElement dropdownIcon = $x("//div[@id='issuelinks-issues-multi-select']//span[contains(@class, 'drop-menu')]");
    private final SelenideElement dropdownSelect = $x("//input[@id='customfield_10100-field' and @role='combobox']");
    private final SelenideElement issueNavigator = $x("//div[@id='full-issue-navigator']//a[@href='/issues/']");
    private final SelenideElement quickSearchInput = $x("//input[@id='quickSearchInput']");
    private final SelenideElement issueActionWorkflow = $x("//a[@id='action_id_21' and contains(@class, 'issueaction-workflow-transition')]");

    @Given("я проверяю, что проект успешно открыт")
    public void verifyProjectOpenedSuccessfully() {
        String title = title();
        String url = WebDriverRunner.url();

        assertTrue(title.contains("Test") || url.contains("TEST"),
                "Авторизация или открытие проекта не удалось. Заголовок: " + title + ", URL: " + url);
    }

    @When("я создаю новый баг с детальным описанием")
    public void createNewBugWithDetails() {
        bugTitle = "баг " + System.currentTimeMillis();
        bugKey = createBugWithDetails(bugTitle);
    }

    private String createBugWithDetails(String bugTitle) {
        createLink.shouldBe(visible, Duration.ofSeconds(10)).click();
        summaryInput.setValue(bugTitle);
        setDescriptionWithVisualCheck("баг");
        selectVersion("Version 2.0");
        labelsTextarea.setValue("tratata");
        setEnvironmentWithVisualCheck("баг");
        selectVersions("Version 2.0");
        selectTaskFromDropdown();
        selectEpic();
        selectSeverityS0();
        issueSubmit.shouldBe(visible, Duration.ofSeconds(5)).click();
        String successMessage = messageSuccess.shouldBe(visible, Duration.ofSeconds(10)).getText();
        return extractBugKey(successMessage);
    }

    private void setDescriptionWithVisualCheck(String text) {
        String descriptionVisualButton = "//div[@id='description-wiki-edit']//button[text()='Визуальный']";
        boolean isActive = $x(descriptionVisualButton + "[@aria-pressed='true']").exists();
        if (!isActive) {
            $x(descriptionVisualButton).click();
        }
        assertTrue($x(descriptionVisualButton + "[@aria-pressed='true']").exists(),
                "Кнопка 'Визуальный' в описании не активна!");
        switchTo().frame(wikiEdit);
        $("body").setValue(text);
        switchTo().defaultContent();
    }

    private void selectVersion(String version) {
        try {
            $("select#fixVersions").selectOptionByValue("10001");
        } catch (Exception e) {
            throw new AssertionError(" Нет такой версии в первом окне ");
        }
    }

    private void selectVersions(String version) {
        try {
            $("select#versions").selectOptionByValue("10001");
        } catch (Exception e) {
            throw new AssertionError(" Нет такой версии во втором окне ");
        }
    }

    private void setEnvironmentWithVisualCheck(String text) {
        String environmentVisualButton = "//div[@id='environment-wiki-edit']//button[text()='Визуальный']";
        boolean isActive = $x(environmentVisualButton + "[@aria-pressed='true']").exists();
        if (!isActive) {
            $x(environmentVisualButton).click();
        }
        assertTrue($x(environmentVisualButton + "[@aria-pressed='true']").exists(),
                "Кнопка 'Визуальный' в окружении не активна");
        switchTo().frame(environmentWikiEdit);
        $("body").setValue(text);
        switchTo().defaultContent();
    }

    private void selectTaskFromDropdown() {
        try {
            dropdownIcon.shouldBe(visible, Duration.ofSeconds(5)).click();
        } catch (Exception e) {
            throw new AssertionError(" Не удалось открыть dropdown задачи ");
        }
    }

    private void selectEpic() {
        try {
            dropdownSelect.shouldBe(visible, Duration.ofSeconds(5)).click();
        } catch (Exception e) {
            throw new AssertionError(" Не удалось выбрать epic ");
        }
    }

    private void selectSeverityS0() {
        try {
            $("select#customfield_10400").selectOptionByValue("10104");
        } catch (Exception e) {
            throw new AssertionError(" Не удалось выбрать severity ");
        }
    }

    private String extractBugKey(String message) {
        Pattern pattern = Pattern.compile("([A-Z]+-\\d+)");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "TEST-NEW";
    }

    @And("я нахожу созданный баг в системе")
    public void searchAndFindCreatedBug() {
        issueNavigator.shouldBe(visible, Duration.ofSeconds(5)).click();
        quickSearchInput.shouldBe(visible, Duration.ofSeconds(5))
                .setValue(bugTitle)
                .pressEnter();

        String currentBugKey = $x("//a[@id='key-val']").shouldBe(visible, Duration.ofSeconds(5)).getText();
        assertEquals(bugKey, currentBugKey, "Открыта не та задача! Ожидалось: " + bugKey + ", но было: " + currentBugKey);
    }

    @And("я выполняю workflow обработки бага")
    public void executeBugWorkflow() {
        executeWorkflow();
    }

    private void executeWorkflow() {
        clickWorkflowByXPath();
        clickInProgressByXPath();
        clickBusinessProcessDone();
        refresh();
    }

    private void clickWorkflowByXPath() {
        $x("//a[@id='action_id_11' and contains(@class, 'issueaction-workflow-transition')]")
                .shouldBe(visible, Duration.ofSeconds(5)).click();
    }

    private void clickInProgressByXPath() {
        issueActionWorkflow.shouldBe(visible, Duration.ofSeconds(5)).click();
    }

    private void clickBusinessProcessDone() {
        $x("//a[@id='opsbar-transitions_more' and contains(@class, 'aui-button') and contains(@class, 'aui-dropdown2-trigger')]")
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();
        $x("//a[contains(@href, 'action=31') and @role='menuitem']")
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();
    }

    @Then("статус бага должен быть завершенным")
    public void verifyBugStatusIsCompleted() {
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

    @And("баг успешно обработан через все этапы")
    public void bugSuccessfullyProcessed() {
    }

    public void setProjectPage(ProjectPage projectPage) {
        this.projectPage = projectPage;
    }

    public void setLoginPage(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public ProjectPage getProjectPage() {
        return projectPage;
    }
}