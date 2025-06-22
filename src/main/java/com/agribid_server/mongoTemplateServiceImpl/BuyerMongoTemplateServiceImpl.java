package com.agribid_server.mongoTemplateServiceImpl;

import java.time.LocalDate;
import java.util.List;

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
import com.agribid_server.exception.BuyerException;
import com.agribid_server.mongoTemplateService.BuyerMongoTemplateService;

@Service
public class BuyerMongoTemplateServiceImpl implements BuyerMongoTemplateService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<CropsBiddingDto> getCropListingsForBidding(String country, String state, String district,
			String village) {
		try {

			MatchOperation matchOperation = Aggregation
					.match(Criteria.where("cropsList.country").is(country).and("cropsList.state").is(state)
							.and("cropsList.district").is(district).and("cropsList.village").is(village));

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
	public APISuccessMessage saveNewBidFroCrop(String farmerId, String cropId,
			BidDetailsDto bidDetails) {
		try {
			// Performing MATCH operation using the FarmerId
			Query matchQuery = new Query(Criteria.where("farmerId").is(farmerId).and("cropId").is(cropId));

			// Performing UPDATE operation using the Farmer details and Crop bid details
			Update updateQuery = new Update().setOnInsert("farmerId", farmerId).setOnInsert("cropId", cropId)
					.setOnInsert("createdAt", LocalDate.now())
					.push("bidDetails", bidDetails);

			// Performs an UPSERT If no document is found that matches the query, a new
			// document is created and inserted by combining the query document and the
			// update document.
			mongoTemplate.upsert(matchQuery, updateQuery, CropBidDetails.class);

			return new APISuccessMessage("Bid placed succesfully", "success");
		} catch (Exception e) {
			throw new BuyerException(e.getMessage());
		}
	}

}
