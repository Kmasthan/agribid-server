package com.agribid_server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agribid_server.entity.SoldCropBidDetails;

@Repository
public interface SoldCropBidDetailsRepository extends MongoRepository<SoldCropBidDetails, String> {

}
