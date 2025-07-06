package com.agribid_server.microHelperService;

import java.util.List;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.UserCommunicationDto;

public interface UserCommunicationMicroHelperService {

	List<UserCommunicationDto> getUserReceiversDataFromDB(String userId);

	APISuccessMessage saveUserCommunication(UserCommunicationDto userCommunication);

	List<UserCommunicationDto> getAllChatsDataFromDB(String senderId, String receiverId);

}
