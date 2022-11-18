@feature
Feature: Testing scenario for planets

  Background:
    Given I make a request to the Star Wars API at Planets Endpoint

  @scenarioStatus
  Scenario: Checking the Planets status code of a response
    When I check the status code
    Then it will be 200

  @scenarioServerName
  Scenario:  Check that the server header is nginx/1.16.1
    When I check the header for the server name
    Then I will get "nginx/1.16.1"

  @scenarioNameUpperCase
  Scenario: check that the first letter of Name is upper case
    When I get the Name
    Then I will have an upper case letter on the first word

  @scenarioNameNotNull
  Scenario: check that the Name has a valid entry
    When I get the Name
    Then Name will not be null

  @scenarioRotationalPeriodValidNumberGreaterThanOneOrUnknown
  Scenario: check that the Rotational Period is valid
    When I get the Rotational Period
    Then Rotational Period will be a valid integer greater than one or a String unknown

  @scenarioDiameterValidNumberGreaterThanOneOrUnknown
  Scenario: check that the Diameter is valid
    When I get the Diameter
    Then Diameter will be a valid integer greater than one or a String unknown


