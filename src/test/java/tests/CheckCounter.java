package tests;

import org.junit.jupiter.api.Test;
import pages.ProjectPage;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.*;

public class CheckCounter extends BaseTest {

    @Test
    void testTaskCounterAfterCreation() {
        ProjectPage projectPage = loginAndOpenProject();
        int initialCount = projectPage.getTasksCount();
        assertTrue(initialCount > 0, "В проекте нет задач");
        String taskName = "Test Task " + System.currentTimeMillis();
        projectPage.createNewTask(taskName);
        sleep(2000);
        refresh();
        sleep(3000);
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
        } catch (Exception e) { //
        }
        return 0;
    }
}