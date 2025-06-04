package com.agribid_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.agribid_server.entity.CropListing;
import com.agribid_server.entity.FarmerCropListingDetails;
import com.agribid_server.exception.FarmerException;
import com.agribid_server.mongoTemplateService.FarmerMongoTemplateService;

@Service
public class FarmerMongoTemplateServiceImpl implements FarmerMongoTemplateService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public String addCropWithUpsert(String farmerId, String farmerName, String farmerPhone, String farmerEmail,
			CropListing newCrop) {
		try {

			// Performing MATCH operation using the FarmerId
			Query matchQuery = new Query(Criteria.where("farmerId").is(farmerId));

			// Performing UPDATE operation using the Farmer details and New Crop
			Update updateQuery = new Update().setOnInsert("farmerId", farmerId).setOnInsert("farmerName", farmerName)
					.setOnInsert("farmerPhone", farmerPhone).setOnInsert("farmerEmail", farmerEmail)
					.push("cropsList", newCrop);

			// Performs an UPSERT If no document is found that matches the query, a new
			// document is created and inserted by combining the query document and the update document.
			mongoTemplate.upsert(matchQuery, updateQuery, FarmerCropListingDetails.class);

			return "New crop listed succesfully";
		} catch (Exception e) {
			throw new FarmerException(e.getMessage());
		}
	}

}
