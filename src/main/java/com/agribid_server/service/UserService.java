package com.agribid_server.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.LoginDto;
import com.agribid_server.dto.UserDto;
import com.agribid_server.dto.UserNavItems;

public interface UserService {

	public APISuccessMessage saveUser(UserDto registeredUser, MultipartFile file);

	public UserDto getUserWithEmailOrMobileAndPassword(LoginDto login);

	public List<UserNavItems> getUserNavItems(String userType);
}
