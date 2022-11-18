package com.sparta.framework.dto;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.framework.SWAPIRegex;

public class PeopleDTO {

	@JsonProperty("films")
	private List<String> films;

	@JsonProperty("homeworld")
	private String homeworld;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("skin_color")
	private String skinColor;

	@JsonProperty("edited")
	private String edited;

	@JsonProperty("created")
	private String created;

	@JsonProperty("mass")
	private String mass;

	@JsonProperty("vehicles")
	private List<String> vehicles;

	@JsonProperty("url")
	private String url;

	@JsonProperty("hair_color")
	private String hairColor;

	@JsonProperty("birth_year")
	private String birthYear;

	@JsonProperty("eye_color")
	private String eyeColor;

	@JsonProperty("species")
	private List<String> species;

	@JsonProperty("starships")
	private List<String> starships;

	@JsonProperty("name")
	private String name;

	@JsonProperty("height")
	private String height;

	public boolean hasValidGender() {
		return gender.matches(SWAPIRegex.GENDER_PATTERN);
	}

	public boolean hasValidBirthYear() {
		return birthYear.matches(SWAPIRegex.BIRTH_YEAR_PATTERN);
	}

	public boolean hasValidHeight() {
		return height.matches(SWAPIRegex.POSITIVE_INTEGER_PATTERN);
	}

	public boolean hasValidMass() {
		return mass.matches(SWAPIRegex.POSITIVE_INTEGER_PATTERN);
	}

	private boolean isValidISO8601Date(String date) {
		try {
			LocalDate.parse(date, DateTimeFormatter.ISO_DATE_TIME);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	public boolean hasValidCreationDate() {
		return isValidISO8601Date(created);
	}

	public boolean hasValidEditedDate() {
		return isValidISO8601Date(edited);
	}

	public boolean fieldIsValidSWAPIURL(String field) {
		String value = "";
		try {
			Field f = getClass().getDeclaredField(field);
			value = (String) f.get(this);
		} catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
			return false;
		}
		return value.matches(SWAPIRegex.URL_PATTERN);
	}

	public boolean fieldIsValidListOfSWAPIURLs(String field) {
		List<String> value = new ArrayList<>();
		try {
			Field f = getClass().getDeclaredField(field);
			value = (List<String>) f.get(this);

		} catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
			return false;
		}
		for (String url: value) {
			if (!url.matches(SWAPIRegex.URL_PATTERN)) {
				return false;
			}
		}
		return true;
	}

	public List<String> getFilms(){
		return films;
	}

	public String getHomeworld(){
		return homeworld;
	}

	public String getGender(){
		return gender;
	}

	public String getSkinColor(){
		return skinColor;
	}

	public String getEdited(){
		return edited;
	}

	public String getCreated(){
		return created;
	}

	public String getMass(){
		return mass;
	}

	public List<String> getVehicles(){
		return vehicles;
	}

	public String getUrl(){
		return url;
	}

	public String getHairColor(){
		return hairColor;
	}

	public String getBirthYear(){
		return birthYear;
	}

	public String getEyeColor(){
		return eyeColor;
	}

	public List<String> getSpecies(){
		return species;
	}

	public List<String> getStarships(){
		return starships;
	}

	public String getName(){
		return name;
	}

	public String getHeight(){
		return height;
	}
}