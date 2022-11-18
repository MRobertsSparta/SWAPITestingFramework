package com.sparta.framework.framework_tests;

import com.sparta.framework.connection.ConnectionManager;
import com.sparta.framework.connection.ConnectionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class ConnectionManagerTests {

    private static ConnectionManager connectionManager;
    private static final String testURL = "http://test.test";

    @BeforeEach
    void setup() {
        connectionManager = ConnectionManager.from();
    }

    @Test
    @DisplayName("Test that the from() method returns a new ConnectionManager Object")
    void testFrom() {
        assertThat(ConnectionManager.from(), instanceOf(ConnectionManager.class));
    }

    @Test
    @DisplayName("Test that the baseURL() method sets the internal URL to the base URL")
    void testBaseURL() {
        connectionManager.baseURL();
        assertThat(connectionManager.getURL(), equalTo(ConnectionManager.BASE_URL));
    }

    @Test
    @DisplayName("Test that the URL() method sets the internal URL to the passed string")
    void testURL() {
        connectionManager.URL(testURL);
        assertThat(connectionManager.getURL(), equalTo(testURL));
    }

    @Test
    @DisplayName("Test that the slash() method adds the given path to the URL")
    void testSlash() {
        connectionManager.URL(testURL).slash("test");
        assertThat(connectionManager.getURL(), equalTo(testURL + "/test"));
    }

    @Test
    @DisplayName("Test that the withParameter() method adds the given path variable to the map")
    void testWithParameter() {
        connectionManager.URL(testURL).withParameter("key", "value");
        assertThat(connectionManager.getParameters(), hasEntry("key", "value"));
    }

    @Test
    @DisplayName("Test that the getResponse() method creates a ConnectionResponse with the correct URL")
    void testGetResponse() {
        MockedConstruction<ConnectionResponse> mock = Mockito.mockConstruction(ConnectionResponse.class);
        ArgumentCaptor<String> passedString = ArgumentCaptor.forClass(String.class);

        connectionManager.URL(testURL).withParameter("key", "value").getResponse();
        Mockito.verify(mock.constructed().get(0)).makeRequest(passedString.capture());

        assertThat(passedString.getValue(), equalTo("http://test.test?format=json&key=value"));
    }
}
