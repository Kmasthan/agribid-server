package com.agribid_server.dto;

import java.util.List;

public class CountryDto {

	private String name;
	private List<StateDto> states;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<StateDto> getStates() {
		return states;
	}

	public void setStates(List<StateDto> states) {
		this.states = states;
	}

}
