package com.sparta.framework.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PeopleCollectionDTO{

	@JsonProperty("next")
	private String next;

	@JsonProperty("previous")
	private Object previous;

	@JsonProperty("count")
	private int count;

	@JsonProperty("results")
	private List<PeopleDTO> results;

	public String getNext(){
		return next;
	}

	public Object getPrevious(){
		return previous;
	}

	public int getCount(){
		return count;
	}

	public List<PeopleDTO> getResults(){
		return results;
	}
}