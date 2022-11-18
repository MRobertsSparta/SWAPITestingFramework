package com.sparta.cucumber;

import com.sparta.framework.connection.ConnectionResponse;
import com.sparta.framework.dto.PlanetsDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;

import static com.sparta.framework.connection.ConnectionManager.from;
import static java.lang.Character.isUpperCase;

public class MyStepdefs {

    private static ConnectionResponse response;
    private static PlanetsDto dto;
    private int statusCode;
    private String header;
    private String name;
    private String rotationalPeriod;
    private String diameter;


    @Given("I make a request to the Star Wars API at Planets Endpoint")
    public void iMakeARequestToTheStarWarsAPIAtPlanetsEndpoint() {
        response = from().baseURL().slash("planets").slash("4").getResponse();
        dto = response.getBodyAs(PlanetsDto.class);
    }

    @When("I check the status code")
    public void iCheckTheStatusCode() {statusCode = response.getStatusCode();
    }

    @Then("it will be {int}")
    public void itWillBe(int expected) {
        Assertions.assertEquals(expected, statusCode);
    }

    @When("I check the header for the server name")
    public void iCheckTheHeaderForTheServerName() {
        header = response.getHeader("Server");
    }

    @Then("I will get {string}")
    public void iWillGetNginx(String expected) {
        Assertions.assertEquals(expected, header);
    }

    @When("I get the Name")
    public void iGetTheName() {
        name = dto.getName();
    }

    @Then("I will have an upper case letter on the first word")
    public void iWillHaveAnUpperCaseLetterOnTheFirstWord() {
        Assertions.assertTrue(isUpperCase(name.charAt(0)));
    }

    @Then("Name will not be null")
    public void nameWillNotBeNull() {
        Assertions.assertTrue(name.length() > 1);
    }

    @When("I get the Rotational Period")
    public void iGetTheRotationalPeriod() {
        rotationalPeriod = dto.getRotationPeriod();
    }

    @Then("Rotational Period will be a valid integer greater than one or a String unknown")
    public void rotationalPeriodWillBeAValidIntegerGreaterThanOneOrAStringUnknown() {
        Assumptions.assumeTrue(NumberUtils.isParsable(rotationalPeriod));
        Assertions.assertTrue(Integer.parseInt(rotationalPeriod) > 1);
    }

    @When("I get the Diameter")
    public void iGetTheDiameter() {
        diameter = dto.getDiameter();
    }

    @Then("Diameter will be a valid integer greater than one or a String unknown")
    public void diameterWillBeAValidIntegerGreaterThanOneOrAStringUnknown() {
        Assumptions.assumeTrue(NumberUtils.isParsable(diameter));
        Assertions.assertTrue(Integer.parseInt(diameter) > 1);
    }
}
