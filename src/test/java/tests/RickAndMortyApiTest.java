package tests;

import api.RickAndMortyApi;
import models.Character;
import models.Episode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RickAndMortyApiTest extends BaseTest {

    @Test
    @DisplayName("Сравнение последнего персонажа из последнего эпизода Морти с самим Морти")
    void testMortyAndLastCharacterComparison() {
        try {
            Character morty = RickAndMortyApi.getCharacterByName("Morty Smith");
            assertNotNull(morty, "Морти Смит не найден");

            List<String> mortyEpisodes = morty.getEpisode();
            assertNotNull(mortyEpisodes, "Список эпизодов Морти не должен быть null");
            assertFalse(mortyEpisodes.isEmpty(), "У Морти должен быть хотя б один эпизод");

            String lastEpisodeUrl = mortyEpisodes.get(mortyEpisodes.size() - 1);

            Episode lastMortyEpisode = RickAndMortyApi.getEpisodeByUrl(lastEpisodeUrl);
            assertNotNull(lastMortyEpisode, "Эпизод не найден");
            Character lastCharacter = RickAndMortyApi.getLastCharacterFromEpisode(lastMortyEpisode);
            assertNotNull(lastCharacter, "Последний персонаж эпизода не найден!");
            String mortySpecies = morty.getSpecies();
            String lastCharSpecies = lastCharacter.getSpecies();

            String mortyLocation = morty.getLastKnownLocation() != null ?
                    morty.getLastKnownLocation().getName() : "null";
            String lastCharLocation = lastCharacter.getLastKnownLocation() != null ?
                    lastCharacter.getLastKnownLocation().getName() : "null";

            assertNotNull(mortySpecies, "У Морти должна быть указана раса");
            assertNotNull(lastCharSpecies, "У последнего персонажа должна быть указана раса");
            assertNotNull(morty.getLastKnownLocation(), "У Морти должна быть локация");
            assertNotNull(lastCharacter.getLastKnownLocation(),
                    "У последнего персонажа должна быть лока");

        } catch (Exception e) {
            fail("Тест упал с ошибкой: " + e.getMessage());
        }
    }
}