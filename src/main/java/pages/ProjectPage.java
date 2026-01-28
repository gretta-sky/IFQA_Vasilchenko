package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectPage {

    private final SelenideElement projectsMenu = $x("//a[@id='browse_link']").as("Меню 'Проекты'");
    private final SelenideElement viewAllProjectsLink = $x("//a[contains(text(), 'View all projects') or contains(text(), 'Просмотр всех проектов')]").as("Ссылка 'View all projects'");
    private final SelenideElement projectSearchInput = $x("//input[@id='project-filter-text']").as("Поле поиска проектов");
    private final SelenideElement tasksCounter = $x("//div[@class='pager']//div[@class='showing']/span").as("Счетчик задач");
    private final SelenideElement createIssueButton = $x("//a[@id='create_link']").as("Кнопка 'Создать задачу'");
    private final SelenideElement foundProject = $x("//a[@title='Test' and @data-track-click='projects.browse.project']").as("Найти проект");
    private final SelenideElement Summary = $x("//input[@id='summary']").as("Описание");
    private final SelenideElement IssueSubmit = $x("//input[@id='create-issue-submit']").as("Кнопка Создать баг");
    private final SelenideElement AllTasksAndFilters = $x("//div[@id='full-issue-navigator']//a[@href='/issues/']").as("Все таски");
    private final SelenideElement ForTask = $x("//input[@id='searcher-query']").as("Поиск таска");
    private final SelenideElement Jira = $x("//img[@alt='Jira']").as("Главная страница");
    private final SelenideElement foundTest = $x("//a[@original-title='Test']").as("Найти заголовок Тест");
    private final SelenideElement CreateLink = $x("//a[@id='create_link']").as("Создать ссылку");
    private final SelenideElement Textarea = $x("//textarea[@id='labels-textarea']").as("Поле для текста");
    private final SelenideElement MessageSuccess = $x("//div[contains(@class, 'aui-message-success')]").as("Сообщение об успехе");
    private final SelenideElement WikiEdit = $x("//div[@id='description-wiki-edit']//iframe").as("Описание");
    private final SelenideElement EnvironmentWikiEdit = $x("//div[@id='environment-wiki-edit']//iframe").as("Окружение");
    private final SelenideElement dropdownIconXpath = $x("//div[@id='issuelinks-issues-multi-select']//span[contains(@class, 'drop-menu')]").as("Выпадающее поле бага");
    private final SelenideElement dropdownSelectXpath = $x("//input[@id='customfield_10100-field' and @role='combobox']").as("Выпадающее поле выбора");
    private final SelenideElement IssueNavigator = $x("//div[@id='full-issue-navigator']//a[@href='/issues/']").as("Навигация по задачам");
    private final SelenideElement quickSearchInput = $x("//input[@id='quickSearchInput']").as("Быстрый поиск");
    private final SelenideElement issueActionWorkflow = $x("//a[@id='action_id_21' and contains(@class, 'issueaction-workflow-transition')]").as("Переключение статуса");
    private final SelenideElement clickWorkflow = $x("//a[@id='action_id_11' and contains(@class, 'issueaction-workflow-transition')]").as("Переключение статуса");
    private final SelenideElement businessProcess = $x("//a[@id='opsbar-transitions_more' and contains(@class, 'aui-button') and contains(@class, 'aui-dropdown2-trigger')]").as("Статус 'В процессе'");
    private final SelenideElement businessProcessDone = $x("//aui-item-link[@id='action_id_31']").as("Статус выполнено");
    private final SelenideElement finalStatusFinal = $x("//span[contains(@class, 'jira-issue-status-lozenge')]").as("Статус выполнено в баге");
    private final SelenideElement versionSelectFix = $("select#fixVersions").as("Выбор версии");
    private final SelenideElement versionSelect = $("select#versions").as("Выбор версии");
    private final SelenideElement severitySelect = $("select#customfield_10400").as("Выбор серьёзности");

    @Step("Открыть проект")
    public ProjectPage openProject(String projectName) {
        projectsMenu.shouldBe(visible, Duration.ofSeconds(5)).click();
        viewAllProjectsLink.shouldBe(visible, Duration.ofSeconds(5)).click();
        projectSearchInput.shouldBe(visible, Duration.ofSeconds(5))
                .setValue(projectName)
                .pressEnter();
        String projectKey = projectName.toUpperCase();
        foundProject.shouldBe(visible, Duration.ofSeconds(5));
        foundProject.click();
        checkProjectIsOpened(projectName);
        return this;
    }

    @Step("Проверить открытие проекта")
    private void checkProjectIsOpened(String projectName) {
        boolean isOpened = false;
        String currentUrl = url().toLowerCase();
        if (currentUrl.contains(projectName.toLowerCase())) {
            isOpened = true;
        }
        if (!isOpened) {
            throw new AssertionError("Проект '" + projectName + "' не открылся. " + "Текущий URL: " + url() + ", заголовок: " + title());
        }
    }

    @Step("Получить значение со счётчика")
    public int getTasksCount() {
        try {
            String counterText = tasksCounter.shouldBe(visible, Duration.ofSeconds(5))
                    .getText();
            return extractTotalCount(counterText);
        } catch (Exception e) {
            return 0;
        }
    }

    @Step("Получить число со счётчика")
    private int extractTotalCount(String text) {
        try {
            if (text.contains("из")) {
                String[] parts = text.split("из");
                return Integer.parseInt(parts[1].trim());
            } else if (text.contains("of")) {
                String[] parts = text.split("of");
                return Integer.parseInt(parts[1].trim());
            }
        } catch (Exception e) {
            throw new AssertionError("Не удалось получить число");
        }
        return 0;
    }

    @Step("Создать новую задачу")
    public ProjectPage createNewTask(String summary) {
        createIssueButton.shouldBe(visible, Duration.ofSeconds(5)).click();
        Summary.shouldBe(visible, Duration.ofSeconds(5)).setValue(summary);
        IssueSubmit.shouldBe(visible, Duration.ofSeconds(5)).click();
        return this;
    }

    @Step("Получить счётчик задач")
    public String getTasksCounterText() {
        return tasksCounter.shouldBe(visible, Duration.ofSeconds(5)).getText();
    }

    @Step("Открыть все задачи")
    public void openAllTasksAndFilters() {
        AllTasksAndFilters.shouldBe(visible, Duration.ofSeconds(5)).click();
    }

    @Step("Поиск задачи")
    public void searchForTask() {
        ForTask.shouldBe(visible)
                .setValue("TestSeleniumATHomework")
                .pressEnter();
    }

    @Step("Найти задачу")
    public void foundTest() {
        Jira.shouldBe(visible, Duration.ofSeconds(5)).click();
        projectsMenu.shouldBe(visible, Duration.ofSeconds(5)).click();
        viewAllProjectsLink.shouldBe(visible, Duration.ofSeconds(5)).click();
        foundTest.shouldBe(visible, Duration.ofSeconds(5)).click();
    }

    @Step("Создать детальный баг")
    public String createBugWithDetails(String bugTitle) {
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

    @Step("Добавить описание")
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

    @Step("Выбрать версию")
    private void selectVersion(String version) {
        versionSelectFix.selectOptionByValue("10001");
    }

    @Step("Выбрать версию")
    private void selectVersions(String version) {
        versionSelect.selectOptionByValue("10001");
    }

    @Step("Добавить описание")
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

    @Step("Выбрать вид бага")
    private void selectTaskFromDropdown() {
        dropdownIconXpath.shouldBe(visible)
                .click();
    }

    private void SelectEpic() {
        dropdownSelectXpath.shouldBe(visible)
                .click();
    }

    @Step("Выбрать серьёзность")
    private void selectSeverityS0() {
        severitySelect.selectOptionByValue("10104");
    }

    @Step("Получить ключ бага")
    private String extractBugKey(String message) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("([A-Z]+-\\d+)");
        java.util.regex.Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            String bugKey = matcher.group(1);
            return bugKey;
        }
        return "TEST-NEW";
    }

    @Step("Найти баг и провести по статусам")
    public void searchAndProcessBug(String bugTitle, String bugKey) {
        IssueNavigator.shouldBe(visible, Duration.ofSeconds(5)).click();
        quickSearchInput
                .shouldBe(visible)
                .setValue(bugTitle)
                .pressEnter();
        String currentBugKey = $x("//a[@id='key-val']").shouldBe(visible).getText();
        assertEquals(bugKey, currentBugKey, "Открыта не та задача!");
        executeWorkflow();
    }

    @Step("Привести баг к статусу выполнено")
    private void executeWorkflow() {
        clickWorkflowByXPath();
        clickInProgressByXPath();
        clickBusinessProcessDone();
        refresh();
        String finalStatus = finalStatusFinal
                .shouldBe(visible, Duration.ofSeconds(5))
                .getText();

        assertTrue(finalStatus.contains("ГОТОВО") ||
                        finalStatus.contains("ВЫПОЛНЕНО") ||
                        finalStatus.contains("CLOSED") ||
                        finalStatus.contains("RESOLVED") ||
                        finalStatus.contains("DONE"),
                "Задача не была закрыта. Текущий статус: " + finalStatus);
    }

    @Step("Поменять статус бага")
    private void clickWorkflowByXPath() {
        clickWorkflow.shouldBe(visible, Duration.ofSeconds(5))
                .click();
    }

    @Step("Поменять статус бага на 'В работе'")
    private void clickInProgressByXPath() {
        issueActionWorkflow
                .shouldBe(visible, Duration.ofSeconds(5))
                .click();
    }

    @Step("Поменять статус бага на 'Выполнено'")
    private void clickBusinessProcessDone() {
        businessProcess.shouldBe(visible, Duration.ofSeconds(10))
                .click();
        businessProcessDone.shouldBe(visible, Duration.ofSeconds(10))
                .click();
    }
}