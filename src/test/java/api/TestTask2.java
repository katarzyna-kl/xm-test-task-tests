package api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.testng.annotations.Test;

public class TestTask2 {

  private final String baseURL = "https://swapi.dev/api/";

  private String filmWithDarthVaderAndLessPlanets;

  //Test Case 1 - Search for a person with the name Vader
  @Test
  public void shouldGetPersonByName() {
    given()
        .get(baseURL + "people/?search=vader")
        .then()
        .assertThat()
        .body("results[0].name", equalTo("Darth Vader"));
  }

  //Test Case 2 - Find which film that Darth Vader joined has the less planets
  @Test
  public void shouldGetFilmWithDarthVaderAndLessPlanets() {
    ValidatableResponse darthVaderDataResponse = given()
        .get(baseURL + "people/?search=vader")
        .then()
        .assertThat()
        .body("results.name[0]", equalTo("Darth Vader"));

    ArrayList<String> films = darthVaderDataResponse.extract().body().path("results[0].films");

    HashMap<String, Integer> filmsAndNumberOfPlanets = new HashMap<>();

    for (String film : films) {
      Response filmWithDarthVaderResponse = given()
          .get(film)
          .then()
          .extract().response();
      JsonPath jsonPath = new JsonPath(filmWithDarthVaderResponse.getBody().asString());
      filmsAndNumberOfPlanets.put(jsonPath.getString("title"), jsonPath.get("planets.size()"));
    }

    filmWithDarthVaderAndLessPlanets = filmsAndNumberOfPlanets
        .entrySet()
        .stream().min(Entry.comparingByValue(Comparator.naturalOrder()))
        .get().getKey();

    System.out.println(
        "Film with Darth Vader which has less planets: " + filmWithDarthVaderAndLessPlanets);
  }

  //Test Case 3 - Verify if Vader's starship is on film from test case 2
  @Test
  public void verifyIfVaderStarshipIsPresent() {
    ArrayList<String> starship = given()
        .get(baseURL + "people/?search=vader")
        .then()
        .extract().body().path("results[0].starships");

    given()
        .get(baseURL + "films/?search=" + filmWithDarthVaderAndLessPlanets)
        .then()
        .body("results[0].starships", hasItem(starship.get(0)));

  }

  //Test Case 4 - Find the oldest person ever played in all Star Wars films with less than 10
  // requests
  @Test
  public void findOldestPersonWhichPlayedInAllFilms() {
    //Get all Star Wars films
    Response allFilmsDataResponse = given()
        .get(baseURL + "films/")
        .then()
        .extract().response();
    JsonPath filmsJson = new JsonPath(allFilmsDataResponse.getBody().asString());

    int numberOfFilms = filmsJson.getInt("count");

    //Get list of people from the first movie
    List<String> peopleInFirstFilm = filmsJson.getList("results[0].characters");

    //Get list of people who played in all films - take people who played in first film and check if
    //they played in other films, if not remove from the list
    List<String> listOfPeoplePlayingInAllFilms = new ArrayList<>(peopleInFirstFilm);
    for (int i = 1; i < numberOfFilms; i++) {
      List<String> listOfPeople = filmsJson.getList("results[" + i + "].characters");
      listOfPeoplePlayingInAllFilms = listOfPeoplePlayingInAllFilms.stream()
          .filter(person -> listOfPeople.contains(person))
          .collect(Collectors.toList());
    }

    //Get birth years and names of people who played in all films.
    Map<String, Integer> birthYearAndPersonName = new HashMap<>();

    for (String person : listOfPeoplePlayingInAllFilms) {
      Response personDataResponse = given()
          .get(person)
          .then()
          .extract().response();
      JsonPath personDataJson = new JsonPath(personDataResponse.getBody().asString());

      String personName = personDataJson.getString("name");
      String birthYear = personDataJson.getString("birth_year");

      //Format date: BBY to negative values, ABY keep as positive
      int birthYearFormatted = 0;
      if (birthYear.contains("BBY")) {
        birthYearFormatted = Integer.parseInt(birthYear.replace("BBY", "")) * (-1);
      } else if (birthYear.contains("ABY")) {
        birthYearFormatted = Integer.parseInt(birthYear.replace("ABY", ""));
      }
      birthYearAndPersonName.put(personName, birthYearFormatted);
    }

    //Find the oldest person
    String oldestPerson = birthYearAndPersonName.entrySet()
        .stream().min(Entry.comparingByValue(Comparator.naturalOrder()))
        .get().getKey();

    System.out.println(
        "The oldest person which played in all Star Wars films is " + oldestPerson);
  }
}
