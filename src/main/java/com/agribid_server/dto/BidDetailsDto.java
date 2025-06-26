package com.agribid_server.dto;

import java.time.LocalDate;

public class BidDetailsDto {

	private String buyerId;
	private String buyerName;
	private String buyerEmail;
	private String buyerPhone;
	private double bidAmount;
	private String measure;
	private boolean biddingForTotalQuantity;
	private double biddingQuantity;
	private String currency;
	private LocalDate createdAt;
	private LocalDate modifiedAt;

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	public double getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(double bidAmount) {
		this.bidAmount = bidAmount;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public boolean isBiddingForTotalQuantity() {
		return biddingForTotalQuantity;
	}

	public void setBiddingForTotalQuantity(boolean biddingForTotalQuantity) {
		this.biddingForTotalQuantity = biddingForTotalQuantity;
	}

	public double getBiddingQuantity() {
		return biddingQuantity;
	}

	public void setBiddingQuantity(double biddingQuantity) {
		this.biddingQuantity = biddingQuantity;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDate modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

}
