package com.sparta.basic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.framework.dto.SpeciesCollectionDTO;
import com.sparta.framework.dto.SpeciesDTO;
import io.restassured.internal.UriValidator;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SWAPISpeciesTests {
    private static HttpClient client;
    private static HttpRequest request;
    private static HttpResponse<String> response;
    private static ObjectMapper mapper;

    private static SpeciesDTO speciesDTO;

    private static SpeciesCollectionDTO speciesCollectionDTO;

    @BeforeAll
    static void setUpAll() {
        mapper = new ObjectMapper();
        client = HttpClient.newHttpClient();
        try {
            request = HttpRequest
                    .newBuilder()
                    .uri(new URI("https://swapi.dev/api/species/"))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            speciesDTO = mapper.readValue(new URL("https://swapi.dev/api/species/31?format=json"), SpeciesDTO.class);
            speciesCollectionDTO = mapper.readValue(new URL("https://swapi.dev/api/species?format=json"), SpeciesCollectionDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Nested
    @DisplayName("Testing status code")
    class testStatusCode{

        @Test
        @DisplayName("Check that the Status code is 200")
        void checkThatTheStatusCodeIs200() {
            Assertions.assertEquals(200, response.statusCode());
        }
        @Test
        @DisplayName("Check that the Status code is not equal to 400")
        void checkThatTheStatusCodeIs400() {
            Assertions.assertNotEquals(400, response.statusCode());
        }
    }


    @Nested
    @DisplayName("Testing Header")
    class testHeader{

        @Test
        @DisplayName("Testing the response returns Header")
        void testingTheResponseReturnsHeader() {
            System.out.println(response.headers());
        }
        @Test
        @DisplayName("Testing the Header Server")
        void testingTheHeaders() {
            System.out.println(response.headers().firstValue("Server"));
            Assertions.assertEquals("GET, HEAD, OPTIONS", response.headers().firstValue("allow").orElse("Options not Found"));
        }

    }

    @Nested
    @DisplayName("Testing Body")
    class testBody {

        @Nested
        @DisplayName("Testing Collection")
        class SpeciesCollectionTests{
            @Test
            @DisplayName("Testing the response body")
            void testingTheResponseBody() {
                System.out.println(response.body());
            }

            @Test
            @DisplayName("Check list of species size matches the count")
            void checkSpeciesCountMatchesListTotal(){
                //list for storing vehicles
                List<SpeciesDTO> speciesList = new ArrayList<>();
                do
                {
                    speciesList.addAll(speciesCollectionDTO.getResults());
                    try {
                        //move onto next page
                        speciesCollectionDTO = mapper.readValue(new URL(speciesCollectionDTO.getNext()), SpeciesCollectionDTO.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //before stopping the loop, add the last results list
                    if(speciesCollectionDTO.getNext() == null){
                        speciesList.addAll(speciesCollectionDTO.getResults());
                    }
                }while(speciesCollectionDTO.getNext() != null);

                //now that all vehicles have been added to a list
                //compare size with the .getCount from the JSON response
                Assertions.assertEquals(speciesCollectionDTO.getCount(), speciesList.size());
            }

            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("Check that results list is not empty or null")
            void checkSpeciesListIsNotNullOrEmpty(List<SpeciesDTO> testList){
                Assertions.assertNotEquals(testList, speciesCollectionDTO.getResults());
            }

            @Test
            @DisplayName("Check that the Next link is valid or null")
            void checkThatNextLinkIsValid(){
                Assertions.assertTrue(speciesCollectionDTO.getNext() == null || UriValidator.isUri(speciesCollectionDTO.getNext()));
            }

            @Test
            @DisplayName("Check that the Previous link is valid or null")
            void checkThatPreviousLinkIsValid(){
                Assertions.assertTrue(speciesCollectionDTO.getPrevious() == null || UriValidator.isUri((String) speciesCollectionDTO.getPrevious()));
            }

        }

       @Nested
       @DisplayName("Testing Species Fields")
       class SpeciesTests{

           @Nested
           @DisplayName("Testing Name Field")
           class NameTests{
               @Test
               @DisplayName("Check if name of result body is not null and not empty")
               void checkIfNameOfResultBodyIsNotNullAndNotEmpty() {
                   Assertions.assertTrue(speciesDTO.getName() != null && speciesDTO.getName() != "");

               }

               @Test
               @DisplayName("Check if name of result body starts with a capital letter followed by lowercase letters")
               void checkIfNameOfResultBodyStartsWithACapitalLetterFollowedByLowerCaseLetters() {
                   Assertions.assertTrue(speciesDTO.getName().matches("[A-Z][a-z]+"));
               }

           }

           @Nested
           @DisplayName("Testing Average Height Field")
           class AverageHeightTests{
               @Test
               @DisplayName("Check if average height of result body is not null and not empty")
               void checkIfAverageHeightOfResultBodyIsNotNullAndNotEmpty() {
                   Assertions.assertTrue(speciesDTO.getAverageHeight() != null && speciesDTO.getAverageHeight() != "");

               }

               @Test
               @DisplayName("Check if average height is a integer string")
               void checkIfAverageHeightIsAIntegerString() {
                   if (NumberUtils.isParsable(speciesDTO.getAverageHeight())) {
                       System.out.println("String is numeric!");
                   } else {
                       System.out.println("String is not numeric.");
                   }
               }

           }

           @Nested
           @DisplayName("Testing Homeworld Field")
           class HomeworldTests {
               @Test
               @DisplayName("Check if Homeworld of result body is not null")
               void checkIfHomeworldOfResultBodyIsNotNull() {
                   assertThat(speciesDTO.getHomeworld(), is(notNullValue()));
               }

               @Test
               @DisplayName("Check that Homeworld is valid")
               void checkURLIsValid(){
                   Assertions.assertTrue(speciesDTO.getHomeworld() == null || speciesDTO.getHomeworld().matches("(https?:\\/{2}swapi\\.dev\\/api\\/[a-zA-Z\\d.\\-_~:\\/?#\\[\\]@!$&'()*,+;%=]*)?"));
               }

           }

           @Nested
           @DisplayName("Testing Films Field")
           class FilmTests{
               @Test
               @DisplayName("Check that films field doesn't return a null")
               void checkThatFilmsFieldDoesntReturnANull() {
                   Assumptions.assumeTrue(speciesDTO.getFilms() != null);
               }

               
           }


           @Nested
           @DisplayName("Testing Created Field")
           class CreatedTests{
               @ParameterizedTest
               @NullAndEmptySource
               @DisplayName("Check that Created Date is not null or empty")
               void checkCreatedDateNotNullOrEmpty(String testInput){
                   Assertions.assertNotEquals(testInput, speciesDTO.getCreated());
               }

               @Test
               @DisplayName("Check that the Created Date is in valid format")
               void checkCreatedDateValidFormat(){
                   boolean parsableDate = false;

                   try {
                       LocalDate.parse(speciesDTO.getCreated(), DateTimeFormatter.ISO_DATE_TIME);
                       parsableDate = true;
                   } catch (DateTimeParseException e) {
                       e.printStackTrace();
                   }
                   Assertions.assertTrue(parsableDate);
               }


           }


       }



    }

}



