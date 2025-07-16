package com.agribid_server.mongoTemplateService;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.BidDetailsDto;
import com.agribid_server.dto.CropsBiddingDto;
import com.agribid_server.entity.CropListing;

public interface BuyerMongoTemplateService {

	List<CropsBiddingDto> getCropListingsForBidding(String country, String state, String district, String village);

	APISuccessMessage saveNewBidFroCrop(String farmerId, String cropId,
			BidDetailsDto bidDetails);

	Map<String, CropListing> getCropsListByCropIds(Set<String> cropIds);

}
