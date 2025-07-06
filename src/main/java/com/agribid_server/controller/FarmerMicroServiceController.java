package com.agribid_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.service.FarmerMicroService;

@RestController
@RequestMapping("farmer-service")
public class FarmerMicroServiceController {
	
	@Autowired
	private FarmerMicroService farmerMicroService;

	@DeleteMapping("delete-crop-bid-details/{id}")
	public APISuccessMessage deleteCropBidDetails(@PathVariable("id") String id) {
		return farmerMicroService.deleCropBidDetails(id);
	}
}
