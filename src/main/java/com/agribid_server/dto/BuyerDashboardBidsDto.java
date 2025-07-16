package com.agribid_server.dto;

import com.agribid_server.entity.CropListing;

public class BuyerDashboardBidsDto {

	private CropListing cropDetails;
	private int position;
	private double topBidAmount;
	private String topBidAmountCurrency;
	private double userBidAmount;
	private String userBidCurrency;

	public CropListing getCropDetails() {
		return cropDetails;
	}

	public void setCropDetails(CropListing cropDetails) {
		this.cropDetails = cropDetails;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public double getTopBidAmount() {
		return topBidAmount;
	}

	public void setTopBidAmount(double topBidAmount) {
		this.topBidAmount = topBidAmount;
	}

	public String getTopBidAmountCurrency() {
		return topBidAmountCurrency;
	}

	public void setTopBidAmountCurrency(String topBidAmountCurrency) {
		this.topBidAmountCurrency = topBidAmountCurrency;
	}

	public double getUserBidAmount() {
		return userBidAmount;
	}

	public void setUserBidAmount(double userBidAmount) {
		this.userBidAmount = userBidAmount;
	}

	public String getUserBidCurrency() {
		return userBidCurrency;
	}

	public void setUserBidCurrency(String userBidCurrency) {
		this.userBidCurrency = userBidCurrency;
	}
}
