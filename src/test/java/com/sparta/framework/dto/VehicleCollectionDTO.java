package com.sparta.framework.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleCollectionDTO {

	@JsonProperty("next")
	private String next;

	@JsonProperty("previous")
	private String previous;

	@JsonProperty("count")
	private Integer count;

	@JsonProperty("results")
	private List<VehicleDTO> results;

	public String getNext(){
		return next;
	}

	public String getPrevious(){
		return previous;
	}

	public Integer getCount(){
		return count;
	}

	public List<VehicleDTO> getResults(){
		return results;
	}

	@Override
 	public String toString(){
		return
			"VehicleDTO{" +
			"next = '" + next + '\'' +
			",previous = '" + previous + '\'' +
			",count = '" + count + '\'' +
			",results = '" + results + '\'' +
			"}";
		}
}
