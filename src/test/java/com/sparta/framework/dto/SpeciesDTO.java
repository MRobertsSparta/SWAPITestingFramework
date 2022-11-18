package com.sparta.framework.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.framework.SWAPIRegex;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static com.sparta.framework.SWAPIRegex.STARTS_WITH_UPPERCASE_PATTERN;
import static com.sparta.framework.SWAPIRegex.URL_PATTERN;


public class SpeciesDTO {

	@JsonProperty("films")
	private List<String> films;

	@JsonProperty("skin_colors")
	private String skinColors;

	@JsonProperty("homeworld")
	private String homeworld;

	@JsonProperty("edited")
	private String edited;

	@JsonProperty("created")
	private String created;

	@JsonProperty("eye_colors")
	private String eyeColors;

	@JsonProperty("language")
	private String language;

	@JsonProperty("classification")
	private String classification;

	@JsonProperty("people")
	private List<String> people;

	@JsonProperty("url")
	private String url;

	@JsonProperty("hair_colors")
	private String hairColors;

	@JsonProperty("average_height")
	private String averageHeight;

	@JsonProperty("name")
	private String name;

	@JsonProperty("designation")
	private String designation;

	@JsonProperty("average_lifespan")
	private String averageLifespan;

	public List<String> getFilms(){
		return films;
	}

	public String getSkinColors(){
		return skinColors;
	}

	public String getHomeworld(){
		return homeworld;
	}

	public String getEdited(){
		return edited;
	}

	public String getCreated(){
		return created;
	}

	public String getEyeColors(){
		return eyeColors;
	}

	public String getLanguage(){
		return language;
	}

	public String getClassification(){
		return classification;
	}

	public List<String> getPeople(){
		return people;
	}

	public String getUrl(){
		return url;
	}

	public String getHairColors(){
		return hairColors;
	}

	public String getAverageHeight(){
		return averageHeight;
	}

	public String getName(){
		return name;
	}

	public String getDesignation(){
		return designation;
	}

	public String getAverageLifespan(){
		return averageLifespan;
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

	public boolean averageHeightIsAIntegerString(String height) {
		return NumberUtils.isParsable(height);
	}

	public boolean validSWAPIUrl(String url){
		return url.equals(URL_PATTERN);
	}

	public boolean validStartsWithUpperCasePattern(String string){
		return string.equals(STARTS_WITH_UPPERCASE_PATTERN);
	}

	@Override
	public String toString() {
		return "SpeciesDTO{" +
				"films=" + films +
				", skinColors='" + skinColors + '\'' +
				", homeworld='" + homeworld + '\'' +
				", edited='" + edited + '\'' +
				", created='" + created + '\'' +
				", eyeColors='" + eyeColors + '\'' +
				", language='" + language + '\'' +
				", classification='" + classification + '\'' +
				", people=" + people +
				", url='" + url + '\'' +
				", hairColors='" + hairColors + '\'' +
				", averageHeight='" + averageHeight + '\'' +
				", name='" + name + '\'' +
				", designation='" + designation + '\'' +
				", averageLifespan='" + averageLifespan + '\'' +
				'}';
	}


}
