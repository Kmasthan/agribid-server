package com.agribid_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.LoginDto;
import com.agribid_server.dto.UserDto;
import com.agribid_server.dto.UserNavItems;
import com.agribid_server.service.UserService;
import com.sun.net.httpserver.Authenticator.Success;


@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("welcome")
	public String welcome() {
		return "Welcome to agri-bid";
	}
	
	@PostMapping("save-farmer")
	public APISuccessMessage saveFarmer(@RequestBody UserDto farmer) {
		return userService.saveUser(farmer);
	}
	
	@PostMapping("save-buyer")
	public APISuccessMessage saveBuyer(@RequestBody UserDto buyer) {
		return userService.saveUser(buyer);
	}
	
	@PostMapping("get-loggedin-user")
	public UserDto getUserForLogInCred(@RequestBody LoginDto login) {
		return userService.getUserWithEmailOrMobileAndPassword(login);
	}
	
	@GetMapping("user-left-nav-items")
	public List<UserNavItems> getFarmerRoleNavItems(@RequestParam("usertype") String userType) {
		return userService.getUserNavItems(userType);
	}
}
