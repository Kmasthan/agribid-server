package com.agribid_server.mongoTemplateService;

import com.agribid_server.entity.CropListing;

public interface FarmerMongoTemplateService {

	public String addCropWithUpsert(String farmerId, String farmerName, String farmerPhone, String farmerEmail, CropListing newCrop);
}
