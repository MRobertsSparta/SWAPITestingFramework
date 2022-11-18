package com.sparta.cucumber;

import com.sparta.framework.connection.ConnectionResponse;
import com.sparta.framework.dto.PlanetsDto;
import com.sparta.framework.dto.StarshipDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static com.sparta.framework.connection.ConnectionManager.from;

public class MyStarshipStepdefs {
    private static ConnectionResponse response;
    private static StarshipDTO dto;
    private int statusCode;
    private String header;
    private String name;

    @Given("I make a request to the Star Wars API at Starships Endpoint")
    public void iMakeARequestToTheStarWarsAPIAtStarshipsEndpoint() {
        response = from().baseURL().slash("starships").slash("2").getResponse();
        dto = response.getBodyAs(StarshipDTO.class);

    }

    @When("I check the status code of Starship response")
    public void iCheckTheStatusCodeOfStarshipResponse() {
        statusCode = response.getStatusCode();
    }

    @Then("Status code for Starship response will be {int}")
    public void statusCodeForStarshipResponseWillBe(int expected) {
        Assertions.assertEquals(expected, statusCode);
    }

    @When("I check the header for the server name of Starship response")
    public void iCheckTheHeaderForTheServerNameOfStarshipResponse() {
        header = response.getHeader("Server");
    }

    @Then("header will be {string}")
    public void headerWillBe(String expected) {
        Assertions.assertEquals(expected, header);
    }

    @When("I check the starship name")
    public void iCheckTheStarshipName() {
        name = dto.getName();
    }

    @Then("Starship name will be not empty")
    public void starshipNameWillBeNotEmpty() {
        assertThat(name, is(not(emptyString())));
    }

    @When("I check the starship cost in credits")
    public void iCheckTheStarshipCostInCredits() {
    }

    @Then("I will get a valid non negative in")
    public void iWillGetAValidNonNegativeIn() {
        assertThat(dto.isCostInCreditsAValidNonNegativeInt(), is(true));
    }
}
