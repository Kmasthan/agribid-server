package com.agribid_server.entity;

import java.time.LocalDate;
import com.agribid_server.enums.CropStatus;

public class CropListing {

	private String id;
	private String country;
	private String state;
	private String district;
	private String village;
	private String cropName;
	private double weight;
	private String measure;
	private String quality;
	private CropStatus status;
	private LocalDate listedAt;
	private LocalDate modifiedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
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

	public LocalDate getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDate modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

}
