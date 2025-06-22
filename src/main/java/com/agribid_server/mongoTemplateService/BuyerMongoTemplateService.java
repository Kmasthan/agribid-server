package com.agribid_server.mongoTemplateService;

import java.util.List;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.BidDetailsDto;
import com.agribid_server.dto.CropsBiddingDto;

public interface BuyerMongoTemplateService {

	List<CropsBiddingDto> getCropListingsForBidding(String country, String state, String district, String village);

	APISuccessMessage saveNewBidFroCrop(String farmerId, String cropId,
			BidDetailsDto bidDetails);

}
