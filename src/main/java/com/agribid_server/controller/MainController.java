package com.agribid_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agribid_server.dto.UserDto;
import com.agribid_server.service.UserService;

@RestController
@RequestMapping("agri-bid")
public class MainController {
	
	@Autowired
	private UserService userService;

	@GetMapping("welcome")
	public String welcome() {
		return "Welcome to spring boot";
	}
	
	@PostMapping("save-farmer")
	public String saveFarmer(@RequestBody UserDto farmer) {
		return userService.saveUser(farmer);
	}
}
