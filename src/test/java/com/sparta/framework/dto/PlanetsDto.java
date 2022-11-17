package com.sparta.framework.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlanetsDto {

	@JsonProperty("films")
	private List<String> films;

	@JsonProperty("edited")
	private String edited;

	@JsonProperty("created")
	private String created;

	@JsonProperty("climate")
	private String climate;

	@JsonProperty("rotation_period")
	private String rotationPeriod;

	@JsonProperty("url")
	private String url;

	@JsonProperty("population")
	private String population;

	@JsonProperty("orbital_period")
	private String orbitalPeriod;

	@JsonProperty("surface_water")
	private String surfaceWater;

	@JsonProperty("diameter")
	private String diameter;

	@JsonProperty("gravity")
	private String gravity;

	@JsonProperty("name")
	private String name;

	@JsonProperty("residents")
	private List<String> residents;

	@JsonProperty("terrain")
	private String terrain;

	public List<String> getFilms(){
		return films;
	}

	public String getEdited(){
		return edited;
	}

	public String getCreated(){
		return created;
	}

	public String getClimate(){
		return climate;
	}

	public String getRotationPeriod(){
		return rotationPeriod;
	}

	public String getUrl(){
		return url;
	}

	public String getPopulation(){
		return population;
	}

	public String getOrbitalPeriod(){
		return orbitalPeriod;
	}

	public String getSurfaceWater(){
		return surfaceWater;
	}

	public String getDiameter(){
		return diameter;
	}

	public String getGravity(){
		return gravity;
	}

	public String getName(){
		return name;
	}

	public List<String> getResidents(){
		return residents;
	}

	public String getTerrain(){
		return terrain;
	}

	@Override
 	public String toString(){
		return 
			"ResultsItem{" + 
			"films = '" + films + '\'' + 
			",edited = '" + edited + '\'' + 
			",created = '" + created + '\'' + 
			",climate = '" + climate + '\'' + 
			",rotation_period = '" + rotationPeriod + '\'' + 
			",url = '" + url + '\'' + 
			",population = '" + population + '\'' + 
			",orbital_period = '" + orbitalPeriod + '\'' + 
			",surface_water = '" + surfaceWater + '\'' + 
			",diameter = '" + diameter + '\'' + 
			",gravity = '" + gravity + '\'' + 
			",name = '" + name + '\'' + 
			",residents = '" + residents + '\'' + 
			",terrain = '" + terrain + '\'' + 
			"}";
		}
}