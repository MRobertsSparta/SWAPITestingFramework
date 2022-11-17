package com.sparta.basic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.framework.dto.StarshipCollectionDTO;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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

        }
    }
}
