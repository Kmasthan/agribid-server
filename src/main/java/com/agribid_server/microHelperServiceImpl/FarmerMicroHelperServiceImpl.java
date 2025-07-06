package com.agribid_server.microHelperServiceImpl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.exception.FarmerException;
import com.agribid_server.microHelperService.FarmerMicroHelperService;

@Service
public class FarmerMicroHelperServiceImpl implements FarmerMicroHelperService {

	@Value("${service.agri-bid-server.url}")
	private String agriBidServerUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public APISuccessMessage deleteCropBidDetailsWithId(String id) {
		try {
			// Set Url
			String url = UriComponentsBuilder.fromUriString(agriBidServerUrl + "farmer-service/delete-crop-bid-details/" + id)
					.toUriString();

			// Set Headers
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			// Set Entity
			HttpEntity<String> entity = new HttpEntity<>(headers);

			// Set DELETE request
			ResponseEntity<APISuccessMessage> response = restTemplate.exchange(url, HttpMethod.DELETE, entity,
					APISuccessMessage.class);
			return response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FarmerException(e.getMessage());
		}
	}
}
