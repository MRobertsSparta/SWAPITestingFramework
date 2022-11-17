package com.sparta.framework.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StarshipCollectionDTO {

    @JsonProperty("next")
    private String next;

    @JsonProperty("previous")
    private Object previous;

    @JsonProperty("count")
    private Integer count;

    @JsonProperty("results")
    private List<StarshipDTO> results;

    public String getNext(){
        return next;
    }

    public Object getPrevious(){
        return previous;
    }

    public Integer getCount(){
        return count;
    }

    public List<StarshipDTO> getResults(){
        return results;
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