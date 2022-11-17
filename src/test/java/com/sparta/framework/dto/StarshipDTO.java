package com.sparta.framework.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StarshipDTO {

    @JsonProperty("max_atmosphering_speed")
    private String maxAtmospheringSpeed;

    @JsonProperty("cargo_capacity")
    private String cargoCapacity;

    @JsonProperty("films")
    private List<String> films;

    @JsonProperty("passengers")
    private String passengers;

    @JsonProperty("pilots")
    private List<Object> pilots;

    @JsonProperty("edited")
    private String edited;

    @JsonProperty("consumables")
    private String consumables;

    @JsonProperty("MGLT")
    private String mGLT;

    @JsonProperty("created")
    private String created;

    @JsonProperty("length")
    private String length;

    @JsonProperty("starship_class")
    private String starshipClass;

    @JsonProperty("url")
    private String url;

    @JsonProperty("manufacturer")
    private String manufacturer;

    @JsonProperty("crew")
    private String crew;

    @JsonProperty("hyperdrive_rating")
    private String hyperdriveRating;

    @JsonProperty("cost_in_credits")
    private String costInCredits;

    @JsonProperty("name")
    private String name;

    @JsonProperty("model")
    private String model;

    public String getMaxAtmospheringSpeed(){
        return maxAtmospheringSpeed;
    }

    public String getCargoCapacity(){
        return cargoCapacity;
    }

    public List<String> getFilms(){
        return films;
    }

    public String getPassengers(){
        return passengers;
    }

    public List<Object> getPilots(){
        return pilots;
    }

    public String getEdited(){
        return edited;
    }

    public String getConsumables(){
        return consumables;
    }

    public String getMGLT(){
        return mGLT;
    }

    public String getCreated(){
        return created;
    }

    public String getLength(){
        return length;
    }

    public String getStarshipClass(){
        return starshipClass;
    }

    public String getUrl(){
        return url;
    }

    public String getManufacturer(){
        return manufacturer;
    }

    public String getCrew(){
        return crew;
    }

    public String getHyperdriveRating(){
        return hyperdriveRating;
    }

    public String getCostInCredits(){
        return costInCredits;
    }

    public String getName(){
        return name;
    }

    public String getModel(){
        return model;
    }

    @Override
     public String toString(){
        return 
            "ResultsItem{" + 
            "max_atmosphering_speed = '" + maxAtmospheringSpeed + '\'' + 
            ",cargo_capacity = '" + cargoCapacity + '\'' + 
            ",films = '" + films + '\'' + 
            ",passengers = '" + passengers + '\'' + 
            ",pilots = '" + pilots + '\'' + 
            ",edited = '" + edited + '\'' + 
            ",consumables = '" + consumables + '\'' + 
            ",mGLT = '" + mGLT + '\'' + 
            ",created = '" + created + '\'' + 
            ",length = '" + length + '\'' + 
            ",starship_class = '" + starshipClass + '\'' + 
            ",url = '" + url + '\'' + 
            ",manufacturer = '" + manufacturer + '\'' + 
            ",crew = '" + crew + '\'' + 
            ",hyperdrive_rating = '" + hyperdriveRating + '\'' + 
            ",cost_in_credits = '" + costInCredits + '\'' + 
            ",name = '" + name + '\'' + 
            ",model = '" + model + '\'' + 
            "}";
        }
}