package pages;

import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
public class ProjectPage {

    private final SelenideElement projectsMenu = $x("//a[@id='browse_link']").as("Меню 'Проекты'");
    private final SelenideElement viewAllProjectsLink = $x("//a[contains(text(), 'View all projects') or contains(text(), 'Просмотр всех проектов')]").as("Ссылка 'View all projects'");
    private final SelenideElement projectSearchInput = $x("//input[@id='project-filter-text']").as("Поле поиска проектов");
    private final SelenideElement tasksCounter = $x("//div[@class='pager']//div[@class='showing']/span").as("Счетчик задач");
    private final SelenideElement createIssueButton = $x("//a[@id='create_link']").as("Кнопка 'Создать задачу'");
    private final SelenideElement foundProject = $x("//a[@title='Test' and @data-track-click='projects.browse.project']");
    private final SelenideElement Summary = $x("//input[@id='summary']").as("Summary");
    private final SelenideElement Issuesubmit = $x("//input[@id='create-issue-submit']").as("Issue submit");

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
    private void checkProjectIsOpened(String projectName) {
        boolean isOpened = false;
        String currentUrl = url().toLowerCase();
        if (currentUrl.contains(projectName.toLowerCase())) {
            isOpened = true;
        }
        if (!isOpened) {
            throw new AssertionError("Проект '" + projectName + "' не открылся. " +"Текущий URL: " + url() + ", заголовок: " + title());
        }
    }

    public int getTasksCount() {
        try {
            String counterText = tasksCounter.shouldBe(visible, Duration.ofSeconds(5))
                    .getText();
            return extractTotalCount(counterText);
        } catch (Exception e) {
         return 0;
        }
    }
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
            //
        }
        return 0;
    }
    public ProjectPage createNewTask(String summary) {
        createIssueButton.shouldBe(visible, Duration.ofSeconds(5)).click();
        Summary.shouldBe(visible, Duration.ofSeconds(5)).setValue(summary);
        Issuesubmit.shouldBe(visible, Duration.ofSeconds(5)).click();
        return this;
    }
    public String getTasksCounterText() {
        return tasksCounter.shouldBe(visible, Duration.ofSeconds(5)).getText();
    }
}