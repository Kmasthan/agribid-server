package com.agribid_server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agribid_server.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	User findByMobileNumberOrEmailAndUserType(String userName, String userName2, String userType);


}
