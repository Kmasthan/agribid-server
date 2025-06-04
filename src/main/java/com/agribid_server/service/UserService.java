package com.agribid_server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agribid_server.dto.LoginDto;
import com.agribid_server.dto.UserDto;
import com.agribid_server.dto.UserNavItems;

public interface UserService {

	public String saveUser(UserDto registeredUser);

	public UserDto getUserWithEmailOrMobileAndPassword(LoginDto login);

	public List<UserNavItems> getUserNavItems(String userType);
}
