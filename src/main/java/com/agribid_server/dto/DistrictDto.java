package com.agribid_server.dto;

import java.util.List;

public class DistrictDto {
	private String districtName;
	private List<String> villages;



	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public List<String> getVillages() {
		return villages;
	}

	public void setVillages(List<String> villages) {
		this.villages = villages;
	}
}
