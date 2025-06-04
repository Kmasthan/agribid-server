package com.agribid_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agribid_server.entity.CropListing;
import com.agribid_server.service.FarmerService;

@RestController
@RequestMapping("farmer")
public class FarmerController {

	@Autowired
	private FarmerService farmerService;

	@PostMapping("new-crop-listing")
	public String addCropToListing(@RequestParam("farmerId") String farmerId,
			@RequestParam("farmerName") String farmerName, @RequestParam("farmerPhone") String farmerPhone,
			@RequestParam("farmerEmail") String farmerEmail, @RequestParam("newCropPosition") int position,
			@RequestBody CropListing newCrop) {
		return farmerService.addNewCropToListing(farmerId, farmerName, farmerPhone, farmerEmail, position, newCrop);
	}
	
	@GetMapping("get-crop-lisitngs")
	public List<CropListing> getFarmerCropListings(@RequestParam("farmerId") String farmerId) {
		return farmerService.getFarmerCropListings(farmerId);
	}

}
