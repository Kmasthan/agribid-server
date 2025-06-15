package com.agribid_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agribid_server.service.RegionDataService;

@RestController
@RequestMapping("region-data")
public class RegionDataController {

	@Autowired
	private RegionDataService regionDataService;

	@GetMapping("countries")
	public List<String> getCountries() {
		return regionDataService.getCountriesList();
	}

	@GetMapping("states/{country-name}")
	public List<String> getStatesListForTheCountry(@PathVariable("country-name") String countryName) {
		return regionDataService.getStatesList(countryName);
	}

	@GetMapping("districts/{country-name}/{state-name}")
	public List<String> getDistrictsListForTheState(@PathVariable("country-name") String countryName,
			@PathVariable("state-name") String stateName) {
		return regionDataService.getDistrictsList(countryName, stateName);
	}

	@GetMapping("villages/{country-name}/{state-name}/{district-name}")
	public List<String> getVillagesListForTehDistrict(@PathVariable("country-name") String countryName,
			@PathVariable("state-name") String stateName, @PathVariable("district-name") String districtName) {
		return regionDataService.getVillagesList(countryName, stateName, districtName);
	}
}
