package com.agribid_server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agribid_server.entity.CropBidDetails;

@Repository
public interface CropBidDetailsRepository extends MongoRepository<CropBidDetails, String> {

	CropBidDetails findByFarmerIdAndCropId(String farmerId, String cropId);

}
