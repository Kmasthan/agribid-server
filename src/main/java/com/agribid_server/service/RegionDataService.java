package com.agribid_server.service;

import java.util.List;

public interface RegionDataService {

	List<String> getCountriesList();

	List<String> getStatesList(String countryName);

	List<String> getDistrictsList(String countryName, String stateName);

	List<String> getVillagesList(String countryName, String stateName, String districtName);

}
