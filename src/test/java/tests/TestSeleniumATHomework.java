package tests;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import pages.ProjectPage;
import pages.TaskPage;
import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestSeleniumATHomework extends BaseTest {

    private final SelenideElement AllTasksAndFilters = $x("//div[@id='full-issue-navigator']//a[@href='/issues/']").as("Все таски");
    private final SelenideElement ForTask = $x("//input[@id='searcher-query']").as("Поиск таска");

    @Test
    @Epic("Web интерфейс")
    @Feature("Проверка статуса и версии")
    @Story("Успешная проверка")
    @Description("Статус - в работе, версия 2.0")
    void testTaskDetails() {
        ProjectPage projectPage = loginAndOpenProject();
        openAllTasksAndFilters();
        searchForTask();
        TaskPage taskPage = new TaskPage();
        String status = taskPage.getStatus();
        System.out.println("Статус задачи: " + status);
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
        System.out.println("Версия: " + version);
        assertEquals("Version 2.0", version,
                "В версиях должно быть 'Version 2.0'. Фактически: " + version);
    }
    private void openAllTasksAndFilters() {
        AllTasksAndFilters.shouldBe(visible, Duration.ofSeconds(5)).click();
    }
    private void searchForTask() {
        ForTask.shouldBe(visible)
                .setValue("TestSeleniumATHomework")
                .pressEnter();
    }
}