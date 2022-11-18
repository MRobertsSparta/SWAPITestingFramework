@feature
Feature: Testing scenario for starships
  Background:
    Given I make a request to the Star Wars API at Starships Endpoint

  @scenarioStatusStarship
  Scenario: Checking the Starships status code of a response
    When I check the status code of Starship response
    Then Status code for Starship response will be 200

  @scenarioServerNameStarship
  Scenario:  Check that the server header is nginx/1.16.1 for Starships dto
    When I check the header for the server name of Starship response
    Then header will be "nginx/1.16.1"

  @scenarioStarshipNameIsNotEmpty
  Scenario: Check that the starship name is not empty
    When I check the starship name
    Then Starship name will be not empty

  @scenarioStarshipCostInCreditsIsAValidNonNegativeInt
  Scenario: Check that the starship cost in credits is a non negative int
    When I check the starship cost in credits
    Then I will get a valid non negative in