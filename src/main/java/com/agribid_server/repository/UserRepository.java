package com.agribid_server.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.agribid_server.dto.UserDto;
import com.agribid_server.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	@Query("{ '$or' : [{'mobileNumber' : ?0}, {'email' : ?1}], 'userType' : ?2 }")
	User findByMobileNumberOrEmailAndUserType(String mobileNumber, String email, String userType);

	List<UserDto> findByIdIn(List<String> receiversIds);

}
