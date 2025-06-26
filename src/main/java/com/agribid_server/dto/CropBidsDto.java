package com.agribid_server.dto;

import com.agribid_server.entity.CropListing;

public class CropBidsDto {

	private CropListing cropDetails;
	private BidDetailsDto bidDetails;

	public CropListing getCropDetails() {
		return cropDetails;
	}

	public void setCropDetails(CropListing cropDetails) {
		this.cropDetails = cropDetails;
	}

	public BidDetailsDto getBidDetails() {
		return bidDetails;
	}

	public void setBidDetails(BidDetailsDto bidDetails) {
		this.bidDetails = bidDetails;
	}
}
