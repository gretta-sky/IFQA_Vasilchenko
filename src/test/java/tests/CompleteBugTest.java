package tests;

import com.codeborne.selenide.WebDriverRunner;
import hooks.WebHooks;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProjectPage;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Создание бага")
public class CompleteBugTest extends WebHooks {
    @Test
    @Epic("Web интерфейс")
    @Feature("Полный автотест")
    @Story("Успешный автотест")
    @Description("Проверка полного создания бага")
    @Tag("ID-3")
    void completeBugScenario() {
        ProjectPage projectPage = executePreviousTests();
        String bugTitle = "баг " + System.currentTimeMillis();
        String bugKey = projectPage.createBugWithDetails(bugTitle);
        projectPage.searchAndProcessBug(bugTitle, bugKey);
    }
    private ProjectPage executePreviousTests() {
        ProjectPage projectPage = loginAndOpenProject();
        assertTrue(title().contains("Test") || WebDriverRunner.url().contains("TEST"),
                "Авторизация или открытие проекта не удалось");
        int initialCount = projectPage.getTasksCount();
        assertTrue(initialCount > 0, "В проекте нет задач");
        String taskName = "Test Task " + System.currentTimeMillis();
        projectPage.createNewTask(taskName);
        projectPage.foundTest();
        int newCount = projectPage.getTasksCount();
        assertEquals(initialCount + 1, newCount,
                "Счетчик не увеличился после создания задачи");
        return projectPage;
    }
}