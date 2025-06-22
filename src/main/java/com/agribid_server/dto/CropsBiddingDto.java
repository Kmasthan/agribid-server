package com.agribid_server.dto;

import com.agribid_server.entity.CropListing;

public class CropsBiddingDto {
	private String farmerId;
	private String farmerName;
	private String farmerEmail;
	private String farmerPhone;
	private CropListing cropData;

	public String getFarmerId() {
		return farmerId;
	}

	public void setFarmerId(String farmerId) {
		this.farmerId = farmerId;
	}

	public String getFarmerName() {
		return farmerName;
	}

	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}

	public String getFarmerEmail() {
		return farmerEmail;
	}

	public void setFarmerEmail(String farmerEmail) {
		this.farmerEmail = farmerEmail;
	}

	public String getFarmerPhone() {
		return farmerPhone;
	}

	public void setFarmerPhone(String farmerPhone) {
		this.farmerPhone = farmerPhone;
	}

	public CropListing getCropData() {
		return cropData;
	}

	public void setCropData(CropListing cropData) {
		this.cropData = cropData;
	}
}