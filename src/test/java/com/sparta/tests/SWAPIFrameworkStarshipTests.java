package com.sparta.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import static com.sparta.framework.connection.ConnectionManager.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.sparta.framework.connection.ConnectionResponse;
import com.sparta.framework.dto.StarshipCollectionDTO;
import com.sparta.framework.dto.StarshipDTO;
import io.restassured.internal.UriValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class SWAPIFrameworkStarshipTests {

    private static StarshipCollectionDTO collectionDTO;
    private static StarshipDTO dto;
    private static ConnectionResponse collectionResponse;
    private static ConnectionResponse response;


    @BeforeAll
    static void setupAll() {
        collectionResponse = from().baseURL().slash("starships").getResponse();
        response = from().baseURL().slash("starships").slash("2").getResponse();


        collectionDTO = collectionResponse.getBodyAs(StarshipCollectionDTO.class);
        dto = response.getBodyAs(StarshipDTO.class);
    }

    @Nested
    @DisplayName("Starship Collection Tests")
    class StarShipCollectionTests {

        @Nested
        @DisplayName("Status Code Tests")
        class StatusCodeTests {

            @Test
            @DisplayName("Test that status code is 200")
            void TestThatStatusCodeIs200() {
                Assertions.assertEquals(200, collectionResponse.getStatusCode());
            }

        }

        @Nested
        @DisplayName("Response Header Tests")
        class ResponseHeaderTests {
            @Test
            @DisplayName("Check that header server is nginx")
            void checkHeaderServerNginx(){
                Assertions.assertEquals("nginx/1.16.1", collectionResponse.getHeader("Server"));
            }
        }

        @Nested
        class ResponseBodyTests {
            @Test
            @DisplayName("Check count is not lower than 0")
            void checkCountNotLowerThanZero(){
                Assertions.assertTrue(collectionDTO.isCountGreaterThanOrEqualsZero());
            }

            @Test
            @DisplayName("Check that result length is greater than 0 if count is also greater than 0")
            void checkResultLengthGreaterThanZero(){
                Assumptions.assumeTrue(collectionDTO.isCountGreaterThanZero());
                Assertions.assertTrue(collectionDTO.isResultLengthGreaterThanZero());
            }

            @Test
            @DisplayName("Check list of starships size matches the count")
            void checkStarshipsCountMatchesListTotal(){
                Assertions.assertTrue(collectionDTO.isTotalOfResultsEqualToCount());
            }

            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("Check that results list is not empty or null")
            void checkStarshipListIsNotNullOrEmpty(List<StarshipDTO> testList){
                Assertions.assertNotEquals(testList, collectionDTO.getResults());
            }

            @Test
            @DisplayName("Check that the Next link is valid or null")
            void checkThatNextLinkIsValid(){
                Assertions.assertTrue(collectionDTO.getNext() == null || UriValidator.isUri(collectionDTO.getNext()));
            }

            @Test
            @DisplayName("Check that the Previous link is valid or null")
            void checkThatPreviousLinkIsValid(){
                Assertions.assertTrue(collectionDTO.getPrevious() == null || UriValidator.isUri(collectionDTO.getPrevious()));
            }
        }
    }

    @Nested
    @DisplayName("Starship Tests")
    class StarShipTests {

        @Nested
        @DisplayName("Status Code Tests")
        class StatusCodeTests {

            @Test
            @DisplayName("Test that status code is 200")
            void TestThatStatusCodeIs200() {
                Assertions.assertEquals(200, response.getStatusCode());
            }

        }

        @Nested
        @DisplayName("Response Header Tests")
        class ResponseHeaderTests {
            @Test
            @DisplayName("Check that header server is nginx")
            void checkHeaderServerNginx(){
                Assertions.assertEquals("nginx/1.16.1", response.getHeader("Server"));
            }
        }

        @Nested
        class ResponseBodyTests {
            @Test
            @DisplayName("Test that name is not empty")
            void TestThatNameIsNotEmptyString() {
                assertThat(dto.getName(), is(not(emptyString())));
            }

            @Test
            @DisplayName("Test that name is not null")
            void TestThatNameIsNotNull() {
                assertThat(dto.getName(), is(notNullValue()));
            }

            @Test
            @DisplayName("Test that cost in credits is not empty")
            void TestThatCostInCreditsIsNotEmptyString() {
                assertThat(dto.getCostInCredits(), is(not(emptyString())));
            }

            @Test
            @DisplayName("Test that cost in credits is not null")
            void TestThatCostInCreditsIsNotNull() {
                assertThat(dto.getCostInCredits(), is(notNullValue()));
            }

            @Test
            @DisplayName("Test that cost in credits is valid non negative int")
            void TestThatCostInCreditsIsValidNonNegativeInt() {
                assertThat(Integer.parseInt(dto.getCostInCredits()), is(not(lessThan(0))));
            }

            @Test
            @DisplayName("Test that max atmosphering speed is not empty")
            void TestThatMaxAtmospheringSpeedIsNotEmptyString() {
                assertThat(dto.getMaxAtmospheringSpeed(), is(not(emptyString())));
            }

            @Test
            @DisplayName("Test that max atmosphering speed is not null")
            void TestThatMaxAtmospheringSpeedIsNotNull() {
                assertThat(dto.getMaxAtmospheringSpeed(), is(notNullValue()));
            }

            @Test
            @DisplayName("Test that max atmosphering speed is parsable to int or is N/A")
            void TestThatMaxAtmospheringSpeedIsParsableToIntOrIsNA() {
                String maxAtmospheringSpeed = dto.getMaxAtmospheringSpeed();
                boolean acceptableValue = false;

                try {
                    Integer.parseInt(maxAtmospheringSpeed);
                    acceptableValue = true;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                if (maxAtmospheringSpeed.equals("N/A")) {
                    acceptableValue = true;
                }

                assertThat(acceptableValue, is(true));
            }

            @Test
            @DisplayName("Test that films is not null")
            void TestThatFilmsIsNotNull() {
                assertThat(dto.getFilms(), is(notNullValue()));
            }

            @Test
            @DisplayName("Test that created date is not empty")
            void TestThatCreatedDateIsNotEmptyString() {
                assertThat(dto.getCreated(), is(not(emptyString())));
            }

            @Test
            @DisplayName("Test that created date is not null")
            void TestThatCreatedDateIsNotNull() {
                assertThat(dto.getCreated(), is(notNullValue()));
            }

            @Test
            @DisplayName("Test that created date is valid according to ISO 8601")
            void TestThatCreatedDateIsValidForISO8601() {
                boolean parsableDate = false;

                try {
                    LocalDate.parse(dto.getCreated(), DateTimeFormatter.ISO_DATE_TIME);
                    parsableDate = true;
                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                }

                assertThat(parsableDate, is(true));
            }

            @Test
            @DisplayName("Test that url is not empty")
            void TestThatUrlIsNotEmptyString() {
                assertThat(dto.getUrl(), is(not(emptyString())));
            }

            @Test
            @DisplayName("Test that url is not null")
            void TestThatUrlIsNotNull() {
                assertThat(dto.getUrl(), is(notNullValue()));
            }

            @Test
            @DisplayName("Test that url is valid and part of api")
            void TestThatUrlIsValidAndPartOfAPI() {
                assertThat(dto.getUrl(), matchesPattern("(https?:\\/{2}swapi\\.dev\\/api\\/[a-zA-Z\\d.\\-_~:\\/?#\\[\\]@!$&'()*,+;%=]*)?"));
            }
        }

    }


}
