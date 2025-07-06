package com.agribid_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.LoginDto;
import com.agribid_server.dto.UserDto;
import com.agribid_server.dto.UserNavItems;
import com.agribid_server.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("welcome")
	public String welcome() {
		return "Welcome to agri-bid";
	}

	@PostMapping(value = "save-farmer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public APISuccessMessage saveFarmer(@RequestPart("farmer") UserDto farmer,
			@RequestPart("imageFile") MultipartFile file) {
		return userService.saveUser(farmer, file);
	}

	@PostMapping(value = "save-buyer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public APISuccessMessage saveBuyer(@RequestPart("buyer") UserDto buyer,
			@RequestPart("imageFile") MultipartFile file) {
		return userService.saveUser(buyer, file);
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
