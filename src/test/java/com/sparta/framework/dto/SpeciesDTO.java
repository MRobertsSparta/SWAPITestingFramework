package com.sparta.framework.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


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
