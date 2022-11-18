package com.sparta.framework.dto;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.framework.connection.ConnectionResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetCollection {
	private static ObjectMapper mapper;

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

	public int getTotalSize(){
		mapper = new ObjectMapper();
		List<PlanetsDto> planetsList = new ArrayList<>();
		PlanetCollection collectionDto = this;
		do {
			planetsList.addAll(collectionDto.getResults());
			try {
				collectionDto = mapper.readValue(new URL(collectionDto.getNext()), PlanetCollection.class);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if (collectionDto.getNext() == null) {
				planetsList.addAll(collectionDto.getResults());
			}
		} while (collectionDto.getNext() != null);
		return planetsList.size();
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