package com.agribid_server.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import com.agribid_server.dto.BidDetailsDto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;

@Document(collection = "crop-bid-details")
public class CropBidDetails {

	@Id
	private String id;
	private String farmerId;
	private String cropId;
	private List<BidDetailsDto> bidDetails;
	private LocalDate createdAt;

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

	public String getCropId() {
		return cropId;
	}

	public List<BidDetailsDto> getBidDetails() {
		return bidDetails;
	}

	public void setBidDetails(List<BidDetailsDto> bidDetails) {
		this.bidDetails = bidDetails;
	}

	public void setCropId(String cropId) {
		this.cropId = cropId;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
}
