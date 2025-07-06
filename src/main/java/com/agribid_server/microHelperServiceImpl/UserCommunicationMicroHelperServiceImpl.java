package com.agribid_server.microHelperServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.UserCommunicationDto;
import com.agribid_server.exception.UserCommunicationException;
import com.agribid_server.microHelperService.UserCommunicationMicroHelperService;

@Service
public class UserCommunicationMicroHelperServiceImpl implements UserCommunicationMicroHelperService {

	@Value("${service.quick-convo-server.url}")
	private String quickConvoServerUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<UserCommunicationDto> getUserReceiversDataFromDB(String userId) {
		try {
			// Set Url
			String url = UriComponentsBuilder.fromUriString(quickConvoServerUrl)
					.path("get-conversations-of-users/{userid}").buildAndExpand(userId).toUriString();

			// Set Headers
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			// Set Entity
			HttpEntity<String> entity = new HttpEntity<>(headers);

			// Set GET request
			ResponseEntity<UserCommunicationDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity,
					UserCommunicationDto[].class);

			if (HttpStatus.OK.equals(response.getStatusCode())) {
				return Arrays.asList(response.getBody());
			} else {
				throw new UserCommunicationException(
						"Error while getting User Communication Data" + response.getStatusCode());
			}
		} catch (Exception e) {
			throw new UserCommunicationException(e.getMessage());
		}
	}

	@Override
	public APISuccessMessage saveUserCommunication(UserCommunicationDto userCommunication) {
		try {
			// Set Url
			String url = UriComponentsBuilder.fromUriString(quickConvoServerUrl + "save-user-convo").toUriString();

			// Set Headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			// Set Entity
			HttpEntity<UserCommunicationDto> entity = new HttpEntity<>(userCommunication, headers);

			// Set POST request
			ResponseEntity<APISuccessMessage> response = restTemplate.postForEntity(url, entity,
					APISuccessMessage.class);

			if (HttpStatus.OK.equals(response.getStatusCode())) {
				return response.getBody();
			} else {
				throw new UserCommunicationException(
						"Error while saving User Communication Data" + response.getStatusCode());
			}
		} catch (Exception e) {
			throw new UserCommunicationException(e.getMessage());
		}
	}

	@Override
	public List<UserCommunicationDto> getAllChatsDataFromDB(String senderId, String receiverId) {
		try {
			// Set Url
			String url = UriComponentsBuilder.fromUriString(quickConvoServerUrl)
					.path("get-all-chats/{senderid}/{receiverid}").buildAndExpand(senderId, receiverId).toUriString();

			// Set Headers
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			// Set Entity
			HttpEntity<String> entity = new HttpEntity<>(headers);

			// Set GET request
			ResponseEntity<UserCommunicationDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity,
					UserCommunicationDto[].class);

			if (HttpStatus.OK.equals(response.getStatusCode())) {
				return Arrays.asList(response.getBody());
			} else {
				throw new UserCommunicationException(
						"Error while getting User Communication Data" + response.getStatusCode());
			}
		} catch (Exception e) {
			throw new UserCommunicationException(e.getMessage());
		}
	}

}
