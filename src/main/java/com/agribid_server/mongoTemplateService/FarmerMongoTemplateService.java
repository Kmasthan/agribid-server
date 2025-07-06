package com.agribid_server.mongoTemplateService;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.CropBidsDto;
import com.agribid_server.entity.CropBidDetails;
import com.agribid_server.entity.CropListing;
import com.agribid_server.entity.SoldCropBidDetails;

public interface FarmerMongoTemplateService {

	public APISuccessMessage addCropWithUpsert(String farmerId, String farmerName, String farmerPhone,
			String farmerEmail, CropListing newCrop);

	public APISuccessMessage deleteCropFromListing(String farmerId, String cropId);

	public APISuccessMessage updateCropInListing(String farmerId, String cropId, CropListing updatedCrop);

	public HashMap<String, CropBidDetails> getCropBidsDetailsWithHighestBid(String farmerId);

	public List<CropBidsDto> getBiddedCropsDetails(String farmerId, String country, String state, String district,
			String village, Set<String> biddedCropIds);

	public CropBidDetails getLatestBidForCrop(String farmerId, String cropId);

	public SoldCropBidDetails getAcceptedBidDetailsForCrop(String farmerId, String cropId, String buyerId);
}
