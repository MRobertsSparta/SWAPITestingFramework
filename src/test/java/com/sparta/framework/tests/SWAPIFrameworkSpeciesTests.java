package com.sparta.framework.tests;

import com.sparta.framework.connection.ConnectionResponse;
import com.sparta.framework.dto.SpeciesCollectionDTO;
import com.sparta.framework.dto.SpeciesDTO;
import io.restassured.internal.UriValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static com.sparta.framework.connection.ConnectionManager.from;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SWAPIFrameworkSpeciesTests {
    private static ConnectionResponse speciesResponse;

    private static ConnectionResponse speciesCollectionResponse;
    private static SpeciesDTO speciesDTO;
    private static SpeciesCollectionDTO speciesCollectionDTO;

    @BeforeAll
    static void setUpAll() {
        speciesCollectionResponse = from().baseURL().slash("species").getResponse();
        speciesResponse = from().baseURL().slash("species").slash("31").getResponse();

        speciesCollectionDTO = speciesCollectionResponse.getBodyAs(SpeciesCollectionDTO.class);
        speciesDTO = speciesResponse.getBodyAs(SpeciesDTO.class);
    }

    @Nested
    class SpeciesCollectionTests{
        @Nested
        @DisplayName("Testing status code")
        class StatusCodeTests{

            @Test
            @DisplayName("Check that the Status code is 200")
            void checkThatTheStatusCodeIs200() {
                Assertions.assertEquals(200, speciesCollectionResponse.getStatusCode());
            }
            @Test
            @DisplayName("Check that the Status code is not equal to 400")
            void checkThatTheStatusCodeIs400() {
                Assertions.assertNotEquals(400, speciesCollectionResponse.getStatusCode());
            }
        }


        @Nested
        @DisplayName("Testing Header")
        class ResponseHeaderTests{

            @Test
            @DisplayName("Testing the Header Server")
            void testingTheHeaders() {
                Assertions.assertEquals("nginx/1.16.1", speciesCollectionResponse.getHeader("Server"));
            }

        }

        @Nested
        @DisplayName("Testing the response body")
        class ResponseBodyTests{

            @Test
            @DisplayName("Check count is not lower than 0")
            void checkCountNotLowerThanZero() {
                Assertions.assertTrue(speciesCollectionDTO.getCount() >= 0);
            }

            @Test
            @DisplayName("Check that result length is greater than 0 if count is also greater than 0")
            void checkResultLengthGreaterThanZero() {
                Assumptions.assumeTrue(speciesCollectionDTO.getCount() > 0);
                Assertions.assertTrue(speciesCollectionDTO.getResults().size() > 0);
            }

            @Test
            @DisplayName("Check list of species size matches the count")
            void checkSpeciesCountMatchesListTotal() {
                Assertions.assertEquals(speciesCollectionDTO.getCount(), speciesCollectionDTO.getTotalSpecies());
            }

            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("Check that results list is not empty or null")
            void checkSpeciesListIsNotNullOrEmpty(List<SpeciesDTO> testList){
                Assertions.assertNotEquals(testList, speciesCollectionDTO.getResults());
            }

            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("Check that results list is not empty or null")
            void checkVehiclesListIsNotNullOrEmpty(List<SpeciesDTO> testList) {
                Assertions.assertNotEquals(testList, speciesCollectionDTO.getResults());
            }

            @Test
            @DisplayName("Check that the Next link is valid or null")
            void checkThatNextLinkIsValid() {
                Assertions.assertTrue(speciesCollectionDTO.getNext() == null || UriValidator.isUri(speciesCollectionDTO.getNext()));
            }


        }

    }

    @Nested
    class SpeciesTests{
        @Nested
        @DisplayName("Testing status code")
        class StatusCodeTests{

            @Test
            @DisplayName("Check that the Status code is 200")
            void checkThatTheStatusCodeIs200() {
                Assertions.assertEquals(200, speciesResponse.getStatusCode());
            }
            @Test
            @DisplayName("Check that the Status code is not equal to 400")
            void checkThatTheStatusCodeIs400() {
                Assertions.assertNotEquals(400, speciesResponse.getStatusCode());
            }
        }


        @Nested
        @DisplayName("Testing Header")
        class ResponseHeaderTests{

            @Test
            @DisplayName("Testing the Header Server")
            void testingTheHeaders() {
                Assertions.assertEquals("nginx/1.16.1", speciesResponse.getHeader("Server"));
            }

        }

        @Nested
        @DisplayName("Testing the response body")
        class ResponseBodyTests{

            @Nested
            @DisplayName("Testing Name Field")
            class NameTests {
                @Test
                @DisplayName("Check if name of result body is not null and not empty")
                void checkIfNameOfResultBodyIsNotNullAndNotEmpty() {
                    Assertions.assertTrue(speciesDTO.getName() != null && speciesDTO.getName() != "");

                }

                @Test
                @DisplayName("Check if name of result body starts with a capital letter followed by lowercase letters")
                void checkIfNameOfResultBodyStartsWithACapitalLetterFollowedByLowerCaseLetters() {
                    Assertions.assertTrue(speciesDTO.validStartsWithUpperCasePattern());
                }

            }

            @Nested
            @DisplayName("Testing Average Height Field")
            class AverageHeightTests {
                @Test
                @DisplayName("Check if average height of result body is not null and not empty")
                void checkIfAverageHeightOfResultBodyIsNotNullAndNotEmpty() {
                    Assertions.assertTrue(speciesDTO.getAverageHeight() != null && speciesDTO.getAverageHeight() != "");

                }

                @Test
                @DisplayName("Check if average height is a integer string")
                void checkIfAverageHeightIsAIntegerString() {
                    Assertions.assertTrue(speciesDTO.averageHeightIsAIntegerString(speciesDTO.getAverageHeight()));
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
                void checkURLIsValid() {
                    Assertions.assertTrue(speciesDTO.getHomeworld() == null || speciesDTO.validSWAPIUrl());
                }

            }

            @Nested
            @DisplayName("Testing Films Field")
            class FilmTests {
                @Test
                @DisplayName("Check that films field doesn't return a null")
                void checkThatFilmsFieldDoesntReturnANull() {
                    Assumptions.assumeTrue(speciesDTO.getFilms() != null);
                }

            }


            @Nested
            @DisplayName("Testing Created Field")
            class CreatedTests {
                @ParameterizedTest
                @NullAndEmptySource
                @DisplayName("Check that Created Date is not null or empty")
                void checkCreatedDateNotNullOrEmpty(String testInput) {
                    Assertions.assertNotEquals(testInput, speciesDTO.getCreated());
                }

                @Test
                @DisplayName("Check that the Created Date is in valid format")
                void checkCreatedDateValidFormat() {
                    Assertions.assertTrue(speciesDTO.isDateParseable(speciesDTO.getCreated()));
                }

            }

        }

    }

}




