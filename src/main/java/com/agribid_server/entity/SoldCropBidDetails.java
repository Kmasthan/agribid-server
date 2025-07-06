package com.agribid_server.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.agribid_server.dto.BidDetailsDto;

@Document(collection = "sold-crop-bid-details")
public class SoldCropBidDetails extends CropBidDetails {

	private BidDetailsDto acceptedBid;

	public BidDetailsDto getAcceptedBid() {
		return acceptedBid;
	}

	public void setAcceptedBid(BidDetailsDto acceptedBid) {
		this.acceptedBid = acceptedBid;
	}
}
