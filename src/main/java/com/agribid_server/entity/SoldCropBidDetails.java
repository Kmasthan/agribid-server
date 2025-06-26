package com.agribid_server.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sold-crop-bid-details")
public class SoldCropBidDetails extends CropBidDetails {

	// this class has the same properties of CropBidDetails
}
