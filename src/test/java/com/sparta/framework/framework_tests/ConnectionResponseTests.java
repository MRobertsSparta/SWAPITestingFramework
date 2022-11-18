package com.sparta.framework.framework_tests;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.framework.connection.ConnectionManager;
import com.sparta.framework.connection.ConnectionResponse;
import com.sparta.framework.dto.PeopleDTO;
import com.sparta.framework.exception.ConnectionManagementException;
import com.sparta.framework.injection.Injector;
import io.cucumber.java.sl.In;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class ConnectionResponseTests {

    private final Integer[] validStatusCodes = {200, 201, 200, 201, 202, 203, 204, 205, 206, 207, 208, 226,
            300, 301, 302, 303, 304, 305, 306, 307, 308, 400, 401, 402, 403, 404, 405, 406, 407, 408, 409,
            410, 411, 412, 413, 414, 415, 416, 417, 418, 420, 422, 423, 424, 425, 426, 428, 429, 431, 444,
            449, 450, 451, 499, 500, 501, 502, 503, 504, 505, 506, 507, 508, 509, 510, 511, 598, 599};
    private ConnectionResponse response;

    @BeforeEach
    void setup() {
        response = ConnectionManager.from().baseURL().getResponse();
    }

    @Test
    @DisplayName("Test that attempting to make a request with an invalid url throws an exception")
    void testMakeRequest() {
        assertThrows(ConnectionManagementException.class, () -> response.makeRequest(""));
    }

    @Test
    @DisplayName("Test that a header can be retrieved from a request")
    void testGetHeader() {
        assertThat(response.getHeader("Server"), notNullValue());
    }

    @Test
    @DisplayName("Test that a valid status code can be retrieved from a request")
    void testGetStatusCode() {
        assertThat(validStatusCodes, hasItemInArray(response.getStatusCode()));
    }

    @Test
    @DisplayName("Test that when a body is requested the correct JSON and class are passed to injector")
    void testGetBody() {
        MockedStatic<Injector> injector = Mockito.mockStatic(Injector.class);
        ArgumentCaptor<String> passedString = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Class> passedClass = ArgumentCaptor.forClass(Class.class);
        PeopleDTO mockDTO = Mockito.mock(PeopleDTO.class);

        response.getBodyAs(mockDTO.getClass());
        injector.verify(() -> Injector.getDTO(passedString.capture(), passedClass.capture()));

        assertTrue(isValidJSON(passedString.getValue()));
        assertThat(passedClass.getValue(), equalTo(mockDTO.getClass()));
    }

    public boolean isValidJSON(String json) {
        try {
            new ObjectMapper().readTree(json);
        } catch (JacksonException e) {
            return false;
        }
        return true;
    }
}
