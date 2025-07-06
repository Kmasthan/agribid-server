package com.agribid_server.serviceImpl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.exception.FarmerException;
import com.agribid_server.repository.CropBidDetailsRepository;
import com.agribid_server.service.FarmerMicroService;

@Service
public class FarmerMicroServiceImpl implements FarmerMicroService {

	@Autowired
	private CropBidDetailsRepository cropBidDetailsRepository;

	@Override
	public APISuccessMessage deleCropBidDetails(String id) {
		try {
			Objects.requireNonNull(id, "Id is required");
			cropBidDetailsRepository.deleteById(id);
			return new APISuccessMessage("Crop bid details deleted successfully", "SUCCESS");
		} catch (Exception e) {
			throw new FarmerException(e.getMessage());
		}
	}

}
