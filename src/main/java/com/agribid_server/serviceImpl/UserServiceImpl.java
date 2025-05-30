package com.agribid_server.serviceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agribid_server.dto.UserDto;
import com.agribid_server.entity.User;
import com.agribid_server.exception.UserException;
import com.agribid_server.repository.UserRepository;
import com.agribid_server.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public String saveUser(UserDto registeredUser) {
		try {
			if(registeredUser != null) {
				User newUser = new User();
				BeanUtils.copyProperties(registeredUser, newUser);
				userRepository.save(newUser);
				if(UserTypeEnum.FARMER.toString().equals(registeredUser.getUserType())) {
					return "Farmer registered succesfully";
				} else {
					return "Farmer registered failed!";
				}
			} else {
				throw new UserException("User data not found!");
			}
		} catch (Exception e) {
			throw new UserException(e.getMessage());
		}
	}

}
