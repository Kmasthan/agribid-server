package com.agribid_server.service;

import java.util.List;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.BidDetailsDto;
import com.agribid_server.dto.CropsBiddingDto;

public interface BuyerService {

	List<CropsBiddingDto> getCropListingsForBidding(String country, String state, String district, String village);

	APISuccessMessage placeNewBidForCrop(String farmerId, String cropId, BidDetailsDto bidDetails);

	List<BidDetailsDto> getCropBidDetailsList(String farmerId, String cropId);

}
