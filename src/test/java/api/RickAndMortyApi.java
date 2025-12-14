package api;

import io.restassured.response.Response;
import models.Character;
import models.Episode;

import java.util.List;

import static io.restassured.RestAssured.given;

public class RickAndMortyApi {
    public static Character getCharacterById(int id) {
        return given()
                .when()
                .get("/character/" + id)
                .then()
                .extract()
                .as(Character.class);
    }

    public static Character getCharacterByName(String name) {
        Response response = given()
                .queryParam("name", name)
                .when()
                .get("/character")
                .then()
                .extract()
                .response();

        if (response.statusCode() != 200) {
            throw new RuntimeException("API вернул: " + response.statusCode());
        }
        if (response.jsonPath().getList("results").isEmpty()) {
            throw new RuntimeException("Персонаж '" + name + "' не найден");
        }
        String characterUrl = response.jsonPath().getString("results[0].url");
        int characterId = extractIdFromUrl(characterUrl);
        return getCharacterById(characterId);
    }

    public static Episode getEpisodeById(int id) {
        return given()
                .when()
                .get("/episode/" + id)
                .then()
                .extract()
                .as(Episode.class);
    }

    public static Episode getEpisodeByUrl(String url) {
        return getEpisodeById(extractIdFromUrl(url));
    }

    public static int extractIdFromUrl(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL пустой");
        }
        String[] parts = url.split("/");
        return Integer.parseInt(parts[parts.length - 1]);
    }

    public static Character getLastCharacterFromEpisode(Episode episode) {
        List<String> characterUrls = episode.getCharacters();
        if (characterUrls == null || characterUrls.isEmpty()) {
            throw new RuntimeException("В эпизоде нет персонажей");
        }
        String lastCharacterUrl = characterUrls.get(characterUrls.size() - 1);
        int lastCharacterId = extractIdFromUrl(lastCharacterUrl);
        return getCharacterById(lastCharacterId);
    }
}