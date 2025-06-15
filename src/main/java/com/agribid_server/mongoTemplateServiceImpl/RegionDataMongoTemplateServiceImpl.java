package com.agribid_server.mongoTemplateServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.agribid_server.dto.DistrictDto;
import com.agribid_server.dto.StateDto;
import com.agribid_server.exception.RegionDataException;
import com.agribid_server.mongoTemplateService.RegionDataMongoTemplateService;

@Service
public class RegionDataMongoTemplateServiceImpl implements RegionDataMongoTemplateService {

	List<StateDto> selectedCountryStatesFullData = new ArrayList<>();

	private static final String REGION_DATA_COLLECTION = "region-data";

	private static final String COUNTRY_NAME = "countryName";

	private static final String STATES = "states";

	private static final String STATES_STATE_NAME = "states.stateName";

	private static final String STATES_DISTRICTS = "states.districts";

	private static final String STATES_DISTRICTS_DISTRICT_NAME = "states.districts.districtName";

	private static final String STATES_DISTRICTS_VILLAGES = "states.districts.villages";

	private static final String VILLAGES = "villages";

	private static final String ID = "_id";

	List<String> countries = Arrays.asList("India");

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<String> getCountries() {
		try {
			return countries;
		} catch (Exception e) {
			throw new RegionDataException(e.getMessage());
		}
	}

	@Override
	public List<String> getStatesList(String countryName) {
		try {
			MatchOperation matchQuery = Aggregation.match(Criteria.where(COUNTRY_NAME).is(countryName));

			UnwindOperation unwindOperation = Aggregation.unwind(STATES);

			ProjectionOperation projection = Aggregation.project(STATES_STATE_NAME, STATES_DISTRICTS).andExclude(ID);

			Aggregation aggrigation = Aggregation.newAggregation(matchQuery, unwindOperation, projection);

			AggregationResults<StateDto> aggregationResult = mongoTemplate.aggregate(aggrigation,
					REGION_DATA_COLLECTION, StateDto.class);

			if (aggregationResult != null && aggregationResult.getMappedResults() != null) {
				selectedCountryStatesFullData.clear();
				selectedCountryStatesFullData.addAll(aggregationResult.getMappedResults());
				return aggregationResult.getMappedResults().stream().map(StateDto::getStateName)
						.collect(Collectors.toList());
			} else {
				throw new RegionDataException("States for the country " + countryName + " not found");
			}
		} catch (Exception e) {
			throw new RegionDataException(e.getMessage());
		}
	}

	@Override
	public List<StateDto> getStatesList() {
		return selectedCountryStatesFullData;
	}

	@Override
	public List<String> getDistrictsList(String countryName, String stateName) {
		try {
			MatchOperation matchOperation1 = Aggregation.match(Criteria.where(COUNTRY_NAME).is(countryName));

			UnwindOperation unwindOperation1 = Aggregation.unwind(STATES);

			MatchOperation matchOperation2 = Aggregation.match(Criteria.where(STATES_STATE_NAME).is(stateName));

			ProjectionOperation projectionOperation = Aggregation.project(STATES_DISTRICTS).andExclude(ID);

			Aggregation aggregation = Aggregation.newAggregation(matchOperation1, unwindOperation1, matchOperation2,
					projectionOperation);

			AggregationResults<StateDto> aggregationResult = mongoTemplate.aggregate(aggregation,
					REGION_DATA_COLLECTION, StateDto.class);

			String exceptionMsg = "Districts for the state " + stateName + " not found";
			if (aggregationResult != null && aggregationResult.getMappedResults() != null
					&& !aggregationResult.getMappedResults().isEmpty()) {
				List<String> districtslist = aggregationResult.getMappedResults().stream().map(StateDto::getDistricts)
						.flatMap(List::stream).map(DistrictDto::getDistrictName).collect(Collectors.toList());
				if (districtslist != null && !districtslist.isEmpty()) {
					return districtslist;
				} else {
					throw new RegionDataException(exceptionMsg);
				}
			} else {
				throw new RegionDataException(exceptionMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RegionDataException(e.getMessage());
		}
	}

	@Override
	public List<String> getVillagesList(String countryName, String stateName, String districtName) {
		try {
			MatchOperation matchOperation1 = Aggregation.match(Criteria.where(COUNTRY_NAME).is(countryName));

			UnwindOperation unwindOperation1 = Aggregation.unwind(STATES);

			MatchOperation matchOperation2 = Aggregation.match(Criteria.where(STATES_STATE_NAME).is(stateName));

			UnwindOperation unwindOperation2 = Aggregation.unwind(STATES_DISTRICTS);

			MatchOperation matchOperation3 = Aggregation
					.match(Criteria.where(STATES_DISTRICTS_DISTRICT_NAME).is(districtName));

			// Project the villages and rename STATES_DISTRICTS_VILLAGES to villages
			ProjectionOperation projectionOperation = Aggregation.project().and(STATES_DISTRICTS_VILLAGES).as(VILLAGES)
					.andExclude(ID);

			Aggregation aggregation = Aggregation.newAggregation(matchOperation1, unwindOperation1, matchOperation2,
					unwindOperation2, matchOperation3, projectionOperation);

			AggregationResults<DistrictDto> aggregationResult = mongoTemplate.aggregate(aggregation,
					REGION_DATA_COLLECTION, DistrictDto.class);

			String exceptionMsg = "Villages for the district " + districtName + " not found";
			if (aggregationResult != null && aggregationResult.getMappedResults() != null
					&& !aggregationResult.getMappedResults().isEmpty()) {

				List<String> villagesList = aggregationResult.getMappedResults().get(0).getVillages();
				if (villagesList != null && !villagesList.isEmpty()) {
					return villagesList;
				} else {
					throw new RegionDataException(exceptionMsg);
				}
			} else {
				throw new RegionDataException(exceptionMsg);
			}
		} catch (Exception e) {
			throw new RegionDataException(e.getMessage());
		}
	}

}
