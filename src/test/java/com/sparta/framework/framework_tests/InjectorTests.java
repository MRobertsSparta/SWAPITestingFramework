package com.sparta.framework.framework_tests;

import com.sparta.framework.dto.VehicleDTO;
import com.sparta.framework.exception.ConnectionManagementException;
import com.sparta.framework.injection.Injector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class InjectorTests {

    private final VehicleDTO mockDTO = Mockito.mock(VehicleDTO.class);
    private final String validJSON = "{\"name\":\"Sand Crawler\",\"model\":\"Digger Crawler\"," +
            "\"manufacturer\":\"Corellia Mining Corporation\",\"cost_in_credits\":\"150000\"," +
            "\"length\":\"36.8 \",\"max_atmosphering_speed\":\"30\",\"crew\":\"46\",\"passengers\":\"30\"," +
            "\"cargo_capacity\":\"50000\",\"consumables\":\"2 months\",\"vehicle_class\":\"wheeled\"," +
            "\"pilots\":[],\"films\":[\"https://swapi.dev/api/films/1/\",\"https://swapi.dev/api/films/5/\"]," +
            "\"created\":\"2014-12-10T15:36:25.724000Z\",\"edited\":\"2014-12-20T21:30:21.661000Z\"," +
            "\"url\":\"https://swapi.dev/api/vehicles/4/\"}";

    @Test
    @DisplayName("Test that the getDTO method returns the given DTO")
    void testGetDTO() {
        assertThat(Injector.getDTO(validJSON, mockDTO.getClass()), instanceOf(mockDTO.getClass()));
    }

    @Test
    @DisplayName("Test that an exception is thrown when invalid JSON is passed")
    void testGetDTOException() {
        assertThrows(ConnectionManagementException.class, () -> Injector.getDTO("", mockDTO.getClass()));
    }
}
