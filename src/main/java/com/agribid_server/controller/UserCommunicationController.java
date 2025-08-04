package com.agribid_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.UserCommunicationDto;
import com.agribid_server.service.UserCommunicationService;

@RestController
@RequestMapping("chat")
public class UserCommunicationController {

	@Autowired
	private UserCommunicationService userCommunicationService;

	@GetMapping("welcome")
	public APISuccessMessage welcomeToQuickConvo() {
		return new APISuccessMessage("Welcome to Quick Convo", HttpStatus.OK.toString());
	}

	@GetMapping("get-conversations-of-users/{userid}")
	public List<UserCommunicationDto> getUserChatsData(@PathVariable("userid") String userId) {
		return userCommunicationService.getUserReceiversData(userId);
	}

	@PostMapping("save-user-communication")
	public APISuccessMessage saveUserCommunication(@RequestBody UserCommunicationDto userCommunication) {
		return userCommunicationService.saveUserCommunication(userCommunication);
	}
	
	@GetMapping("get-all-chats/{senderid}/{receiverid}")
	public List<UserCommunicationDto> getAllChats(@PathVariable("senderid") String senderId,
			@PathVariable("receiverid") String receiverId) {
		return userCommunicationService.getAllChatsData(senderId, receiverId);
	}
	
	@GetMapping("get-user-image/{id}")
	public String getUserImageUrl(@PathVariable("id") String userId) {
		return userCommunicationService.getUserImageUrlWithUserId(userId);
	}
}
