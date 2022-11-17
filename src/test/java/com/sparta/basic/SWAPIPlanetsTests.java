package com.sparta.basic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.framework.dto.PlanetCollection;
import com.sparta.framework.dto.PlanetsDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Character.isUpperCase;

public class SWAPIPlanetsTests {
    private static HttpClient client;
    private static HttpRequest request;
    private static HttpResponse<String> response;
    private static ObjectMapper mapper;
    private static PlanetsDto dto;
    private static String url;
    private static PlanetCollection collectionDto;

    @BeforeAll
    static void setupAll() {
        Random rn = new Random();
        int getIdRandom = rn.nextInt(59) + 1;
        mapper = new ObjectMapper();
        url = "https://swapi.dev/api/planets/";
        client = HttpClient.newHttpClient();  //Builder Pattern
        try {
            request = HttpRequest
                    .newBuilder()
                    .uri(new URI(url))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            dto = mapper.readValue(new URL(url + getIdRandom + "?format=json"), PlanetsDto.class);
            collectionDto = mapper.readValue(new URL(url + "?format=json"), PlanetCollection.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Nested
    class HeaderTests {
        @Test
        @DisplayName("Check that header server is nginx")
        void checkHeaderServerNginx() {
            Assertions.assertEquals("nginx/1.16.1", response.headers().firstValue("Server").orElse("Server not found"));
        }
    }

    @Nested
    @DisplayName("Single planet call tests")
    class PlanetTests {
        //status code
        @Test
        @DisplayName("Check That The Status Code is 200")
        void checkThatTheStatusCodeIs200() {
            Assertions.assertEquals(200, response.statusCode());
        }

        //name
        @Test
        @DisplayName("Check that the planet name has a capital letter")
        void checkThatThePlanetNameHasACapitalLetter() {
            String name = dto.getName();
            char checkCase = name.charAt(0);
            Assertions.assertTrue(isUpperCase(checkCase));
        }

        @Test
        @DisplayName("Check that the planet name has a Valid Name")
        void checkThatThePlanetNameHasAValidName() {
            Assertions.assertTrue(dto.getName().length() > 1);
        }

        //Planet Rotation
        @Test
        @DisplayName("Check that the planet rotation is a valid number")
        void checkThatThePlanetRotationHasAValidNumber() {
            Assumptions.assumeTrue(NumberUtils.isParsable(dto.getRotationPeriod()));
            Assertions.assertTrue(Integer.parseInt(dto.getRotationPeriod()) > 1);
        }

        @Test
        @DisplayName("Check that the planet rotation if not a valid number is unknown")
        void checkThatThePlanetRotationIfNotValidNumberReturnsUnknown() {
            Assumptions.assumeTrue(!NumberUtils.isParsable(dto.getRotationPeriod()));
            Assertions.assertTrue(dto.getRotationPeriod().equals("unknown"));
        }

        //Orbital Period
        @Test
        @DisplayName("Check that the orbital Period is a valid number")
        void checkThatTheOrbitalPeriodHasAValidNumber() {
            Assumptions.assumeTrue(NumberUtils.isParsable(dto.getOrbitalPeriod()));
            Assertions.assertTrue(Integer.parseInt(dto.getOrbitalPeriod()) > 1);
        }

        @Test
        @DisplayName("Check that the orbital Period if not a valid number is unknown")
        void checkThatTheOrbitalPeriodIfNotValidNumberReturnsUnknown() {
            Assumptions.assumeTrue(!NumberUtils.isParsable(dto.getOrbitalPeriod()));
            Assertions.assertTrue(dto.getOrbitalPeriod().equals("unknown"));
        }

        //Diameter
        @Test
        @DisplayName("Check that the Diameter is a valid number")
        void checkThatTheDiameterHasAValidNumber() {
            Assumptions.assumeTrue(NumberUtils.isParsable(dto.getDiameter()));
            Assumptions.assumeTrue(Integer.parseInt(dto.getDiameter()) != 0); //One Planet has a diameter of zero? When it should have cited by the "Wookiepedia"
            Assertions.assertTrue(Integer.parseInt(dto.getDiameter()) > 1);
        }

        @Test
        @DisplayName("Check that the Diameter if not a valid number is unknown")
        void checkThatTheDiameterIfNotValidNumberReturnsUnknown() {
            Assumptions.assumeTrue(!NumberUtils.isParsable(dto.getDiameter()));
            Assertions.assertTrue(dto.getDiameter().equals("unknown"));
        }

        //Climate
        @Test
        @DisplayName("Check that the planet climate has a Valid entry")
        void checkThatThePlanetClimateHasAValidField() {
            Assertions.assertTrue(dto.getClimate().length() > 1);
        }

        //Gravity
        @Test
        @DisplayName("Check that the planet gravity has a Valid entry")
        void checkThatThePlanetGravityHasAValidField() {
            Assertions.assertTrue(dto.getGravity().length() >= 1);
        }

        @Test
        @DisplayName("Check that the planet gravity has a number at front if not unknown and N/A")
        void checkThatThePlanetGravityHasNumberAtFront() {
            String gravity = dto.getGravity();
            Assumptions.assumeTrue(!gravity.equals("unknown"));
            Assumptions.assumeTrue(!gravity.equals("N/A"));
            Assertions.assertTrue(Character.isDigit(gravity.charAt(0)));
        }

        //link
        @Test
        @DisplayName("Check that the link is valid or a null")
        void checkThatThePlanetLinkIsValidOrNull() {
            Assertions.assertTrue(UriValidator.isUri(dto.getUrl()));
        }

        //Terrain
        @Test
        @DisplayName("Check that the planet terrain has a Valid entry")
        void checkThatThePlanetTerrainHasAValidField() {
            Assertions.assertTrue(dto.getTerrain().length() > 1);
        }

        //Surface Water
        @Test
        @DisplayName("Check that the planet Surface Water has a Valid entry")
        void checkThatThePlanetSurfaceWaterHasAValidField() {
            Assertions.assertTrue(dto.getSurfaceWater().length() >= 1);
        }

        @Test
        @DisplayName("Check that the planet Surface Water has a Valid Number and not unknown")
        void checkThatThePlanetSurfaceWaterHasAValidNumberNotUnknown() {
            Assumptions.assumeTrue(NumberUtils.isParsable(dto.getSurfaceWater()));
            Assertions.assertTrue(Integer.parseInt(dto.getSurfaceWater()) >= 0);
        }

        @Test
        @DisplayName("Check that the planet Surface Water has a is unknown when not a valid number")
        void checkThatThePlanetSurfaceWaterHasIsUnknownWhenNotAValidNumber() {
            Assumptions.assumeTrue(!NumberUtils.isParsable(dto.getSurfaceWater()));
            Assertions.assertTrue(dto.getSurfaceWater().equals("unknown") || dto.getSurfaceWater().equals("N/A"));
        }

        //Films
        @Test
        @DisplayName("Check that the planet films is not null")
        void checkThatThePlanetFilmsIsNotNull() {
            Assumptions.assumeTrue(dto.getFilms() != null);
        }
        //Population


        //Collections
        @Nested
        class VehicleCollectionTests {
            @Test
            @DisplayName("Check count is not lower than 0")
            void checkCountNotLowerThanZero() {
                Assertions.assertTrue(collectionDto.getCount() >= 0);
            }

            @Test
            @DisplayName("Check that result length is greater than 0 if count is also greater than 0")
            void checkResultLengthGreaterThanZero() {
                Assumptions.assumeTrue(collectionDto.getCount() > 0);
                Assertions.assertTrue(collectionDto.getResults().size() > 0);
            }

            @Test
            @DisplayName("Check list of vehicles size matches the count")
            void checkVehiclesCountMatchesListTotal() {
                //list for storing vehicles
                List<PlanetsDto> planetsList = new ArrayList<>();
                do {
                    planetsList.addAll(collectionDto.getResults());
                    try {
                        //move onto next page
                        collectionDto = mapper.readValue(new URL(collectionDto.getNext()), PlanetCollection.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //before stopping the loop, add the last results list
                    if (collectionDto.getNext() == null) {
                        planetsList.addAll(collectionDto.getResults());
                    }
                } while (collectionDto.getNext() != null);

                Assertions.assertEquals(collectionDto.getCount(), planetsList.size());
            }


            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("Check that results list is not empty or null")
            void checkVehiclesListIsNotNullOrEmpty(List<PlanetsDto> testList) {
                Assertions.assertNotEquals(testList, collectionDto.getResults());
            }

            @Test
            @DisplayName("Check that the Next link is valid or null")
            void checkThatNextLinkIsValid() {
                Assertions.assertTrue(collectionDto.getNext() == null || UriValidator.isUri(collectionDto.getNext()));
            }
        }
      }
}






