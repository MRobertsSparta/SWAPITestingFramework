package com.sparta.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.framework.connection.ConnectionManager;
import com.sparta.framework.connection.ConnectionResponse;
import com.sparta.framework.dto.VehicleDTO;
import com.sparta.framework.dto.VehicleCollectionDTO;
import io.restassured.internal.UriValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SWAPIFrameworkVehicleTests {
    private static VehicleCollectionDTO vehicleCollectionDTO;
    private static VehicleDTO vehicleDTO;
    private static ConnectionResponse response;


    @BeforeAll
    static void setupAll() {
        response = ConnectionManager.from().baseURL().slash("vehicles").getResponse();
        vehicleCollectionDTO = response.getBodyAs(VehicleCollectionDTO.class);
        vehicleDTO = vehicleCollectionDTO.getResults().get(0);
    }

    @Test
    @DisplayName("Check that status code is 200")
    void checkThatTheStatusCodeIs200(){
        Assertions.assertEquals(200, response.getStatusCode());
    }

    @Nested
    class HeaderTests{
        @Test
        @DisplayName("Check that header server is nginx")
        void checkHeaderServerNginx(){
            Assertions.assertEquals("nginx/1.16.1", response.getHeader("Server"));
        }
    }

    @Nested
    class VehicleCollectionTests{
        @Test
        @DisplayName("Check count is not lower than 0")
        void checkCountNotLowerThanZero(){
            Assertions.assertTrue(vehicleCollectionDTO.getCount() >= 0);
        }

        @Test
        @DisplayName("Check that result length is greater than 0 if count is also greater than 0")
        void checkResultLengthGreaterThanZero(){
            Assumptions.assumeTrue(vehicleCollectionDTO.getCount() > 0);
            Assertions.assertTrue(vehicleCollectionDTO.getResults().size() > 0);
        }

        @Test
        @DisplayName("Check list of vehicles size matches the count")
        void checkVehiclesCountMatchesListTotal(){
            ObjectMapper mapper = new ObjectMapper();
            //list for storing vehicles
            List<VehicleDTO> vehiclesList = new ArrayList<>();
            do
            {
                vehiclesList.addAll(vehicleCollectionDTO.getResults());
                try {
                    //move onto next page
                    vehicleCollectionDTO = mapper.readValue(new URL(vehicleCollectionDTO.getNext()), VehicleCollectionDTO.class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //before stopping the loop, add the last results list
                if(vehicleCollectionDTO.getNext() == null){
                    vehiclesList.addAll(vehicleCollectionDTO.getResults());
                }
            }while(vehicleCollectionDTO.getNext() != null);

            /*for (VehicleDTO vehicle: vehiclesList) {
                System.out.println(vehicle.getConsumables());
            }*/

            //now that all vehicles have been added to a list
            //compare size with the .getCount from the JSON response
            Assertions.assertEquals(vehicleCollectionDTO.getCount(), vehiclesList.size());
        }



        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Check that results list is not empty or null")
        void checkVehiclesListIsNotNullOrEmpty(List<VehicleDTO> testList){
            Assertions.assertNotEquals(testList, vehicleCollectionDTO.getResults());
        }

        @Test
        @DisplayName("Check that the Next link is valid or null")
        void checkThatNextLinkIsValid(){
            Assertions.assertTrue(vehicleCollectionDTO.getNext() == null || UriValidator.isUri(vehicleCollectionDTO.getNext()));
        }

        @Test
        @DisplayName("Check that the Previous link is valid or null")
        void checkThatPreviousLinkIsValid(){
            Assertions.assertTrue(vehicleCollectionDTO.getPrevious() == null || UriValidator.isUri(vehicleCollectionDTO.getPrevious()));
        }
    }

    @Nested
    class VehicleTests{
        @Test
        @DisplayName("Check that a specific vehicle resource is reachable via ID")
        void checkVehicleFourExists(){
            vehicleDTO = null;
            response = ConnectionManager.from().baseURL().slash("vehicles/4").getResponse();
            vehicleDTO = response.getBodyAs(VehicleDTO.class);
            Assertions.assertNotNull(vehicleDTO);
        }

        @Nested
        class NameTests{
            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("Check that name is not null or empty")
            void checkNameIsNotNullOrEmpty(String testName){
                Assertions.assertNotEquals(testName, vehicleDTO.getName());
            }

            @Test
            @DisplayName("Check that name starts with a capital letter")
            void checkNameStartsWithCapitalLetter(){
                Assertions.assertTrue(vehicleDTO.isFirstCharUpperCase(vehicleDTO.getName()));
            }
        }

        @Nested
        class MaxAtmospheringSpeedTests{
            @Test
            @DisplayName("Check that max atmosphering speed is greater than 0 or null")
            void checkMaxAtmospheringSpeedIsGreaterThanZero(){
                Assertions.assertTrue(vehicleDTO.getMaxAtmospheringSpeed() == null || vehicleDTO.getMaxAtmospheringSpeed() > 0);
            }
        }

        @Nested
        class ModelTests{
            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("Check that Model is not null or empty")
            void checkModelIsNotNullOrEmpty(String testModel){
                Assertions.assertNotEquals(testModel, vehicleDTO.getModel());
            }
        }

        @Nested
        class VehicleClassTests{
            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("Check that Vehicle Class is not null or empty")
            void checkVehicleClassNotNullOrEmpty(String testVehicleClass){
                Assertions.assertNotEquals(testVehicleClass, vehicleDTO.getVehicleClass());
            }

            @Test
            @DisplayName("Check that Vehicle Class matches one of the allowed classes")
            void checkVehicleClassMatches(){
                Assertions.assertTrue(vehicleDTO.vehicleClassList.contains(vehicleDTO.getVehicleClass()));
            }
        }

        @Nested
        class ManufacturerTests{
            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("Check that the Manufacturer is not null or empty")
            void checkManufacturerIsNotNullOrEmpty(String testManufacturer){
                Assertions.assertNotEquals(testManufacturer, vehicleDTO.getManufacturer());
            }

            @Test
            @DisplayName("Check that the Manufacturer starts with a capital letter or 'u'")
            void checkManufacturerStartsWithCapitalLetter(){
                char firstChar = vehicleDTO.getManufacturer().charAt(0);
                Assertions.assertTrue(vehicleDTO.isFirstCharUpperCase(vehicleDTO.getManufacturer())
                        || vehicleDTO.getManufacturer().charAt(0)=='u');
            }
        }

        @Nested
        class LengthTests{
            @Test
            @DisplayName("Check that Length is greater than 0 or null")
            void checkLengthGreaterThanZero(){
                Assertions.assertTrue(vehicleDTO.getLength() == null ||vehicleDTO.getLength()>0);
            }
        }

        @Nested
        class CostInCreditsTests{
            @Test
            @DisplayName("Check that Cost In Credits is greater than 0 or null")
            void checkCostInCreditsGreaterThanZero(){
                Assertions.assertTrue(vehicleDTO.getCostInCredits() == null || vehicleDTO.getCostInCredits() > 0);
            }
        }

        @Nested
        class CrewTests{
            @ParameterizedTest
            @NullSource
            @DisplayName("Check that crew is not null")
            void checkCrewNotNull(Integer testInteger){
                Assertions.assertNotEquals(testInteger, vehicleDTO.getCrew());
            }

            @Test
            @DisplayName("Check that crew number is not negative")
            void checkCrewNotNegative(){
                Assertions.assertTrue(vehicleDTO.getCrew()>=0);
            }
        }

        @Nested
        class PassengersTests{
            @Test
            @DisplayName("Check that passengers is null or not negative")
            void checkPassengerNotNegative(){
                Assertions.assertTrue(vehicleDTO.getPassengers() == null || vehicleDTO.getPassengers() >= 0);
            }
        }

        @Nested
        class CargoCapacityTests{
            @Test
            @DisplayName("Check that cargo capacity is null or not negative")
            void checkCargoCapacityNotNegative(){
                Assertions.assertTrue(vehicleDTO.getCargoCapacity() == null || vehicleDTO.getCargoCapacity() >= 0);
            }
        }

        @Nested
        class ConsumablesTests{
            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("Check that consumables is not null or empty")
            void checkConsumablesNotNullOrEmpty(String testInput){
                Assertions.assertNotEquals(testInput, vehicleDTO.getConsumables());
            }
        }

        @Nested
        class FilmsTests{
            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("Check that the films list is not null or empty")
            void checkFilmsListNotNullOrEmpty(List<String> filmsList){
                Assertions.assertNotEquals(filmsList, vehicleDTO.getFilms());
            }
        }

        @Nested
        class PilotsTests{
            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("Check that the pilots list is not null or empty")
            void checkPilotsListNotNullOrEmpty(String[] pilotsList){
                Assertions.assertNotEquals(pilotsList, vehicleDTO.getPilots());
            }
        }

        @Nested
        class URLTests{
            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("Check that the URL is not null or empty")
            void checkURLNotNullOrEmpty(String testURL){
                Assertions.assertNotEquals(testURL, vehicleDTO.getUrl());
            }

            @Test
            @DisplayName("Check that URL is valid")
            void checkURLIsValid(){
                Assertions.assertTrue(vehicleDTO.getUrl() == null || UriValidator.isUri(vehicleDTO.getUrl()));
            }
        }

        @Nested
        class DatesTests{
            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("Check that Created Date is not null or empty")
            void checkCreatedDateNotNullOrEmpty(String testInput){
                Assertions.assertNotEquals(testInput, vehicleDTO.getCreated());
            }

            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("Check that the Edited Date is not null or empty")
            void checkEditedDateNotNullOrEmpty(String testInput){
                Assertions.assertNotEquals(testInput, vehicleDTO.getEdited());
            }

            @Test
            @DisplayName("Check that the Created Date is in valid format")
            void checkCreatedDateValidFormat(){
                Assertions.assertTrue(vehicleDTO.isDateParseable(vehicleDTO.getCreated()));
            }

            @Test
            @DisplayName("Check that the Edited Date is in valid format")
            void checkEditedDateValidFormat(){
                Assertions.assertTrue(vehicleDTO.isDateParseable(vehicleDTO.getEdited()));
            }
        }
    }
}
