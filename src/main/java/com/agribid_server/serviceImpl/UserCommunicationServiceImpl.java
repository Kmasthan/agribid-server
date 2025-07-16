package com.agribid_server.serviceImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.UserCommunicationDto;
import com.agribid_server.dto.UserDto;
import com.agribid_server.exception.UserCommunicationException;
import com.agribid_server.microHelperService.UserCommunicationMicroHelperService;
import com.agribid_server.repository.UserRepository;
import com.agribid_server.service.UserCommunicationService;

@Service
public class UserCommunicationServiceImpl implements UserCommunicationService {

	@Autowired
	private UserCommunicationMicroHelperService userCommunicationMicroHelperService;

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<UserCommunicationDto> getUserReceiversData(String userId) {
		try {
			Objects.requireNonNull(userId, "User Id is required");
			List<UserCommunicationDto> receiversData = userCommunicationMicroHelperService
					.getUserReceiversDataFromDB(userId);

			if (receiversData != null && !receiversData.isEmpty()) {
				setImageUrlsForReceivers(receiversData);
				return receiversData;
			} else {
				throw new UserCommunicationException("Not found any communications!");
			}
		} catch (Exception e) {
			throw new UserCommunicationException(e.getMessage());
		}
	}

	@Override
	public APISuccessMessage saveUserCommunication(UserCommunicationDto userCommunication) {
		try {
			validateUserCommunication(userCommunication);

			APISuccessMessage responseMsg = userCommunicationMicroHelperService
					.saveUserCommunication(userCommunication);

			if (responseMsg != null) {
				return responseMsg;
			} else {
				throw new UserCommunicationException("Unable to save user communication data!");
			}
		} catch (Exception e) {
			throw new UserCommunicationException(e.getMessage());
		}
	}

	private void validateUserCommunication(UserCommunicationDto userCommunication) {
		Objects.requireNonNull(userCommunication.getSenderId(), "Sender id is required");
		Objects.requireNonNull(userCommunication.getSenderName(), "Sender name is required");
		Objects.requireNonNull(userCommunication.getSenderMobile(), "Sender mobile number is required");
		Objects.requireNonNull(userCommunication.getReceverId(), "Recever id is required");
		Objects.requireNonNull(userCommunication.getReceverName(), "Recever name is required");
		Objects.requireNonNull(userCommunication.getReceverMobile(), "Recever mobile number is required");
		Objects.requireNonNull(userCommunication.getMessage(), "Message is required");
	}

	@Override
	public List<UserCommunicationDto> getAllChatsData(String senderId, String receiverId) {
		try {
			Objects.requireNonNull(senderId, "Sender Id is required");
			Objects.requireNonNull(receiverId, "Receiver Id is required");

			List<UserCommunicationDto> allChats = userCommunicationMicroHelperService.getAllChatsDataFromDB(senderId,
					receiverId);

			if (allChats != null && !allChats.isEmpty()) {
				return allChats;
			} else {
				throw new UserCommunicationException("Not found any chats!");
			}
		} catch (Exception e) {
			throw new UserCommunicationException(e.getMessage());
		}
	}

	private void setImageUrlsForReceivers(List<UserCommunicationDto> receivers) {
		if (receivers != null && !receivers.isEmpty()) {
			List<String> receiversIds = receivers.stream().map(UserCommunicationDto::getId).toList();

			if (receiversIds != null && !receiversIds.isEmpty()) {
				Map<String, UserDto> usersMap;
				usersMap = userRepository.findByIdIn(receiversIds).stream()
						.collect(Collectors.toMap(UserDto::getId, Function.identity()));
				for (UserCommunicationDto receiver : receivers) {
					receiver.setImageUrl(usersMap.get(receiver.getReceverId()).getImageUrl());
				}
			}

		}
	}

}
