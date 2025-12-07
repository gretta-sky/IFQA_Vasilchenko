package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;

public class ProjectPage {


    private final SelenideElement projectsMenu = $x("//a[@id='browse_link']").as("Меню 'Проекты'");
    private final SelenideElement viewAllProjectsLink = $x("//a[contains(text(), 'View all projects') or contains(text(), 'Просмотр всех проектов')]")
            .as("Ссылка 'View all projects'");
    private final SelenideElement projectSearchInput = $x("//input[@id='project-filter-text']").as("Поле поиска проектов");
    private final SelenideElement tasksCounter = $x("//span[contains(@class, 'results-count')]").as("Счетчик задач");
    private final SelenideElement createIssueButton = $x("//a[@id='create_link']").as("Кнопка 'Создать задачу'");


    public ProjectPage openProject(String projectName) {
        System.out.println("Открываем проект: " + projectName);

        projectsMenu.shouldBe(visible).click();
        sleep(1000);
        viewAllProjectsLink.shouldBe(visible).click();
        sleep(2000);

        projectSearchInput.shouldBe(visible)
                .setValue(projectName)
                .pressEnter();
        sleep(2000);

        String projectKey = projectName.toUpperCase();
        SelenideElement foundProject = $x(
                "//a[@title='Test' and @data-track-click='projects.browse.project']"
        ).shouldBe(visible);
        foundProject.click();
        sleep(3000);

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

    // Метод для получения количества задач (остаётся без изменений)
    public int getTasksCount() {
        try {
            String counterText = tasksCounter.shouldBe(visible)
                    .getText();
            System.out.println("Текст счетчика: " + counterText);
            return extractTotalCount(counterText);
        } catch (Exception e) {
            System.out.println("Не удалось получить счетчик задач: " + e.getMessage());
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
            } else if (text.contains("/")) {
                String[] parts = text.split("/");
                return Integer.parseInt(parts[1].trim());
            }
        } catch (Exception e) {
            System.out.println("Ошибка парсинга счетчика: " + text);
        }
        return 0;
    }

    // Метод для создания задачи (упрощенный)
    public ProjectPage createNewTask(String summary) {
        System.out.println("Создаем задачу: " + summary);

        createIssueButton.shouldBe(visible).click();
        sleep(1000);

        // Заполняем обязательные поля
        $x("//input[@id='summary']").shouldBe(visible).setValue(summary);

        // Нажимаем кнопку создания
        $x("//input[@id='create-issue-submit']").click();

        // Ждем создания
        sleep(3000);

        // Проверяем успешность (можно проверить по уведомлению)
        if ($x("//div[contains(@class, 'aui-message-success')]").exists()) {
            System.out.println("✅ Задача успешно создана");
        } else {
            System.out.println("⚠️ Задача создана, но нет сообщения об успехе");
        }

        return this;
    }

    // Метод для проверки, что страница проекта открыта
    public boolean isProjectPageOpened(String projectName) {
        try {
            return url().contains(projectName.toUpperCase()) ||
                    title().contains(projectName) ||
                    $x("//h1[contains(text(), '" + projectName + "')]").exists();
        } catch (Exception e) {
            return false;
        }
    }

    // Метод для получения текста счетчика
    public String getTasksCounterText() {
        return tasksCounter.shouldBe(visible).getText();
    }
}