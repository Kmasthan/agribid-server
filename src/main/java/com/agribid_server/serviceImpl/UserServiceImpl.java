package com.agribid_server.serviceImpl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agribid_server.dto.APISuccessMessage;
import com.agribid_server.dto.LoginDto;
import com.agribid_server.dto.UserDto;
import com.agribid_server.dto.UserNavItems;
import com.agribid_server.entity.User;
import com.agribid_server.exception.UserException;
import com.agribid_server.repository.UserRepository;
import com.agribid_server.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	List<UserNavItems> userNavItems = Arrays.asList(
			
			// Farmer Nav Items
			new UserNavItems("Dashboard", "fa-solid fa-users-gear", "Quick access to product listings and prices",
					UserTypeEnum.FARMER.toString(), "dashboard", 1),
			new UserNavItems("Crop Listings", "fa-solid fa-list", "View and mannage your crop listings",
					UserTypeEnum.FARMER.toString(), "crop-listings", 2),
			new UserNavItems("My Crop Bids", "fa-solid fa-chart-simple", "View all bids placed by buyers on your crops",
					UserTypeEnum.FARMER.toString(), "my-crop-bids", 3),
			
			// Buyer Nav Items
			new UserNavItems("Dashboard", "fa-solid fa-users-gear", "Quick access to product listings and prices",
					UserTypeEnum.BUYER.toString(), "dashboard", 1),
			new UserNavItems("Crop Bidding", "fa-solid fa-hand-holding-dollar", "View crops and place your bids.",
					UserTypeEnum.BUYER.toString(), "crop-bidding", 2));
	

	@Autowired
	private UserRepository userRepository;

	@Override
	public APISuccessMessage saveUser(UserDto registeredUser) {
		try {
			if (registeredUser != null) {
				validateUserRegistrationData(registeredUser);
				User newUser = new User();
				BeanUtils.copyProperties(registeredUser, newUser);
				User savedUser = userRepository.save(newUser);
				UserDto savedUserDto = new UserDto();
				BeanUtils.copyProperties(savedUser, savedUserDto);
				savedUserDto.setPassword(null);
				return new APISuccessMessage("Registration succesfull, Welcome to AGRI BID", "success", savedUserDto);
			} else {
				throw new UserException("User data not found!");
			}
		} catch (Exception e) {
			throw new UserException(e.getMessage());
		}
	}

	/**
	 * << validateUserRegistrationData() is to validate the user registration data >>
	 * @param registeredUser
	 */
	private void validateUserRegistrationData(UserDto registeredUser) {
		try {
			Objects.requireNonNull(registeredUser.getName(), "User name is required for registration");
			Objects.requireNonNull(registeredUser.getMobileNumber(), "User mobile number is required for registration");
			
			// check the user already exists
			checkTheUserExistsOrNot(registeredUser);
		} catch (Exception e) {
			throw new UserException(e.getMessage());
		}

	}

	/**
	 * << To check the user already exist or not >>
	 * @param registeredUser
	 */
	private void checkTheUserExistsOrNot(UserDto registeredUser) {
		User existingUser = userRepository.findByMobileNumberOrEmailAndUserType(registeredUser.getMobileNumber().trim(),
				registeredUser.getEmail().trim(), registeredUser.getUserType().trim());

		if (existingUser != null) {
			if (existingUser.getMobileNumber().trim().equals(registeredUser.getMobileNumber().trim()))
				throw new UserException(
						"User already exists with the mobile number " + registeredUser.getMobileNumber());

			if (existingUser.getEmail().trim().equals(registeredUser.getEmail().trim()))
				throw new UserException("User already exists with the email " + registeredUser.getEmail().trim());
		}
	}

	@Override
	public UserDto getUserWithEmailOrMobileAndPassword(LoginDto login) {
		try {
			Objects.requireNonNull(login.getUserName(), "User Email/Phone is required for login");
			Objects.requireNonNull(login.getPassword(), "password is required for login");

			User loggedInUser = userRepository.findByMobileNumberOrEmailAndUserType(login.getUserName().trim(),
					login.getUserName().trim(), login.getUserType().trim());

			if (loggedInUser != null) {
				if (login.getPassword().trim().equals(loggedInUser.getPassword())) {
					UserDto user = new UserDto();
					loggedInUser.setPassword(null);
					BeanUtils.copyProperties(loggedInUser, user);
					return user;
				} else {
					throw new UserException("Invalid password");
				}
			} else {
				throw new UserException("User not found");
			}
		} catch (Exception e) {
			throw new UserException(e.getMessage());
		}
	}

	@Override
	public List<UserNavItems> getUserNavItems(String userType) {
		try {
			if (userNavItems != null && !userNavItems.isEmpty()) {
				List<UserNavItems> navItems = userNavItems.stream()
						.filter(items -> userType.trim().equals(items.getUserType()))
						.sorted(Comparator.comparing(UserNavItems::getPosition)).collect(Collectors.toList());
				if (navItems != null && !navItems.isEmpty()) {
					return navItems;
				} else {
					throw new UserException("Leftnav Items not found for " + userType.toLowerCase());
				}
			} else {
				throw new UserException("Leftnav Items not found");
			}
		} catch (Exception e) {
			throw new UserException(e.getMessage());
		}
	}

}
