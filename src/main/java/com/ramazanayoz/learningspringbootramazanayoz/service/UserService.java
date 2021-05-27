package com.ramazanayoz.learningspringbootramazanayoz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramazanayoz.learningspringbootramazanayoz.dao.UserDao;
import com.ramazanayoz.learningspringbootramazanayoz.model.User;
import com.ramazanayoz.learningspringbootramazanayoz.model.User.Gender;



@Service
public class UserService {
	
	private UserDao userDao;
	
	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public List<User> getAllUsers(Optional<String> gender) {
		List<User> users = userDao.selectAllUsers();
		if(!gender.isPresent()) {
			return userDao.selectAllUsers();
		}
		try {
			Gender theGender = Gender.valueOf(gender.get().toUpperCase());
			return userDao.selectAllUsers().stream()
					.filter(user -> user.getGender().equals(theGender))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new IllegalStateException("Invalid gender", e);
		}
	}

	public Optional<User> getUser(UUID userUid) {
		return userDao.selectUserByUserUid(userUid);
	}

	public int updateUser(User user) {
		Optional<User> optionalUser = getUser(user.getUserUid());
		if(optionalUser.isPresent()) {
			return userDao.updateUser(user);
		}
		return -1;
	}

	public int removeUser(UUID userUid) {
		Optional<User> optionalUser = getUser(userUid);
		if(optionalUser.isPresent()) {
			return userDao.deleteUserByUserUid(userUid);
		}
		return -1;
	}

	public int insertUser(User user) {
		UUID userUuid = UUID.randomUUID();
		return userDao.insertUser(userUuid, User.newUser(userUuid, user));
		
	}

}
