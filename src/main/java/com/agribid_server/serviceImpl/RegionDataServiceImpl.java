package com.agribid_server.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agribid_server.dto.DistrictDto;
import com.agribid_server.dto.StateDto;
import com.agribid_server.exception.RegionDataException;
import com.agribid_server.mongoTemplateService.RegionDataMongoTemplateService;
import com.agribid_server.service.RegionDataService;

@Service
public class RegionDataServiceImpl implements RegionDataService {

	@Autowired
	private RegionDataMongoTemplateService regionDataMongoTemplateService;

	List<DistrictDto> selectedStateDistrictsFullData = new ArrayList<>();

	@Override
	public List<String> getCountriesList() {
		return regionDataMongoTemplateService.getCountries();
	}

	@Override
	public List<String> getStatesList(String country) {
		return regionDataMongoTemplateService.getStatesList(country);
	}

	@Override
	public List<String> getDistrictsList(String countryName, String stateName) {
		try {
			List<StateDto> statesList = regionDataMongoTemplateService.getStatesList();

			List<DistrictDto> ditrictsList = new ArrayList<>();
			if (statesList != null && !statesList.isEmpty()) {
				statesList.stream().filter(state -> stateName.equals(state.getStateName())).findAny()
						.map(StateDto::getDistricts).ifPresent(ditrictsList::addAll);
			} else {
				return regionDataMongoTemplateService.getDistrictsList(countryName, stateName);
			}

			if (!ditrictsList.isEmpty()) {
				selectedStateDistrictsFullData.clear();
				selectedStateDistrictsFullData.addAll(ditrictsList);
				return ditrictsList.stream().map(DistrictDto::getDistrictName).collect(Collectors.toList());
			} else {
				throw new RegionDataException("District's data not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RegionDataException(e.getMessage());
		}
	}

	@Override
	public List<String> getVillagesList(String countryName, String stateName, String districtName) {
		try {
			if(!selectedStateDistrictsFullData.isEmpty()) {
				return selectedStateDistrictsFullData.stream().filter(district -> districtName.trim().equals(district.getDistrictName())).findAny()
						.map(DistrictDto::getVillages).orElseThrow(() -> new RegionDataException("District/Villages data not found"));
			} else {
				return regionDataMongoTemplateService.getVillagesList(countryName, stateName, districtName);
			}
		} catch (Exception e) {
			throw new RegionDataException(e.getMessage());
		}
	}

}
