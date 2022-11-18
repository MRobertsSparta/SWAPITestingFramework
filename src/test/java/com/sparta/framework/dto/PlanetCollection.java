package com.sparta.framework.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetCollection {

	@JsonProperty("next")
	private String next;

	@JsonProperty("previous")
	private static Object previous;

	@JsonProperty("count")
	private int count;

	@JsonProperty("results")
	private List<PlanetsDto> results;

	public String getNext(){
		return next;
	}

	public static Object getPrevious(){
		return previous;
	}

	public int getCount(){
		return count;
	}

	public List<PlanetsDto> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"next = '" + next + '\'' + 
			",previous = '" + previous + '\'' + 
			",count = '" + count + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}
}