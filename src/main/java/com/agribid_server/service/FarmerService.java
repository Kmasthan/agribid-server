package com.agribid_server.service;

import java.util.List;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.BidDetailsDto;
import com.agribid_server.dto.CropBidsDto;
import com.agribid_server.entity.CropListing;

public interface FarmerService {

	APISuccessMessage addNewCropToListing(String farmerId, String farmerName, String farmerPhone, String farmerEmail,
			int position, CropListing newCrop);

	List<CropListing> getFarmerCropListings(String farmerId);

	APISuccessMessage deleteCropFromListing(String farmerId, String cropId);

	APISuccessMessage updateCropInListing(String farmerId, String cropId, CropListing updatedCrop);

	List<CropBidsDto> getBiddedCrops(String farmerId, String country, String state, String district, String village);

	BidDetailsDto getLatestBidForCrop(String farmerId, String cropId);

	APISuccessMessage acceptBidForCrop(String farmerId, String cropId, String buyerId, CropListing updatedCrop);
}
