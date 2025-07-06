package com.agribid_server.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "farmer-crop-listing")
public class FarmerCropListingDetails {
	
	@Id
	private String id;
	
	@Indexed
	private String farmerId;
	private String farmerName;
	private String farmerPhone;
	private String farmerEmail;
	private List<CropListing> cropsList = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getFarmerPhone() {
		return farmerPhone;
	}

	public void setFarmerPhone(String farmerPhone) {
		this.farmerPhone = farmerPhone;
	}

	public String getFarmerEmail() {
		return farmerEmail;
	}

	public void setFarmerEmail(String farmerEmail) {
		this.farmerEmail = farmerEmail;
	}

	public List<CropListing> getCropsList() {
		return cropsList;
	}

	public void setCropsList(List<CropListing> cropsList) {
		this.cropsList = cropsList;
	}

}
