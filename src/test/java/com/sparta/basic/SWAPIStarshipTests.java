package com.sparta.basic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.framework.dto.StarshipCollectionDTO;
import com.sparta.framework.dto.StarshipDTO;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

public class SWAPIStarshipTests {

    private static HttpClient client;
    private static HttpRequest request;
    private static HttpResponse response;

    private static ObjectMapper mapper;
    private static StarshipDTO dto;

    private final static String URL = "https://swapi.dev/api/starships/2?format=json";

    @BeforeAll
    static void setupAll() {
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper();


        try {
            dto = mapper.readValue(new URL(URL), StarshipDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            request = HttpRequest
                    .newBuilder()
                    .uri(new URI(URL))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Nested
    @DisplayName("Status Code Tests")
    class StatusCodeTests {

        @Test
        @DisplayName("Test that status code is 200")
        void TestThatStatusCodeIs200() {

            Assertions.assertEquals(200, response.statusCode());
        }

    }

    @Nested
    @DisplayName("Response Body Tests")
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
                LocalDate date = LocalDate.parse(dto.getCreated(), DateTimeFormatter.ISO_DATE_TIME);
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
