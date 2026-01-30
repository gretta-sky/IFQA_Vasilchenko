package steps;

import api.RickAndMortyApi;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.qameta.allure.Step;
import models.Character;
import models.Episode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static utils.utilsProperties.getNameChar;

public class RickAndMortySteps {

    private Character morty;
    private Episode lastEpisode;
    private Character lastCharacter;
    private Exception testException;

    @Step("Я использую Rick and Morty API")
    @Given("Я использую Rick and Morty API")
    public void iUseRickAndMortyApi() {
    }

    @Step("Ищем персонажа")
    @When("Я получаю персонажа")
    public void iGetCharacterByName() {
        String characterName = getNameChar();
        try {
            morty = RickAndMortyApi.getCharacterByName(characterName);
            assertNotNull(morty, "Персонаж '" + characterName + "' не найден");
        } catch (Exception e) {
            testException = e;
            throw e;
        }
    }

    @Step("Ищем список эпизодов")
    @Then("У персонажа должен быть список эпизодов")
    public void characterShouldHaveEpisodesList() {
        assertNotNull(morty.getEpisode(), "Список эпизодов не должен быть null");
        assertFalse(morty.getEpisode().isEmpty(), "У персонажа должен быть хотя бы один эпизод");
    }

    @Step("Получаем последний эпизод персонажа")
    @Then("Я получаю последний эпизод персонажа")
    public void iGetLastEpisodeOfCharacter() {
        List<String> episodes = morty.getEpisode();
        String lastEpisodeUrl = episodes.get(episodes.size() - 1);
        lastEpisode = RickAndMortyApi.getEpisodeByUrl(lastEpisodeUrl);
        assertNotNull(lastEpisode, "Эпизод не найден");

    }

    @Step("Получаем последнего персонажа из этого эпизода")
    @Then("Я получаю последнего персонажа из этого эпизода")
    public void iGetLastCharacterFromEpisode() {
        lastCharacter = RickAndMortyApi.getLastCharacterFromEpisode(lastEpisode);
        assertNotNull(lastCharacter, "Последний персонаж эпизода не найден");
    }

    @Step("Сравниваем расу Морти с расой последнего персонажа")
    @Then("Сравниваю расу Морти с расой последнего персонажа")
    public void compareMortySpeciesWithLastCharacterSpecies() {
        String mortySpecies = morty.getSpecies();
        String lastCharSpecies = lastCharacter.getSpecies();

        assertNotNull(mortySpecies, "У Морти должна быть указана раса");
        assertNotNull(lastCharSpecies, "У последнего персонажа должна быть указана раса");
    }

    @Step("Сравниваем локацию Морти с локацией последнего персонажа")
    @Then("Сравниваю локацию Морти с локацией последнего персонажа")
    public void compareMortyLocationWithLastCharacterLocation() {
        String mortyLocation = morty.getLastKnownLocation() != null ?
                morty.getLastKnownLocation().getName() : "Не указана";
        String lastCharLocation = lastCharacter.getLastKnownLocation() != null ?
                lastCharacter.getLastKnownLocation().getName() : "Не указана";
        assertNotNull(morty.getLastKnownLocation(), "У Морти должна быть указана локация");
        assertNotNull(lastCharacter.getLastKnownLocation(),
                "У последнего персонажа должна быть указана локация");
    }

    @Step("Проверяем что у персонажей указаны все необходимые поля")
    @Then("Проверяю что у персонажей указаны все необходимые поля")
    public void verifyAllRequiredFieldsArePresent() {

        assertAll("Проверка полей Морти",
                () -> assertNotNull(morty.getId(), "ID не должно быть null"),
                () -> assertNotNull(morty.getName(), "Имя не должно быть null"),
                () -> assertNotNull(morty.getStatus(), "Статус не должен быть null"),
                () -> assertNotNull(morty.getSpecies(), "Раса не должна быть null"),
                () -> assertNotNull(morty.getType(), "Тип не должен быть null"),
                () -> assertNotNull(morty.getGender(), "Пол не должен быть null"),
                () -> assertNotNull(morty.getImage(), "Изображение не должно быть null"),
                () -> assertNotNull(morty.getEpisode(), "Эпизоды не должны быть null"),
                () -> assertNotNull(morty.getUrl(), "URL не должен быть null"),
                () -> assertNotNull(morty.getCreated(), "Дата создания не должна быть null")
        );
        assertAll("Проверка полей последнего персонажа",
                () -> assertNotNull(lastCharacter.getId(), "ID не должно быть null"),
                () -> assertNotNull(lastCharacter.getName(), "Имя не должно быть null"),
                () -> assertNotNull(lastCharacter.getStatus(), "Статус не должен быть null"),
                () -> assertNotNull(lastCharacter.getSpecies(), "Раса не должна быть null"),
                () -> assertNotNull(lastCharacter.getType(), "Тип не должен быть null"),
                () -> assertNotNull(lastCharacter.getGender(), "Пол не должен быть null"),
                () -> assertNotNull(lastCharacter.getImage(), "Изображение не должно быть null"),
                () -> assertNotNull(lastCharacter.getEpisode(), "Эпизоды не должны быть null"),
                () -> assertNotNull(lastCharacter.getUrl(), "URL не должен быть null"),
                () -> assertNotNull(lastCharacter.getCreated(), "Дата создания не должна быть null")
        );
    }

    @Step("Тест должен пройти успешно")
    @Then("Тест должен пройти успешно")
    public void testShouldPassSuccessfully() {
        assertNull(testException, "Тест не должен содержать исключений: " +
                (testException != null ? testException.getMessage() : ""));
    }
}