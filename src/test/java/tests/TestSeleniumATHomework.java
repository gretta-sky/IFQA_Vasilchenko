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
import pages.TaskPage;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Проверка статуса задачи")
public class TestSeleniumATHomework extends WebHooks {
    @Test
    @Epic("Web интерфейс")
    @Feature("Проверка статуса и версии")
    @Story("Успешная проверка")
    @Description("Статус - в работе, версия 2.0")
    @Tag("ID-5")
    void testTaskDetails() {
        ProjectPage projectPage = loginAndOpenProject();
        projectPage.openAllTasksAndFilters();
        projectPage.searchForTask();
        TaskPage taskPage = new TaskPage();
        String status = taskPage.getStatus();
        assertTrue(status.equals("Сделать") ||
                        status.equals("В работе") ||
                        status.equals("To Do") ||
                        status.equals("In Progress")||
                        status.equals("СДЕЛАТЬ") ||
                        status.equals("В РАБОТЕ") ||
                        status.equals("TO DO") ||
                        status.equals("IN PROGRESS"),
                "Статус должен быть 'Сделать' или 'В работе'. Фактический: " + status);
        String version = taskPage.getAffectedVersion();
        assertEquals("Version 2.0", version,
                "В версиях должно быть 'Version 2.0'. Фактически: " + version);
    }
}