package com.sparta.framework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.framework.SWAPIRegex;
import com.sparta.framework.dto.SpeciesDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SpeciesCollectionDTO{

	@JsonProperty("next")
	private String next;

	@JsonProperty("previous")
	private String previous;

	@JsonProperty("count")
	private int count;

	@JsonProperty("results")
	private List<SpeciesDTO> results;

	public int getTotalSpecies() {
		ObjectMapper mapper = new ObjectMapper();
		SpeciesCollectionDTO dto = this;
		List<SpeciesDTO> speciesList = new ArrayList<>(dto.getResults());
		while (dto.getNext() != null) {
			try {
				dto = new ObjectMapper().readValue(
						new URL(dto.getNext()), SpeciesCollectionDTO.class);
			} catch (IOException e) {
				System.err.println(e);
			}
			speciesList.addAll(dto.getResults());
		}
		return speciesList.size();
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
