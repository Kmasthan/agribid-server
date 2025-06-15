package com.agribid_server.mongoTemplateService;

import java.util.List;

import com.agribid_server.dto.StateDto;

public interface RegionDataMongoTemplateService {

	List<String> getCountries();

	List<String> getStatesList(String country);

	List<StateDto> getStatesList();

	List<String> getDistrictsList(String countryName, String stateName);

	List<String> getVillagesList(String countryName, String stateName, String districtName);

}
