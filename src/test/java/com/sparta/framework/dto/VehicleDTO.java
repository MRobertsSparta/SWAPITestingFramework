package com.sparta.framework.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleDTO {

	@JsonProperty("max_atmosphering_speed")
	private String maxAtmospheringSpeed;

	@JsonProperty("cargo_capacity")
	private String cargoCapacity;

	@JsonProperty("films")
	private List<String> films;

	@JsonProperty("passengers")
	private String passengers;

	@JsonProperty("pilots")
	private String[] pilots;

	@JsonProperty("edited")
	private String edited;

	@JsonProperty("consumables")
	private String consumables;

	@JsonProperty("created")
	private String created;

	@JsonProperty("length")
	private String length;

	@JsonProperty("url")
	private String url;

	@JsonProperty("manufacturer")
	private String manufacturer;

	@JsonProperty("crew")
	private String crew;

	@JsonProperty("vehicle_class")
	private String vehicleClass;

	@JsonProperty("cost_in_credits")
	private String costInCredits;

	@JsonProperty("name")
	private String name;

	@JsonProperty("model")
	private String model;

	public String getEdited(){
		return edited;
	}

	public String getConsumables(){
		return consumables;
	}

	public String getCreated(){
		return created;
	}

	public String getUrl(){
		return url;
	}

	public String getManufacturer(){
		return manufacturer;
	}

	public String getVehicleClass(){
		return vehicleClass;
	}

	public String getName(){
		return name;
	}

	public String getModel(){
		return model;
	}


	/*
	CONVENIENCE METHODS
	CONVENIENCE FIELDS
	ETC HERE
	 */

	//get list of classes to compare against
	public final List<String> vehicleClassList = List.of("wheeled", "repulsorcraft", "starfighter", "airspeeder", "space/planetary bomber", "assault walker", "walker", "sail barge",
			"repulsorcraft cargo skiff", "speeder", "landing craft", "submarine", "gunship", "transport",
			"wheeled walker", "fire suppression ship", "droid starfighter", "air speeder", "droid tank");

	//check if first letter is uppercase - for manufacturer and name test
	public boolean isFirstCharUpperCase(String toCheck){
		char toUpper = toCheck.toUpperCase().charAt(0);
		char originalChar = toCheck.charAt(0);
		return toUpper == originalChar;
	}

	//check if date is parsable - for created and edited test
	public boolean isDateParseable(String date){
		try {
			LocalDate.parse(date, DateTimeFormatter.ISO_DATE_TIME);
			return true;
		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	CHANGED SOME OF THE GETTERS TO MORE APPROPRIATE DATA TYPES
	Makes testing easier
	 */

	public Integer getCostInCredits(){
		if(!costInCredits.equals("unknown")){
			return Integer.valueOf(costInCredits);
		}
		else {
			return null;
		}
	}

	public Integer getCrew(){
		return Integer.valueOf(crew);
	}

	public Double getLength(){
		if(!length.equals("unknown")){
			return Double.valueOf(length);
		}
		else {
			return null;
		}
	}

	public Integer getMaxAtmospheringSpeed(){
		if(!maxAtmospheringSpeed.equals("unknown")){
			return Integer.valueOf(maxAtmospheringSpeed);
		}
		else {
			return null;
		}
	}

	public Integer getCargoCapacity(){
		if(!cargoCapacity.equals("unknown")){
			return Integer.valueOf(cargoCapacity);
		}
		else {
			return null;
		}
	}

	public List<String> getFilms(){
		return films;
	}

	public Integer getPassengers(){
		if(!passengers.equals("unknown")){
			return Integer.valueOf(passengers);
		}
		else {
			return null;
		}
	}

	public String[] getPilots(){
		return pilots;
	}

	@Override
	public String toString(){
		return
				"ResultsItem{" +
						"max_atmosphering_speed = '" + maxAtmospheringSpeed + '\'' +
						",cargo_capacity = '" + cargoCapacity + '\'' +
						",films = '" + films + '\'' +
						",passengers = '" + passengers + '\'' +
						",pilots = '" + Arrays.toString(pilots) + '\'' +
						",edited = '" + edited + '\'' +
						",consumables = '" + consumables + '\'' +
						",created = '" + created + '\'' +
						",length = '" + length + '\'' +
						",url = '" + url + '\'' +
						",manufacturer = '" + manufacturer + '\'' +
						",crew = '" + crew + '\'' +
						",vehicle_class = '" + vehicleClass + '\'' +
						",cost_in_credits = '" + costInCredits + '\'' +
						",name = '" + name + '\'' +
						",model = '" + model + '\'' +
						"}";
	}
}
