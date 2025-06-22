package com.agribid_server.mongoTemplateServiceImpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.entity.CropListing;
import com.agribid_server.entity.FarmerCropListingDetails;
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
			
			if(result.getModifiedCount() == 0) {
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
			Update update = new Update().set("cropsList.$.country", updatedCrop.getCountry()).set("cropsList.$.state", updatedCrop.getState())
					.set("cropsList.$.district", updatedCrop.getDistrict()).set("cropsList.$.village", updatedCrop.getVillage())
					.set("cropsList.$.cropName", updatedCrop.getCropName()).set("cropsList.$.weight", updatedCrop.getWeight())
					.set("cropsList.$.measure", updatedCrop.getMeasure()).set("cropsList.$.quality", updatedCrop.getQuality())
					.set("cropsList.$.listedAt", updatedCrop.getListedAt()).set("cropsList.$.modifiedAt", LocalDate.now());

			// Performs the UPDATEFIRST
			mongoTemplate.updateFirst(matchQuery, update, FarmerCropListingDetails.class);
			
			return new APISuccessMessage("Crop updated succesfully", "success");
		} catch (Exception e) {
			throw new FarmerException(e.getMessage());
		}
	}

}
