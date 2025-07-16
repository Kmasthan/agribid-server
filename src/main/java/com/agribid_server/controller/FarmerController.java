package com.agribid_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.agribid_server.dto.CropBidsDto;
import com.agribid_server.entity.CropListing;
import com.agribid_server.service.FarmerService;

@RestController
@RequestMapping("farmer")
public class FarmerController {

	@Autowired
	private FarmerService farmerService;

	@PostMapping("new-crop-listing")
	public APISuccessMessage addCropToListing(@RequestParam("farmerId") String farmerId,
			@RequestParam("farmerName") String farmerName, @RequestParam("farmerPhone") String farmerPhone,
			@RequestParam("farmerEmail") String farmerEmail, @RequestParam("newCropPosition") int position,
			@RequestBody CropListing newCrop) {
		return farmerService.addNewCropToListing(farmerId, farmerName, farmerPhone, farmerEmail, position, newCrop);
	}

	@GetMapping("get-crop-lisitngs")
	public List<CropListing> getFarmerCropListings(@RequestParam("farmerId") String farmerId) {
		return farmerService.getFarmerCropListings(farmerId);
	}

	@DeleteMapping("delete-crop/{farmerid}/{cropid}")
	public APISuccessMessage deleteCropFromListing(@PathVariable("farmerid") String farmerId,
			@PathVariable("cropid") String cropId) {
		return farmerService.deleteCropFromListing(farmerId, cropId);
	}

	@PutMapping("update-crop/{farmerid}/{cropid}")
	public APISuccessMessage updateCropInListing(@PathVariable("farmerid") String farmerId,
			@PathVariable("cropid") String cropId, @RequestBody CropListing updatedCrop) {
		return farmerService.updateCropInListing(farmerId, cropId, updatedCrop);
	}

	@GetMapping("get-bidded-crops/{farmerid}")
	public List<CropBidsDto> getBiddedCropsList(@PathVariable("farmerid") String farmerId,
			@RequestParam("country") String country, @RequestParam("state") String state,
			@RequestParam("district") String district, @RequestParam("village") String village) {
		return farmerService.getBiddedCrops(farmerId, country, state, district, village);
	}

	@GetMapping("get-latest-bid-for-crop/{farmerid}/{cropid}")
	public BidDetailsDto getLatestBidForCrop(@PathVariable("farmerid") String farmerId,
			@PathVariable("cropid") String cropId) {
		return farmerService.getLatestBidForCrop(farmerId, cropId);
	}

	@PutMapping("accept-bid/{farmerid}/{cropid}/{buyerid}")
	public APISuccessMessage acceptBidForCrop(@PathVariable("farmerid") String farmerId,
			@PathVariable("cropid") String cropId, @PathVariable("buyerid") String buyerId,
			@RequestBody CropListing updatedCrop) {
		return farmerService.acceptBidForCrop(farmerId, cropId, buyerId, updatedCrop);
	}

	@GetMapping("get-bidded-crops-dashboard/{farmerid}")
	public List<CropBidsDto> getBiddedCropsListForDashBoard(@PathVariable("farmerid") String farmerId) {
		return farmerService.getCropsBidDetailsForDashboard(farmerId);
	}

}
