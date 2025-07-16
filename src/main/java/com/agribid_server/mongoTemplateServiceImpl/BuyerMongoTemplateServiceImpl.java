package com.agribid_server.mongoTemplateServiceImpl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.BidDetailsDto;
import com.agribid_server.dto.CropsBiddingDto;
import com.agribid_server.entity.CropBidDetails;
import com.agribid_server.entity.CropListing;
import com.agribid_server.enums.CropStatus;
import com.agribid_server.exception.BuyerException;
import com.agribid_server.mongoTemplateService.BuyerMongoTemplateService;

import jakarta.validation.constraints.NotEmpty;

@Service
public class BuyerMongoTemplateServiceImpl implements BuyerMongoTemplateService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<CropsBiddingDto> getCropListingsForBidding(String country, String state, String district,
			String village) {
		try {

			MatchOperation matchOperation = Aggregation.match(Criteria.where("cropsList.country").is(country)
					.and("cropsList.state").is(state).and("cropsList.district").is(district).and("cropsList.village")
					.is(village).and("cropsList.status").is("ACTIVE"));

			UnwindOperation unwindOperation = Aggregation.unwind("cropsList");

			ProjectionOperation projectOperation = Aggregation.project().and("cropsList").as("cropData")
					.and("farmerEmail").as("farmerEmail").and("farmerId").as("farmerId").and("farmerName")
					.as("farmerName").and("farmerPhone").as("farmerPhone").andExclude("_id");

			Aggregation aggregation = Aggregation.newAggregation(matchOperation, unwindOperation, matchOperation,
					projectOperation);

			AggregationResults<CropsBiddingDto> aggregationResult = mongoTemplate.aggregate(aggregation,
					"farmer-crop-listing", CropsBiddingDto.class);

			if (!aggregationResult.getMappedResults().isEmpty()) {
				return aggregationResult.getMappedResults();
			}
			throw new BuyerException("Crops for bidding not found!");
		} catch (Exception e) {
			throw new BuyerException(e.getMessage());
		}
	}

	@Override
	public APISuccessMessage saveNewBidFroCrop(String farmerId, String cropId, BidDetailsDto bidDetails) {
		try {
			// Performing MATCH operation using the FarmerId
			Query matchQuery = new Query(Criteria.where("farmerId").is(farmerId).and("cropId").is(cropId));

			// Performing UPDATE operation using the Farmer details and Crop bid details
			Update updateQuery = new Update().setOnInsert("farmerId", farmerId).setOnInsert("cropId", cropId)
					.setOnInsert("createdAt", LocalDate.now()).push("bidDetails", bidDetails);

			// Performs an UPSERT If no document is found that matches the query, a new
			// document is created and inserted by combining the query document and the
			// update document.
			mongoTemplate.upsert(matchQuery, updateQuery, CropBidDetails.class);

			return new APISuccessMessage("Bid placed succesfully", "success");
		} catch (Exception e) {
			throw new BuyerException(e.getMessage());
		}
	}

	@Override
	public Map<String, CropListing> getCropsListByCropIds(
			@NotEmpty(message = "Crops ids required") Set<String> cropIds) {
		try {

			MatchOperation matchOperation = Aggregation
					.match(Criteria.where("cropsList._id").in(cropIds).and("cropsList.status").is(CropStatus.ACTIVE));

			UnwindOperation unwindOperation = Aggregation.unwind("cropsList");

			ProjectionOperation projectionOperation = Aggregation.project().and("cropsList._id").as("_id")
					.and("cropsList.country").as("country").and("cropsList.state").as("state").and("cropsList.district")
					.as("district").and("cropsList.village").as("village").and("cropsList.cropName").as("cropName")
					.and("cropsList.weight").as("weight").and("cropsList.measure").as("measure")
					.and("cropsList.quality").as("quality").and("cropsList.status").as("status")
					.and("cropsList.listedAt").as("listedAt");

			Aggregation aggregation = Aggregation.newAggregation(matchOperation, unwindOperation, projectionOperation);

			AggregationResults<CropListing> aggregationResults = mongoTemplate.aggregate(aggregation,
					"farmer-crop-listing", CropListing.class);

			if (!aggregationResults.getMappedResults().isEmpty()) {
				return new HashMap<>(aggregationResults.getMappedResults().stream()
						.collect(Collectors.toMap(CropListing::getId, Function.identity())));
			} else {
				throw new BuyerException("Bids crops not found");
			}
		} catch (Exception e) {
			throw new BuyerException(e.getMessage());
		}
	}

}
