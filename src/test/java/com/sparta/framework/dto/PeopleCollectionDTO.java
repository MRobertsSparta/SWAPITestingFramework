package com.sparta.framework.dto;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.framework.SWAPIRegex;

public class PeopleCollectionDTO{

	@JsonProperty("next")
	private String next;

	@JsonProperty("previous")
	private String previous;

	@JsonProperty("count")
	private int count;

	@JsonProperty("results")
	private List<PeopleDTO> results;

	public int getTotalPeople() {
		ObjectMapper mapper = new ObjectMapper();
		PeopleCollectionDTO dto = this;
		List<PeopleDTO> peopleList = new ArrayList<>(dto.getResults());
		while (dto.getNext() != null) {
			try {
				dto = new ObjectMapper().readValue(
						new URL(dto.getNext()), PeopleCollectionDTO.class);
			} catch (IOException e) {
				System.err.println(e);
			}
			peopleList.addAll(dto.getResults());
		}
		return peopleList.size();
	}

	public boolean hasValidNextURL() {
		return (next == null || next.matches(SWAPIRegex.URL_PATTERN));
	}

	public boolean hasValidPreviousURL() {
		return (previous == null || previous.matches(SWAPIRegex.URL_PATTERN));
	}

	public String getNext(){
		return next;
	}

	public String getPrevious(){
		return previous;
	}

	public int getCount(){
		return count;
	}

	public List<PeopleDTO> getResults(){
		return results;
	}
}