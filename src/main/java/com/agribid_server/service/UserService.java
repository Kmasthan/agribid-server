package com.agribid_server.service;

import org.springframework.stereotype.Service;

import com.agribid_server.dto.UserDto;

public interface UserService {

	public String saveUser(UserDto registeredUser);
}
