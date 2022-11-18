package com.sparta.framework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.framework.dto.SpeciesDTO;

import java.util.List;

public class SpeciesCollectionDTO{

	@JsonProperty("next")
	private String next;

	@JsonProperty("previous")
	private Object previous;

	@JsonProperty("count")
	private int count;

	@JsonProperty("results")
	private List<SpeciesDTO> results;

	public String getNext(){
		return next;
	}

	public Object getPrevious(){
		return previous;
	}

	public int getCount(){
		return count;
	}

	public List<SpeciesDTO> getResults(){
		return results;
	}

	@Override
	public String toString() {
		return "SpeciesCollectionDTO{" +
				"next='" + next + '\'' +
				", previous=" + previous +
				", count=" + count +
				", results=" + results +
				'}';
	}
}
