package com.sparta.framework.dto;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.framework.SWAPIRegex;
import org.junit.jupiter.api.Assertions;

import static com.sparta.framework.connection.ConnectionManager.from;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StarshipCollectionDTO {

    @JsonProperty("next")
    private String next;

    @JsonProperty("previous")
    private String previous;

    @JsonProperty("count")
    private Integer count;

    @JsonProperty("results")
    private List<StarshipDTO> results;

    public String getNext(){
        return next;
    }

    public String getPrevious(){
        return previous;
    }

    public Integer getCount(){
        return count;
    }

    public List<StarshipDTO> getResults(){
        return results;
    }

    public boolean isCountGreaterThanOrEqualsZero() { return count >= 0; }

    public boolean isCountGreaterThanZero() { return count > 0; }

    public boolean isResultLengthGreaterThanZero() { return results.size() > 0; }

    public boolean isTotalOfResultsEqualToCount() {
        List<StarshipDTO> starshipList = new ArrayList<>();
        StarshipCollectionDTO collectionDTO = this;

        while(collectionDTO.getNext() != null) {
            starshipList.addAll(collectionDTO.getResults());
            collectionDTO = from().URL(collectionDTO.getNext()).getResponse().getBodyAs(StarshipCollectionDTO.class);

            if(collectionDTO.getNext() == null){
                starshipList.addAll(collectionDTO.getResults());
            }
        }

        return collectionDTO.getCount() == starshipList.size();
    }

    public boolean fieldIsValidSWAPIURL(String field) {
        String value = "";
        try {
            Field f = getClass().getDeclaredField(field);
            value = (String) f.get(this);
        } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
            System.err.println(e);
            return false;
        }
        return value.matches(SWAPIRegex.URL_PATTERN);
    }

    public boolean fieldIsValidSWAPIURList(String field) {
        List<String> value = new ArrayList<>();
        try {
            Field f = getClass().getDeclaredField(field);
            value = (List<String>) f.get(this);

        } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
            System.err.println(e);
            return false;
        }
        for (String url: value) {
            if (!url.matches(SWAPIRegex.URL_PATTERN)) {
                return false;
            }
        }
        return true;
    }

    @Override
     public String toString(){
        return 
            "SWAPIStarshipsDTO{" + 
            "next = '" + next + '\'' + 
            ",previous = '" + previous + '\'' + 
            ",count = '" + count + '\'' + 
            ",results = '" + results + '\'' + 
            "}";
        }
}