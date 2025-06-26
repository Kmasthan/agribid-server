package com.agribid_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.BidDetailsDto;
import com.agribid_server.dto.CropsBiddingDto;
import com.agribid_server.service.BuyerService;

@RestController
@RequestMapping("buyer")
public class BuyerController {

	@Autowired
	private BuyerService buyerService;

	@GetMapping("bidding-crops")
	public List<CropsBiddingDto> getCropListingForBidding(@RequestParam("country") String country,
			@RequestParam("state") String state, @RequestParam("district") String district,
			@RequestParam("village") String village) {
		return buyerService.getCropListingsForBidding(country, state, district, village);
	}

	@PostMapping("new-bid/{farmerid}/{cropid}")
	public APISuccessMessage placeNewBidForCrop(@PathVariable("farmerid") String farmerId,
			@PathVariable("cropid") String cropId, @RequestBody BidDetailsDto bidDetails) {
		return buyerService.placeNewBidForCrop(farmerId, cropId, bidDetails);
	}

	@GetMapping("crop-bids/{farmerid}/{cropid}")
	public List<BidDetailsDto> getCropBidDetails(@PathVariable("farmerid") String farmerId,
			@PathVariable("cropid") String cropId) {
		return buyerService.getCropBidDetailsList(farmerId, cropId);
	}

	@PutMapping("update-bid/{farmerid}/{cropid}")
	public APISuccessMessage updateCropBidDetails(@PathVariable("farmerid") String farmerId,
			@PathVariable("cropid") String cropId, @RequestBody BidDetailsDto bidDetails) {
		return buyerService.updateCropBidDetails(farmerId, cropId, bidDetails);
	}
}
