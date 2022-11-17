package com.sparta.basic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.framework.dto.StarshipCollectionDTO;
import com.sparta.framework.dto.StarshipDTO;
import io.restassured.internal.UriValidator;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class SWAPIStarshipCollectionTests {

    private static HttpClient client;
    private HttpRequest request;
    private HttpResponse response;

    private static ObjectMapper mapper;
    private static StarshipCollectionDTO dto;

    private final static String URL = "https://swapi.dev/api/starships?format=json";

    @BeforeAll
    static void setupAll() {
        client = HttpClient.newHttpClient();
        mapper = new ObjectMapper();

        try {
            dto = mapper.readValue(new URL(URL), StarshipCollectionDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setup(TestInfo testInfo) {
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

        @Nested
        @DisplayName("Response Header Tests")
        class ResponseHeaderTests {
            @Test
            @DisplayName("Check that header server is nginx")
            void checkHeaderServerNginx(){
                Assertions.assertEquals("nginx/1.16.1", response.headers().firstValue("Server").orElse("Server not found"));
            }
        }
    }

    @Nested
    class ResponseBodyTests{
        @Test
        @DisplayName("Check count is not lower than 0")
        void checkCountNotLowerThanZero(){
            Assertions.assertTrue(dto.getCount() >= 0);
        }

        @Test
        @DisplayName("Check that result length is greater than 0 if count is also greater than 0")
        void checkResultLengthGreaterThanZero(){
            Assumptions.assumeTrue(dto.getCount() > 0);
            Assertions.assertTrue(dto.getResults().size() > 0);
        }

        @Test
        @DisplayName("Check list of starships size matches the count")
        void checkStarshipsCountMatchesListTotal(){
            //list for storing starships
            List<StarshipDTO> starshipList = new ArrayList<>();
            do {
                starshipList.addAll(dto.getResults());
                try {
                    //move onto next page
                    dto = mapper.readValue(new URL(dto.getNext()), StarshipCollectionDTO.class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //before stopping the loop, add the last results list
                if(dto.getNext() == null){
                    starshipList.addAll(dto.getResults());
                }
            }while(dto.getNext() != null);

            //now that all starships have been added to a list
            //compare size with the .getCount from the JSON response
            Assertions.assertEquals(dto.getCount(), starshipList.size());
        }



        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Check that results list is not empty or null")
        void checkStarshipListIsNotNullOrEmpty(List<StarshipDTO> testList){
            Assertions.assertNotEquals(testList, dto.getResults());
        }

        @Test
        @DisplayName("Check that the Next link is valid or null")
        void checkThatNextLinkIsValid(){
            Assertions.assertTrue(dto.getNext() == null || UriValidator.isUri(dto.getNext()));
        }

        @Test
        @DisplayName("Check that the Previous link is valid or null")
        void checkThatPreviousLinkIsValid(){
            Assertions.assertTrue(dto.getPrevious() == null || UriValidator.isUri(dto.getPrevious()));
        }
    }
}
