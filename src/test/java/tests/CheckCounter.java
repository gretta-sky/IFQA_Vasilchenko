package tests;

import hooks.WebHooks;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProjectPage;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Счётчик задач")
public class CheckCounter extends WebHooks {
    @Test
    @Epic("Web интерфейс")
    @Feature("Проверка счётчика")
    @Story("Успешное изменение счётчика")
    @Description("Проверка изменения счётчика при создании бага")
    @Tag("ID-2")
    void testTaskCounterAfterCreation() {
        ProjectPage projectPage = loginAndOpenProject();
        int initialCount = projectPage.getTasksCount();
        assertTrue(initialCount > 0, "В проекте нет задач");
        String taskName = "Test Task " + System.currentTimeMillis();
        projectPage.createNewTask(taskName);
        projectPage.foundTest();
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