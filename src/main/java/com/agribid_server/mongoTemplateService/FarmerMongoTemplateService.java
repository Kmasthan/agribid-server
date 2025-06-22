package com.agribid_server.mongoTemplateService;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.entity.CropListing;

public interface FarmerMongoTemplateService {

	public APISuccessMessage addCropWithUpsert(String farmerId, String farmerName, String farmerPhone, String farmerEmail, CropListing newCrop);

	public APISuccessMessage deleteCropFromListing(String farmerId, String cropId);

	public APISuccessMessage updateCropInListing(String farmerId, String cropId, CropListing updatedCrop);
}
