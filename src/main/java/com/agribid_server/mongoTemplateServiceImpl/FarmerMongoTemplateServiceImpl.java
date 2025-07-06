package com.agribid_server.mongoTemplateServiceImpl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.CropBidsDto;
import com.agribid_server.entity.CropBidDetails;
import com.agribid_server.entity.CropListing;
import com.agribid_server.entity.FarmerCropListingDetails;
import com.agribid_server.entity.SoldCropBidDetails;
import com.agribid_server.exception.FarmerException;
import com.agribid_server.mongoTemplateService.FarmerMongoTemplateService;
import com.mongodb.client.result.UpdateResult;

@Service
public class FarmerMongoTemplateServiceImpl implements FarmerMongoTemplateService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public APISuccessMessage addCropWithUpsert(String farmerId, String farmerName, String farmerPhone,
			String farmerEmail, CropListing newCrop) {
		try {

			// Performing MATCH operation using the FarmerId
			Query matchQuery = new Query(Criteria.where("farmerId").is(farmerId));

			// Performing UPDATE operation using the Farmer details and New Crop
			Update updateQuery = new Update().setOnInsert("farmerId", farmerId).setOnInsert("farmerName", farmerName)
					.setOnInsert("farmerPhone", farmerPhone).setOnInsert("farmerEmail", farmerEmail)
					.push("cropsList", newCrop);

			// Performs an UPSERT If no document is found that matches the query, a new
			// document is created and inserted by combining the query document and the
			// update document.
			mongoTemplate.upsert(matchQuery, updateQuery, FarmerCropListingDetails.class);

			return new APISuccessMessage("New crop listed succesfully", "success");
		} catch (Exception e) {
			throw new FarmerException(e.getMessage());
		}
	}

	@Override
	public APISuccessMessage deleteCropFromListing(String farmerId, String cropId) {
		try {
			// Performing MATCH operation using the FarmerId
			Query matchQuery = new Query(Criteria.where("farmerId").is(farmerId.trim()));

			// Performing DELETE/PULL operation using the cropId
			Update update = new Update().pull("cropsList", Query.query(Criteria.where("_id").is(cropId.trim())));

			// Performs the UPDATEFIRST
			UpdateResult result = mongoTemplate.updateFirst(matchQuery, update, FarmerCropListingDetails.class);

			if (result.getModifiedCount() == 0) {
				throw new FarmerException("Crop not found");
			}
			return new APISuccessMessage("Crop deleted succesfully", "success");
		} catch (Exception e) {
			throw new FarmerException(e.getMessage());
		}
	}

	@Override
	public APISuccessMessage updateCropInListing(String farmerId, String cropId, CropListing updatedCrop) {
		try {
			// Performing MATCH operation using the FarmerId
			Query matchQuery = new Query(
					Criteria.where("farmerId").is(farmerId.trim()).and("cropsList._id").is(cropId));

			// Performing UPDATE operation using the updatedCrop
			Update update = new Update().set("cropsList.$.country", updatedCrop.getCountry())
					.set("cropsList.$.state", updatedCrop.getState())
					.set("cropsList.$.district", updatedCrop.getDistrict())
					.set("cropsList.$.village", updatedCrop.getVillage())
					.set("cropsList.$.cropName", updatedCrop.getCropName())
					.set("cropsList.$.weight", updatedCrop.getWeight())
					.set("cropsList.$.measure", updatedCrop.getMeasure())
					.set("cropsList.$.quality", updatedCrop.getQuality())
					.set("cropsList.$.status", updatedCrop.getStatus())
					.set("cropsList.$.listedAt", updatedCrop.getListedAt())
					.set("cropsList.$.modifiedAt", LocalDate.now());

			// Performs the UPDATEFIRST
			mongoTemplate.updateFirst(matchQuery, update, FarmerCropListingDetails.class);

			return new APISuccessMessage("Crop updated succesfully", "SUCCESS");
		} catch (Exception e) {
			throw new FarmerException(e.getMessage());
		}
	}

	@Override
	public HashMap<String, CropBidDetails> getCropBidsDetailsWithHighestBid(String farmerId) {
		try {
			MatchOperation matchOperation = Aggregation.match(Criteria.where("farmerId").is(farmerId));

			AggregationOperation projectSortedBid = context -> new Document("$project",
					new Document("highestBid",
							new Document("$arrayElemAt",
									List.of(new Document("$sortArray",
											new Document("input", "$bidDetails").append("sortBy",
													new Document("bidAmount", -1))),
											0)))
							.append("_id", 0).append("farmerId", 1).append("cropId", 1));
			Aggregation aggregation = Aggregation.newAggregation(matchOperation, projectSortedBid);

			AggregationResults<CropBidDetails> aggregationResult = mongoTemplate.aggregate(aggregation,
					"crop-bid-details", CropBidDetails.class);
			if (!aggregationResult.getMappedResults().isEmpty()) {
				return new HashMap<>(aggregationResult.getMappedResults().stream()
						.collect(Collectors.toMap(CropBidDetails::getCropId, Function.identity())));
			} else {
				throw new FarmerException("Bids not found for the crops!");
			}
		} catch (Exception e) {
			throw new FarmerException(e.getMessage());
		}
	}

	@Override
	public List<CropBidsDto> getBiddedCropsDetails(String farmerId, String country, String state, String district,
			String village, Set<String> biddedCropIds) {
		try {
			MatchOperation matchOperation1 = Aggregation.match(Criteria.where("farmerId").is(farmerId));

			UnwindOperation unwindOperation = Aggregation.unwind("cropsList");

			MatchOperation matchOperation2 = Aggregation.match(Criteria.where("cropsList.country").is(country)
					.and("cropsList.state").is(state).and("cropsList.district").is(district).and("cropsList.village")
					.is(village).and("cropsList._id").in(biddedCropIds).and("cropsList.status").is("ACTIVE"));

			ProjectionOperation projectionOperation = Aggregation.project().and("cropsList").as("cropDetails")
					.andExclude("_id");

			Aggregation aggregation = Aggregation.newAggregation(matchOperation1, unwindOperation, matchOperation2,
					projectionOperation);

			AggregationResults<CropBidsDto> aggregationResults = mongoTemplate.aggregate(aggregation,
					"farmer-crop-listing", CropBidsDto.class);

			if (!aggregationResults.getMappedResults().isEmpty()) {
				return aggregationResults.getMappedResults();
			} else {
				throw new FarmerException("Crops not found!");
			}
		} catch (Exception e) {
			throw new FarmerException(e.getMessage());
		}
	}

	@Override
	public CropBidDetails getLatestBidForCrop(String farmerId, String cropId) {
		try {
			MatchOperation matchOperation = Aggregation
					.match(Criteria.where("farmerId").is(farmerId).and("cropId").is(cropId));

			AggregationOperation projectionSortBid = context -> new Document("$project",
					new Document("highestBid", new Document("$arrayElemAt", List.of(new Document("$sortArray",
							new Document("input", "$bidDetails").append("sortBy", new Document("bidAmount", -1))), 0)))
							.append("_id", 0));

			Aggregation aggregation = Aggregation.newAggregation(matchOperation, projectionSortBid);

			AggregationResults<CropBidDetails> aggregationResult = mongoTemplate.aggregate(aggregation,
					"crop-bid-details", CropBidDetails.class);

			if (aggregationResult.getUniqueMappedResult() != null) {
				return aggregationResult.getUniqueMappedResult();
			} else {
				throw new FarmerException("Bids not found for the crops!");
			}
		} catch (Exception e) {
			throw new FarmerException(e.getMessage());
		}
	}

	@Override
	public SoldCropBidDetails getAcceptedBidDetailsForCrop(String farmerId, String cropId, String buyerId) {
		try {
			MatchOperation matchOperation1 = Aggregation
					.match(Criteria.where("farmerId").is(farmerId).and("cropId").is(cropId));

			UnwindOperation unwindOperation = Aggregation.unwind("bidDetails");

			MatchOperation matchOperation2 = Aggregation.match(Criteria.where("bidDetails.buyerId").is(buyerId));

			ProjectionOperation projectionOperation = Aggregation.project().and("bidDetails").as("acceptedBid")
					.andInclude("farmerId", "cropId");

			Aggregation aggregation = Aggregation.newAggregation(matchOperation1, unwindOperation, matchOperation2,
					projectionOperation);

			AggregationResults<SoldCropBidDetails> aggregationResult = mongoTemplate.aggregate(aggregation,
					"crop-bid-details", SoldCropBidDetails.class);

			if (aggregationResult.getUniqueMappedResult() != null) {
				return aggregationResult.getUniqueMappedResult();
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new FarmerException(e.getMessage());
		}
	}

}
