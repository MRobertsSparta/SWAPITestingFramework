package com.sparta.basic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.framework.dto.PeopleCollectionDTO;
import com.sparta.framework.dto.PeopleDTO;
import org.junit.jupiter.api.*;

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
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SWAPIPeopleTest {

    private static final String endpoint = "https://swapi.dev/api/people";
    private static final String format = "?format=json";

    @Nested
    @DisplayName("Test request head")
    class testHead {
        private static int statusCode;
        private static Map<String, List<String>> headers;

        @BeforeAll
        static void setupAll() throws URISyntaxException, IOException, InterruptedException {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI(endpoint + format))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            statusCode = response.statusCode();
            headers = response.headers().map();
        }

        @Test
        @DisplayName("Test that the status code is 200")
        void testStatusCode() {
            assertThat(statusCode, equalTo(200));
        }

        @Test
        @DisplayName("Test that the server is nginx/1.16.1")
        void testServer() {
            assertThat(headers.get("Server").get(0), equalTo("nginx/1.16.1"));
        }

        @Test
        @DisplayName("Test that the content type is 'application/json'")
        void testContentType() {
            assertThat(headers.get("Content-Type").get(0), equalTo("application/json"));
        }
    }

    @Nested
    @DisplayName("Test request body")
    class testBody {

        private static final String URLPattern =
                "(https?:\\/{2}swapi\\.dev\\/api\\/[a-zA-Z\\d.\\-_~:\\/?#\\[\\]@!$&'()*,+;%=]*)?";
        private static PeopleDTO peopleDTO;
        private static PeopleCollectionDTO peopleCollectionDTO;

        @BeforeAll
        static void setupAll() throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            peopleDTO = mapper.readValue(new URL(endpoint + "/1" + format), PeopleDTO.class);
            peopleCollectionDTO = mapper.readValue(
                    new URL(endpoint + format), PeopleCollectionDTO.class);
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
                assertThat(peopleDTO.getBirthYear(), matchesPattern("\\d+ ?[AB]BY"));
            }

            @Test
            @DisplayName("Check eye colour isn't empty")
            void checkEyeColour() {
                assertThat(peopleDTO.getEyeColor(), not(emptyString()));
            }

            @Test
            @DisplayName("Check gender is a valid gender")
            void checkGender() {
                assertThat(peopleDTO.getGender(), matchesPattern("((fe)?male|unknown|n\\/a)"));
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
                assertThat(peopleDTO.getHeight(), matchesPattern("\\d+"));
            }

            @Test
            @DisplayName("Check mass is a positive integer")
            void checkMass() {
                assertThat(peopleDTO.getMass(), matchesPattern("\\d+"));
            }

            @Test
            @DisplayName("Check homeworld is a valid SWAPI URL")
            void checkHomeworld() {
                assertThat(peopleDTO.getHomeworld(), matchesPattern(URLPattern));
            }

            @Test
            @DisplayName("Check url is a valid SWAPI URL")
            void checkURL() {
                assertThat(peopleDTO.getUrl(), matchesPattern(URLPattern));
            }

            @Test
            @DisplayName("Check films is an array of valid SWAPI URL")
            void checkFilms() {
                for (String films: peopleDTO.getFilms()) {
                    assertThat(films, matchesPattern(URLPattern));
                }
            }

            @Test
            @DisplayName("Check species is an array of valid SWAPI URL")
            void checkSpecies() {
                for (String species: peopleDTO.getSpecies()) {
                    assertThat(species, matchesPattern(URLPattern));
                }
            }

            @Test
            @DisplayName("Check starships is an array of valid SWAPI URL")
            void checkStarships() {
                for (String starship: peopleDTO.getStarships()) {
                    assertThat(starship, matchesPattern(URLPattern));
                }
            }

            @Test
            @DisplayName("Check vehicles is an array of valid SWAPI URL")
            void checkVehicles() {
                for (String vehicle: peopleDTO.getVehicles()) {
                    assertThat(vehicle, matchesPattern(URLPattern));
                }
            }

            @Test
            @DisplayName("Check created is a ISO 8601 date")
            void checkCreated() {
                boolean parsableDate;
                try {
                    LocalDate.parse(peopleDTO.getCreated(), DateTimeFormatter.ISO_DATE_TIME);
                    parsableDate = true;
                } catch (DateTimeParseException e) {
                    parsableDate = false;
                }
                assertThat(parsableDate, is(true));
            }

            @Test
            @DisplayName("Check edited is a ISO 8601 date")
            void checkEdited() {
                boolean parsableDate;
                try {
                    LocalDate.parse(peopleDTO.getEdited(), DateTimeFormatter.ISO_DATE_TIME);
                    parsableDate = true;
                } catch (DateTimeParseException e) {
                    parsableDate = false;
                }
                assertThat(parsableDate, is(true));
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
                ObjectMapper mapper = new ObjectMapper();
                PeopleCollectionDTO dto = peopleCollectionDTO;
                List<PeopleDTO> peopleList = new ArrayList<>(dto.getResults());
                while (dto.getNext() != null) {
                    try {
                        dto = new ObjectMapper().readValue(
                                new URL(dto.getNext()), PeopleCollectionDTO.class);
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                    peopleList.addAll(dto.getResults());
                }
                assertThat(peopleCollectionDTO.getCount(), equalTo(peopleList.size()));
            }

            @Test
            @DisplayName("Check that the Next link is valid if it isn't null")
            void checkThatNextLinkIsValid() {
                Assumptions.assumeTrue(peopleCollectionDTO.getNext() != null);
                assertThat(peopleCollectionDTO.getNext(), matchesPattern(URLPattern));
            }
        }
    }
}
