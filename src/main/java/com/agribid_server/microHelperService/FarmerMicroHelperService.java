package com.agribid_server.microHelperService;

import com.agribid_server.dto.APISuccessMessage;

public interface FarmerMicroHelperService {

	APISuccessMessage deleteCropBidDetailsWithId(String id);

}
