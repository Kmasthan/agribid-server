package com.agribid_server.service;

import com.agribid_server.dto.APISuccessMessage;

public interface FarmerMicroService {

	APISuccessMessage deleCropBidDetails(String id);

}
