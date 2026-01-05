package steps;

import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import pages.ProjectPage;
import pages.LoginPage;
import com.codeborne.selenide.WebDriverRunner;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.*;
import static utils.utilsProperties.*;

public class ProjectSteps {

    private ProjectPage projectPage;
    private LoginPage loginPage;
    private int initialTaskCount;

    private final SelenideElement projectsMenu = $x("//a[@id='browse_link']").as("Меню 'Проекты'");
    private final SelenideElement viewAllProjectsLink = $x("//a[contains(text(), 'View all projects') or contains(text(), 'Просмотр всех проектов')]").as("Ссылка 'View all projects'");
    private final SelenideElement jiraLogo = $x("//img[@alt='Jira']").as("Главная страница");
    private final SelenideElement foundTest = $x("//a[@original-title='Test']");


    private String getProjectNameFromConfig() {
        return getProjectName();
    }

    @Given("я вхожу с уже валидными учетными данными")
    public void loginWithValidCredentials() {
        loginPage = new LoginPage().open();
        projectPage = loginPage.login(getUsername(), getPassword());
    }

    @Given("я авторизован в системе")
    public void userIsLoggedIn() {
        loginPage = new LoginPage().open();
        projectPage = loginPage.login(getUsername(), getPassword());
    }

    @Given("я открываю проект")
    public void openProject() {
        String projectName = getProjectNameFromConfig();

        if (projectPage == null) {
            loginPage = new LoginPage().open();
            projectPage = loginPage.login(getUsername(), getPassword());
        }
        projectPage = projectPage.openProject(projectName);
    }

    @When("я создаю новую задачу с уникальным именем")
    public void createNewTaskWithUniqueName() {
        String taskName = "Test Task " + System.currentTimeMillis();
        projectPage.createNewTask(taskName);
    }

    @Then("заголовок страницы должен содержать проект")
    public void verifyPageTitleContainsProjectOrJira() {
        String projectName = getProjectNameFromConfig();
        String projectNameUpper = projectName.toUpperCase();
        String pageTitle = WebDriverRunner.getWebDriver().getTitle();

        assertTrue(pageTitle.contains(projectName) ||
                        pageTitle.contains(projectNameUpper) ||
                        pageTitle.contains("Jira"),
                "Заголовок должен содержать '" + projectName + "', '" + projectNameUpper + "' или 'Jira', но был: " + pageTitle);
    }

    @And("я возвращаюсь на страницу проекта")
    public void returnToProjectPage() {
        String projectName = getProjectNameFromConfig();
        jiraLogo.shouldBe(visible, Duration.ofSeconds(5)).click();
        projectsMenu.shouldBe(visible, Duration.ofSeconds(5)).click();
        viewAllProjectsLink.shouldBe(visible, Duration.ofSeconds(5)).click();
        foundTest.shouldBe(visible, Duration.ofSeconds(5)).click();
        String currentUrl = WebDriverRunner.url();
        assertTrue(currentUrl.toLowerCase().contains(projectName.toLowerCase()),
                "Должны находиться на странице проекта '" + projectName +
                        "', но URL: " + currentUrl);
    }

    @Then("я должен находиться на странице проекта")
    public void verifyOnProjectPage() {
        String projectName = getProjectNameFromConfig();
        String currentUrl = WebDriverRunner.url();

        assertTrue(currentUrl.toLowerCase().contains(projectName.toLowerCase()),
                "URL должен содержать название проекта '" + projectName + "', но был: " + currentUrl);
    }

    @Then("счетчик задач должен отображаться")
    public void verifyTaskCounterIsDisplayed() {
        String counterText = projectPage.getTasksCounterText();
        assertNotNull(counterText, "Счетчик задач должен отображаться");
        assertFalse(counterText.isEmpty(), "Текст счетчика не должен быть пустым");
    }

    @Then("количество задач должно увеличиться на 1")
    public void verifyTaskCountIncreasedByOne() {
        int newCount = projectPage.getTasksCount();
        assertEquals(initialTaskCount + 1, newCount,
                "Количество задач должно увеличиться на 1. Было: " + initialTaskCount + ", стало: " + newCount);
    }

    @And("текст счетчика должен отображать новое количество")
    public void verifyCounterTextDisplaysNewCount() {
        int newCount = projectPage.getTasksCount();
        String counterText = projectPage.getTasksCounterText();
        assertTrue(counterText.contains(String.valueOf(newCount)) ||
                        extractNumberFromText(counterText) == newCount,
                "Текст счетчика должен содержать новое количество задач (" + newCount +
                        "), но был: " + counterText);
    }

    @And("я запоминаю текущее количество задач")
    public void rememberCurrentTaskCount() {
        initialTaskCount = projectPage.getTasksCount();
    }

    private int extractNumberFromText(String text) {
        try {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group());
            }
        } catch (Exception e) {
            throw new AssertionError(" Парсинг не удался ");
        }
        return 0;
    }
}