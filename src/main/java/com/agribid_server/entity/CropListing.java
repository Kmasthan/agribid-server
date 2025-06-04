package com.agribid_server.entity;

import java.time.LocalDate;
import com.agribid_server.enums.CropStatus;

public class CropListing {

	private String id;
	private String state;
	private String district;
	private String village;
	private String cropName;
	private String weight;
	private String quality;
	private CropStatus status;
	private LocalDate listedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public CropStatus getStatus() {
		return status;
	}

	public void setStatus(CropStatus status) {
		this.status = status;
	}

	public LocalDate getListedAt() {
		return listedAt;
	}

	public void setListedAt(LocalDate listedAt) {
		this.listedAt = listedAt;
	}

}
