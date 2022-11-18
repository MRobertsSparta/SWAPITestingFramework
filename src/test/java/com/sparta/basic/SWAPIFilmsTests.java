package com.sparta.basic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.framework.dto.FilmsCollectionDTO;
import com.sparta.framework.dto.FilmsDTO;
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

public class SWAPIFilmsTests {

    private static HttpClient client;
    private static HttpRequest request;
    private static HttpResponse<String> response;
    private static ObjectMapper mapper;
    private static FilmsDTO filmsDTO;
    private static FilmsCollectionDTO filmsCollectionDTO;


    @BeforeAll
    static void SetupAll() {
        mapper = new ObjectMapper();
        client = HttpClient.newHttpClient(); //Builder pattern
        try {
            request = HttpRequest
                    .newBuilder()
                    .uri(new URI("https://swapi.dev/api/films/"))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            filmsCollectionDTO = mapper.readValue(new URL("https://swapi.dev/api/films?format=json"), FilmsCollectionDTO.class);
            filmsDTO = filmsCollectionDTO.getResults().get(0);
            //filmsDTO = mapper.readValue(new URL("https://swapi.dev/api/films/?format=json"), FilmsDTO.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Nested
    @DisplayName("Testing Status Code")
    class testStatusCode {
        @Test
        @DisplayName("Check that the status code is 200")
        void checkThatTheStatusCodeIs200() {
            Assertions.assertEquals(200, response.statusCode());
        }
    }

    @Nested
    @DisplayName("Testing Header")
    class testHeader {
        @Test
        @DisplayName("Testing Headers is nginx")
        void testingTheHeadersServer() {
            System.out.println(response.headers().firstValue("Server"));
            Assertions.assertEquals("nginx/1.16.1", response.headers().firstValue("Server").orElse("Server not found"));
        }
    }

    @Nested
    @DisplayName("Testing Body")
    class testBody {

        @Nested
        @DisplayName("Testing Collection")
        class FilmsCollectionTests {
            @Test
            @DisplayName("Testing the response body")
            void testingTheResponseBody() {
                System.out.println(response.body());
            }

            @Test
            @DisplayName("Check list of films size matches the count")
            void checkFilmsCountMatchesListTotal(){
                //list for storing films
//                List<FilmsDTO> filmsList = new ArrayList<>();
//                do
//                {
//                    filmsList.addAll(filmsCollectionDTO.getResults());
//                    try {
//                        //move onto next page
//                        filmsCollectionDTO = mapper.readValue(new URL( filmsCollectionDTO.getNext()), FilmsCollectionDTO.class);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    //before stopping the loop, add the last results list
//                    if(filmsCollectionDTO.getNext() == null){
//                        filmsList.addAll(filmsCollectionDTO.getResults());
//                    }
//                }while(filmsCollectionDTO.getNext() != null);

                //now that all films have been added to a list
                //compare size with the .getCount from the JSON response

                ObjectMapper mapper = new ObjectMapper();
                FilmsCollectionDTO dto = filmsCollectionDTO;
                List<FilmsDTO> filmsList = new ArrayList<>(dto.getResults());
                while (dto.getNext() != null) {
                    try {
                        dto = new ObjectMapper().readValue(
                                new URL(dto.getNext()), FilmsCollectionDTO.class);
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                    filmsList.addAll(dto.getResults());
                }

                Assertions.assertEquals(filmsCollectionDTO.getCount(), filmsList.size());
            }

            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("Check that results list is not empty or null")
            void checkFilmsListIsNotNullOrEmpty(List<FilmsDTO> testList) {
                Assertions.assertNotEquals(testList, filmsCollectionDTO.getResults());
            }

            @Test
            @DisplayName("Check that the Next link is valid or null")
            void checkThatNextLinkIsValid() {
                Assertions.assertTrue(filmsCollectionDTO.getNext() == null || UriValidator.isUri((String)filmsCollectionDTO.getNext()));
            }

            @Test
            @DisplayName("Check that the Previous link is valid or null")
            void checkThatPreviousLinkIsValid() {
                Assertions.assertTrue(filmsCollectionDTO.getPrevious() == null || UriValidator.isUri((String) filmsCollectionDTO.getPrevious()));
            }
        }

        @Nested
        @DisplayName("Testing Films Fields")
        class testFilmsFields {

            @Test
            @DisplayName("Check the title is not null and not empty")
            void checkTheTitleIsNotNullAndNotEmpty() {
                Assertions.assertTrue(filmsDTO.getTitle() != null && filmsDTO.getTitle() != "");
            }

            @Test
            @DisplayName("Check the director is not null and not empty")
            void checkTheDirectorIsNotNullAndNotEmpty() {
                Assertions.assertTrue(filmsDTO.getDirector() != null && filmsDTO.getDirector() != "");
            }

            @Test
            @DisplayName("Check the episodeid is not null and not negative")
            void checkTheEpisodeIdIsNotNullAndNotNegative() {
                Assertions.assertTrue(filmsDTO.getEpisodeId() > 0);
            }





        }

    }


}
