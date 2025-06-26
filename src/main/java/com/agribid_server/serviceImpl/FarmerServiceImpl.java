package com.agribid_server.serviceImpl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.BidDetailsDto;
import com.agribid_server.dto.CropBidsDto;
import com.agribid_server.entity.CropBidDetails;
import com.agribid_server.entity.CropListing;
import com.agribid_server.entity.FarmerCropListingDetails;
import com.agribid_server.exception.FarmerException;
import com.agribid_server.mongoTemplateService.FarmerMongoTemplateService;
import com.agribid_server.repository.CropBidDetailsRepository;
import com.agribid_server.repository.FarmerCropListingDetailsRepository;
import com.agribid_server.service.FarmerService;

@Service
public class FarmerServiceImpl implements FarmerService {

	@Autowired
	private FarmerCropListingDetailsRepository farmerCropListingDetailsRepository;

	@Autowired
	private FarmerMongoTemplateService farmerMongoTemplateService;

	@Autowired
	private CropBidDetailsRepository cropBidDetailsRepository;

	@Override
	public APISuccessMessage addNewCropToListing(String farmerId, String farmerName, String farmerPhone,
			String farmerEmail, int position, CropListing newCrop) {
		try {
			Objects.requireNonNull(farmerId, "Farmer id is required!");
			Objects.requireNonNull(farmerName, "Farmer name is required!");
			Objects.requireNonNull(farmerPhone, "Farmer Phone is required!");
			Objects.requireNonNull(newCrop, "New crop not found!");

			newCrop.setId(farmerId + "-" + position);
			newCrop.setListedAt(LocalDate.now());
			return farmerMongoTemplateService.addCropWithUpsert(farmerId, farmerName, farmerPhone, farmerEmail,
					newCrop);
		} catch (Exception e) {
			throw new FarmerException(e.getMessage());
		}
	}

	@Override
	public List<CropListing> getFarmerCropListings(String farmerId) {
		try {
			Objects.requireNonNull(farmerId, "Farmer id is required!");
			FarmerCropListingDetails farmerCropListingDetails = farmerCropListingDetailsRepository
					.findByFarmerId(farmerId);

			if (farmerCropListingDetails != null && farmerCropListingDetails.getCropsList() != null
					&& !farmerCropListingDetails.getCropsList().isEmpty()) {
				return farmerCropListingDetails.getCropsList().stream()
						.sorted(Comparator.comparing(CropListing::getState)).collect(Collectors.toList());
			} else {
				throw new FarmerException("Crops list is empty");
			}
		} catch (Exception e) {
			throw new FarmerException(e.getMessage());
		}
	}

	@Override
	public APISuccessMessage deleteCropFromListing(String farmerId, String cropId) {
		try {
			Objects.requireNonNull(farmerId, "Farmer Id is Null");
			Objects.requireNonNull(cropId, "Crop Id is Null");
			return farmerMongoTemplateService.deleteCropFromListing(farmerId, cropId);
		} catch (Exception e) {
			throw new FarmerException(e.getMessage());
		}
	}

	@Override
	public APISuccessMessage updateCropInListing(String farmerId, String cropId, CropListing updatedCrop) {
		try {
			Objects.requireNonNull(farmerId, "Farmer Id is Null");
			Objects.requireNonNull(cropId, "Crop Id is Null");
			return farmerMongoTemplateService.updateCropInListing(farmerId, cropId, updatedCrop);
		} catch (Exception e) {
			throw new FarmerException(e.getMessage());
		}
	}

	@Override
	public List<CropBidsDto> getBiddedCrops(String farmerId, String country, String state, String district,
			String village) {
		try {
			Objects.requireNonNull(farmerId, "Farmer Id is Null");

			// to get the crops bids details
			HashMap<String, CropBidDetails> cropsBidsDetails = farmerMongoTemplateService
					.getCropBidsDetailsWithHighestBid(farmerId);
			List<CropBidsDto> biddedCropsDetails;

			if (cropsBidsDetails != null && !cropsBidsDetails.isEmpty()) {
				Set<String> biddedCropIds = cropsBidsDetails.keySet();

				// to get the bidded crops details
				biddedCropsDetails = farmerMongoTemplateService.getBiddedCropsDetails(farmerId, country, state,
						district, village, biddedCropIds);

				if (biddedCropsDetails != null && !biddedCropsDetails.isEmpty()) {
					for (CropBidsDto biddedCrop : biddedCropsDetails) {
						// setting the bid details for the crop details
						biddedCrop.setBidDetails(
								cropsBidsDetails.get(biddedCrop.getCropDetails().getId()).getHighestBid());
					}
					// clearing the cropsBidsDetails
					cropsBidsDetails.clear();

					return biddedCropsDetails;
				} else {
					throw new FarmerException("Bidded crops not found");
				}
			} else {
				throw new FarmerException("No bids for your crops");
			}
		} catch (Exception e) {
			throw new FarmerException(e.getMessage());
		}
	}

	@Override
	public BidDetailsDto getLatestBidForCrop(String farmerId, String cropId) {
		try {
			Objects.requireNonNull(farmerId, "Farmer Id is Null");
			Objects.requireNonNull(cropId, "Crop Id is Null");
			// to get the latest bid details
			CropBidDetails cropsBidsDetails = farmerMongoTemplateService.getLatestBidForCrop(farmerId, cropId);
			if (cropsBidsDetails != null && cropsBidsDetails.getHighestBid() != null) {
				return cropsBidsDetails.getHighestBid();
			} else {
				throw new FarmerException("Bid not found!");
			}
		} catch (Exception e) {
			throw new FarmerException(e.getMessage());
		}
	}

}
