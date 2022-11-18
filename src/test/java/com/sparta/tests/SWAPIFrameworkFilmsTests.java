package com.sparta.tests;

import com.sparta.framework.connection.ConnectionResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static com.sparta.framework.connection.ConnectionManager.from;

public class SWAPIFrameworkFilmsTests {

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


}}
