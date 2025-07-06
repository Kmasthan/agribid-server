package com.agribid_server.service;

import java.util.List;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.UserCommunicationDto;

public interface UserCommunicationService {

	List<UserCommunicationDto> getUserReceiversData(String userId);

	APISuccessMessage saveUserCommunication(UserCommunicationDto userCommunication);

	List<UserCommunicationDto> getAllChatsData(String senderId, String receiverId);

}
