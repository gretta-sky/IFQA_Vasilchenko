package tests;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import pages.ProjectPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

public class CheckCounter extends BaseTest {

    private final SelenideElement projectsMenu = $x("//a[@id='browse_link']").as("Меню 'Проекты'");
    private final SelenideElement viewAllProjectsLink = $x("//a[contains(text(), 'View all projects') or contains(text(), 'Просмотр всех проектов')]").as("Ссылка 'View all projects'");
    private final SelenideElement Jira = $x("//img[@alt='Jira']").as("Главная страница");
    private final SelenideElement foundTest = $x("//a[@original-title='Test']");

    @Test
    @Epic("Web интерфейс")
    @Feature("Проверка счётчика")
    @Story("Успешное изменение счётчика")
    @Description("Проверка изменения счётчика при создании бага")
    void testTaskCounterAfterCreation() {
        ProjectPage projectPage = loginAndOpenProject();
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
                        "Было: " + initialCount + ", стало: " + newCount);
        String counterText = projectPage.getTasksCounterText();
        assertTrue(counterText.contains(String.valueOf(newCount)) ||
                        extractNumber(counterText) == newCount,
                "Текст счетчика должен содержать новое количество задач");
    }
    private int extractNumber(String text) {
        try {
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\d+");
            java.util.regex.Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group());
            }
        } catch (Exception e) {
            fail("Не удалось получить число");
        }
        return 0;
    }
}