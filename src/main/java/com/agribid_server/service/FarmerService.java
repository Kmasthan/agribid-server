package com.agribid_server.service;

import java.util.List;

import com.agribid_server.entity.CropListing;

public interface FarmerService {

	String addNewCropToListing(String farmerId, String farmerName, String farmerPhone, String farmerEmail, int position,
			CropListing newCrop);

	List<CropListing> getFarmerCropListings(String farmerId);
}
