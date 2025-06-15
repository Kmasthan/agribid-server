package com.agribid_server.dto;

import java.util.List;

public class StateDto {
	private String stateName;
	private List<DistrictDto> districts;



	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public List<DistrictDto> getDistricts() {
		return districts;
	}

	public void setDistricts(List<DistrictDto> districts) {
		this.districts = districts;
	}

}
