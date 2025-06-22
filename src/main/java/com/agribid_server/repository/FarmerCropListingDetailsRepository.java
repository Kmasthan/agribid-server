package com.agribid_server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.agribid_server.entity.FarmerCropListingDetails;

public interface FarmerCropListingDetailsRepository extends MongoRepository<FarmerCropListingDetails, String> {

	FarmerCropListingDetails findByFarmerId(String farmerId);

}
