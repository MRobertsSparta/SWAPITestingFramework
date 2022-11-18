package com.sparta.tests;

import com.sparta.framework.connection.ConnectionResponse;
import com.sparta.framework.dto.PeopleCollectionDTO;
import com.sparta.framework.dto.PeopleDTO;
import org.junit.jupiter.api.*;

import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static com.sparta.framework.connection.ConnectionManager.*;
import static org.junit.jupiter.api.Assertions.*;

public class SWAPIFrameworkPeopleTests {

    @Nested
    @DisplayName("Test request head")
    class testHead {

        private static ConnectionResponse response;

        @BeforeAll
        static void setupAll() {
            response = from().baseURL().slash("people").getResponse();
        }

        @Test
        @DisplayName("Test that the status code is 200")
        void testStatusCode() {
            assertThat(response.getStatusCode(), equalTo(200));
        }

        @Test
        @DisplayName("Test that the server is nginx/1.16.1")
        void testServer() {
            assertThat(response.getHeader("Server"), equalTo("nginx/1.16.1"));
        }

        @Test
        @DisplayName("Test that the content type is 'application/json'")
        void testContentType() {
            assertThat(response.getHeader("Content-Type"), equalTo("application/json"));
        }
    }

    @Nested
    @DisplayName("Test request body")
    class testBody {
        private static PeopleDTO peopleDTO;
        private static PeopleCollectionDTO peopleCollectionDTO;

        @BeforeAll
        static void setupAll() throws IOException {
            peopleDTO = from().baseURL().slash("people/1").getResponse().getBodyAs(PeopleDTO.class);
            peopleCollectionDTO = from().baseURL().slash("people").getResponse().getBodyAs(PeopleCollectionDTO.class);
        }

        @Nested
        @DisplayName("Test singular DTO")
        class SingularTests {

            @Test
            @DisplayName("Check name isn't empty")
            void checkName() {
                assertThat(peopleDTO.getName(), not(emptyString()));
            }

            @Test
            @DisplayName("Check birth year is a number followed by BBY or ABY")
            void checkBirthYear() {
                assertTrue(peopleDTO.hasValidBirthYear());
            }

            @Test
            @DisplayName("Check eye colour isn't empty")
            void checkEyeColour() {
                assertThat(peopleDTO.getEyeColor(), not(emptyString()));
            }

            @Test
            @DisplayName("Check gender is a valid gender")
            void checkGender() {
                assertTrue(peopleDTO.hasValidGender());
            }

            @Test
            @DisplayName("Check hair colour isn't empty")
            void checkHairColour() {
                assertThat(peopleDTO.getHairColor(), not(emptyString()));
            }

            @Test
            @DisplayName("Check skin colour isn't empty")
            void checkSkinColour() {
                assertThat(peopleDTO.getSkinColor(), not(emptyString()));
            }

            @Test
            @DisplayName("Check height is a positive integer")
            void checkHeight() {
                assertTrue(peopleDTO.hasValidHeight());
            }

            @Test
            @DisplayName("Check mass is a positive integer")
            void checkMass() {
                assertTrue(peopleDTO.hasValidMass());
            }

            @Test
            @DisplayName("Check homeworld is a valid SWAPI URL")
            void checkHomeworld() {
                assertTrue(peopleDTO.fieldIsValidSWAPIURL("homeworld"));
            }

            @Test
            @DisplayName("Check url is a valid SWAPI URL")
            void checkURL() {
                assertTrue(peopleDTO.fieldIsValidSWAPIURL("url"));
            }

            @Test
            @DisplayName("Check films is an array of valid SWAPI URL")
            void checkFilms() {
                assertTrue(peopleDTO.fieldIsValidListOfSWAPIURLs("films"));
            }

            @Test
            @DisplayName("Check species is an array of valid SWAPI URL")
            void checkSpecies() {
                assertTrue(peopleDTO.fieldIsValidListOfSWAPIURLs("species"));
            }

            @Test
            @DisplayName("Check starships is an array of valid SWAPI URL")
            void checkStarships() {
                assertTrue(peopleDTO.fieldIsValidListOfSWAPIURLs("starships"));
            }

            @Test
            @DisplayName("Check vehicles is an array of valid SWAPI URL")
            void checkVehicles() {
                assertTrue(peopleDTO.fieldIsValidListOfSWAPIURLs("vehicles"));
            }

            @Test
            @DisplayName("Check created is an ISO 8601 date")
            void checkCreated() {
                assertTrue(peopleDTO.hasValidCreationDate());
            }

            @Test
            @DisplayName("Check edited is an ISO 8601 date")
            void checkEdited() {
                assertTrue(peopleDTO.hasValidEditedDate());
            }
        }

        @Nested
        @DisplayName("Test collection DTO")
        class CollectionTests {
            @Test
            @DisplayName("Check count is not lower than 0")
            void checkCountNotLowerThanZero() {
                assertThat(peopleCollectionDTO.getCount(), greaterThanOrEqualTo(0));
            }

            @Test
            @DisplayName("Check that result length is greater than 0 if count is also greater than 0")
            void checkResultLengthGreaterThanZero() {
                Assumptions.assumeTrue(peopleCollectionDTO.getCount() > 0);
                assertThat(peopleCollectionDTO.getResults().size(), greaterThan(0));
            }

            @Test
            @DisplayName("Check size of the list of people matches the count")
            void checkVehiclesCountMatchesListTotal() {
                assertThat(peopleCollectionDTO.getCount(), equalTo(peopleCollectionDTO.getTotalPeople()));
            }

            @Test
            @DisplayName("Check that the Next link is valid if it isn't null")
            void checkThatNextLinkIsValid() {
                assertTrue(peopleCollectionDTO.hasValidNextURL());
            }

            @Test
            @DisplayName("Check that the Previous link is valid if it isn't null")
            void checkThatPreviousLinkIsValid() {
                assertTrue(peopleCollectionDTO.hasValidPreviousURL());
            }
        }
    }
}

