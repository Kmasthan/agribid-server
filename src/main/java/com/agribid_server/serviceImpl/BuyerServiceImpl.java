package com.agribid_server.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.BidDetailsDto;
import com.agribid_server.dto.BuyerDashboardBidsDto;
import com.agribid_server.dto.CropsBiddingDto;
import com.agribid_server.entity.CropBidDetails;
import com.agribid_server.entity.CropListing;
import com.agribid_server.exception.BuyerException;
import com.agribid_server.mongoTemplateService.BuyerMongoTemplateService;
import com.agribid_server.repository.CropBidDetailsRepository;
import com.agribid_server.service.BuyerService;

import jakarta.validation.constraints.NotNull;

@Service
public class BuyerServiceImpl implements BuyerService {

	@Autowired
	private BuyerMongoTemplateService buyerMongoTemplateService;

	@Autowired
	private CropBidDetailsRepository cropBidDetailsRepository;

	@Override
	public List<CropsBiddingDto> getCropListingsForBidding(String country, String state, String district,
			String village) {
		try {
			Objects.requireNonNull(country, "Country is null");
			Objects.requireNonNull(state, "State is null");
			Objects.requireNonNull(district, "District is null");
			Objects.requireNonNull(village, "Village is null");

			return buyerMongoTemplateService.getCropListingsForBidding(country, state, district, village);
		} catch (Exception e) {
			throw new BuyerException(e.getMessage());
		}
	}

	@Override
	public APISuccessMessage placeNewBidForCrop(String farmerId, String cropId, BidDetailsDto bidDetails) {
		try {
			Objects.requireNonNull(farmerId, "FarmerId is null");
			Objects.requireNonNull(cropId, "Crop Id is null");
			Objects.requireNonNull(bidDetails, "Bid details is null");

			bidDetails.setCreatedAt(LocalDate.now());
			return buyerMongoTemplateService.saveNewBidFroCrop(farmerId, cropId, bidDetails);
		} catch (Exception e) {
			throw new BuyerException(e.getMessage());
		}
	}

	@Override
	public List<BidDetailsDto> getCropBidDetailsList(String farmerId, String cropId) {
		try {
			Objects.requireNonNull(farmerId, "FarmerId is null");
			Objects.requireNonNull(cropId, "Crop Id is null");
			CropBidDetails cropBidDetails = cropBidDetailsRepository.findByFarmerIdAndCropId(farmerId, cropId);
			if (cropBidDetails != null && cropBidDetails.getBidDetails() != null
					&& !cropBidDetails.getBidDetails().isEmpty()) {
				return cropBidDetails.getBidDetails().stream()
						.sorted(Comparator.comparing(BidDetailsDto::getBidAmount).reversed()).toList();
			} else {
				throw new BuyerException("Bids not found for the crop");
			}
		} catch (Exception e) {
			throw new BuyerException(e.getMessage());
		}
	}

	@Override
	public APISuccessMessage updateCropBidDetails(String farmerId, String cropId, BidDetailsDto bidDetails) {
		try {
			Objects.requireNonNull(farmerId, "FarmerId is null");
			Objects.requireNonNull(cropId, "Crop Id is null");
			Objects.requireNonNull(bidDetails, "Bid details is null");

			CropBidDetails cropBidDetails = cropBidDetailsRepository.findByFarmerIdAndCropId(farmerId, cropId);
			if (cropBidDetails != null && cropBidDetails.getBidDetails() != null
					&& !cropBidDetails.getBidDetails().isEmpty()) {
				BidDetailsDto matchedBid = cropBidDetails.getBidDetails().stream()
						.filter(bid -> bid.getBuyerId().equals(bidDetails.getBuyerId())).findFirst().orElse(null);

				if (matchedBid != null) {
					int index = cropBidDetails.getBidDetails().indexOf(matchedBid);
					if (index > -1) {
						bidDetails.setModifiedAt(LocalDate.now());
						cropBidDetails.getBidDetails().set(index, bidDetails);
					}
					cropBidDetailsRepository.save(cropBidDetails);
					return new APISuccessMessage("Bid updated successfully", "success");
				} else {
					throw new BuyerException("No Bid found to update");
				}
			} else {
				throw new BuyerException("Bids not found for the crop");
			}
		} catch (Exception e) {
			throw new BuyerException(e.getMessage());
		}
	}

	@Override
	public List<BuyerDashboardBidsDto> getCropBidsListForDashboard(
			@NotNull(message = "Buyer id is required") String userId) {
		try {
			List<CropBidDetails> userActiveBidDetails = cropBidDetailsRepository.findByBidDetailsBuyerId(userId);

			if (!userActiveBidDetails.isEmpty()) {
				Set<String> cropIds = userActiveBidDetails.stream().map(CropBidDetails::getCropId)
						.collect(Collectors.toSet());

				Map<String, CropListing> cropsForTheBids = buyerMongoTemplateService.getCropsListByCropIds(cropIds);

				if (cropsForTheBids != null && !cropsForTheBids.isEmpty()) {
					return creatBuyerDashboardBidsList(userActiveBidDetails, cropsForTheBids, userId);
				} else {
					throw new BuyerException("Crops details not found");
				}
			} else {
				throw new BuyerException("Bids not found");
			}
		} catch (Exception e) {
			throw new BuyerException(e.getMessage());
		}
	}

	private List<BuyerDashboardBidsDto> creatBuyerDashboardBidsList(List<CropBidDetails> userActiveBidDetails,
			Map<String, CropListing> cropsForTheBids, String userId) {
		List<BuyerDashboardBidsDto> buyerDashboardBidsList = new ArrayList<>();

		for (CropBidDetails userActiveBid : userActiveBidDetails) {
			BuyerDashboardBidsDto buyerDashboardBid = new BuyerDashboardBidsDto();
			buyerDashboardBid.setCropDetails(cropsForTheBids.get(userActiveBid.getCropId()));

			setBidPositionAndTopAndUserBids(buyerDashboardBid, userActiveBid, userId);

			buyerDashboardBidsList.add(buyerDashboardBid);
		}
		return buyerDashboardBidsList;
	}

	/**
	 * << setBidPositionAndTopAndUserBids() is to set the position, user and top
	 * bid's amount details >>
	 * 
	 * @param buyerDashboardBid
	 * @param userActiveBid
	 * @param userId
	 */
	private void setBidPositionAndTopAndUserBids(BuyerDashboardBidsDto buyerDashboardBid, CropBidDetails userActiveBid,
			String userId) {
		if (userActiveBid.getBidDetails() != null && !userActiveBid.getBidDetails().isEmpty()) {
			userActiveBid.getBidDetails().sort(Comparator.comparing(BidDetailsDto::getBidAmount).reversed());

			buyerDashboardBid.setTopBidAmount(userActiveBid.getBidDetails().get(0).getBidAmount());
			buyerDashboardBid.setTopBidAmountCurrency(userActiveBid.getBidDetails().get(0).getCurrency());

			if (userId.equals(userActiveBid.getBidDetails().get(0).getBuyerId())) {
				buyerDashboardBid.setPosition(1);
				buyerDashboardBid.setUserBidAmount(userActiveBid.getBidDetails().get(0).getBidAmount());
				buyerDashboardBid.setUserBidCurrency(userActiveBid.getBidDetails().get(0).getCurrency());
			} else {
				int currentUserBidPosition = IntStream.range(0, userActiveBid.getBidDetails().size())
						.filter(i -> userId.equals(userActiveBid.getBidDetails().get(i).getBuyerId())).findAny()
						.orElse(-1);
				if (currentUserBidPosition != -1) {
					BidDetailsDto currentUserBid = userActiveBid.getBidDetails().get(currentUserBidPosition);
					if (currentUserBid != null) {
						buyerDashboardBid.setPosition(currentUserBidPosition + 1);
						buyerDashboardBid.setUserBidAmount(currentUserBid.getBidAmount());
						buyerDashboardBid.setUserBidCurrency(currentUserBid.getCurrency());
					}
				}

			}
		}
	}

}
