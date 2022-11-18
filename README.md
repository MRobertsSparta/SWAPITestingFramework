# StarWars-API Testing Framework Project

**Developed by <ins>Sparta Wars: The Phantom Manish</ins>: Riya, Max,
Justin,
Abdulhadi,
Calum,
Gurjeev.**

### **Table of Contents**
* [**About Project**](#about-project)
    * [Built with](#built-with)
    * [Dependencies](#dependencies)
* [**Requirements**](#requirements)
* [**Getting Started**](#getting-started)
* [**Endpoint Testing Examples**](#endpoints)
  * [Without Framework](#without-framework)
  * [With Framework](#with-framework)
* [**BDD And Cucumber**](#bdd-and-cucumber)

## About Project

This project is developed as a team of 6, following a testing framework structure (Service Object Model), using Jackson , 
BDD Development and Cucumber, Rest-Assured, implementing JUnit and Hamcrest Tests, as well as using mocking to test the framework.

The project's functionality is building a testing framework to test the Star Wars API for testers to use.

### <span style="color: blue;">**Built With**</span>

* IntelliJ IDEA (Community Edition)

### <span style="color: blue;">**Dependencies**</span>

* hamcrest-core
* junit-jupiter
* mockito-core
* jackson-databind
* rest-assured
* cucumber-java
* cucumber-junit
* junit-vintage-engine

## Requirements

* Creating a Service Object Model as the API has a number of different responses, which consists of :
  * DTO: Classes that represent the different types of responses that can be called
  * Connection: A class which handles the connection to the live system and collecting the response.
  * Injector: A class responsible for injecting the payload into an appropriate DTO
* DTOs should also provide access to all the data that testers could find useful.
* Provide example test beds showing examples of ALL the different types of test that can be performed for all the endpoints of the Star Wars API.
* Examples of Test classes with and without using the framework to compare the benefits of using the framework.
* Exposing tests via BDD using cucumber so a non technical person can see what is being checked.


## Getting Started

Run the project using IntelliJ Community / Ultimate Edition.
Make sure to install the dependencies and software included.

Clone the repository below.
```
git clone https://github.com/MRobertsSparta/SWAPITestingFramework.git
```

# Testing Framework Structure
* connection
  * Connection Manager
  * Connection Response
  
* injection
  * Injector
  
* dto
  * FilmsCollectionDTO
  * FilmsDTO
  * PeopleCollectionDTO
  * PeopleDTO
  * PlanetCollectionDTO
  * PlanetsDTO
  * SpeciesCollectionDTO
  * SpeciesDTO
  * StarshipCollectionDTO
  * StarshipDTO
  * VehicleCollectionDTO
  * VehicleDTO

* SWAPIRegex

# Endpoint Testing Examples

## Without Framework

### Connection setUp
```
    @BeforeAll
    static void setupAll() {
        mapper = new ObjectMapper();
        //no constructor but u call upon a method instead
        client = HttpClient.newHttpClient();
        try {
            //Builder pattern
            request = HttpRequest.newBuilder()
                    .uri(new URI("https://swapi.dev/api/vehicles/?format=json"))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            vehicleCollectionDTO = mapper.readValue(new URL("https://swapi.dev/api/vehicles/?format=json"), VehicleCollectionDTO.class);
            vehicleDTO = vehicleCollectionDTO.getResults().get(0);

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
```

### Test for Checking list of starships matches the count for Starships Endpoint
```
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
```

## With Framework

### Test for Checking list of starships matches the count for Starships Endpoint

```
            @Test
            @DisplayName("Check list of starships size matches the count")
            void checkStarshipsCountMatchesListTotal(){
                Assertions.assertTrue(collectionDTO.isTotalOfResultsEqualToCount());
            }
```
# BDD And Cucumber 

## Feature File for Planets 

![feauture_file](https://github.com/MRobertsSparta/SWAPITestingFramework/blob/dev/programscreenshots/featurefile.png)

## Stepdefs File for Feature File of Planets

![stepdefs_file](https://github.com/MRobertsSparta/SWAPITestingFramework/blob/dev/programscreenshots/stepdefs.png)

## HTML File on Planets Features Tested on Cucumber 

![html_file](https://github.com/MRobertsSparta/SWAPITestingFramework/blob/dev/programscreenshots/htmlfile.png)