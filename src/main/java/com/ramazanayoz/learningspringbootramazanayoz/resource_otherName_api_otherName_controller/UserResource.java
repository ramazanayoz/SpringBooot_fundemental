package com.ramazanayoz.learningspringbootramazanayoz.resource_otherName_api_otherName_controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ramazanayoz.learningspringbootramazanayoz.model.User;
import com.ramazanayoz.learningspringbootramazanayoz.service.UserService;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserResource {

	private UserService userService;
	
	@Autowired
	public UserResource(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<User> fetUsers(){
		return userService.getAllUsers();
	}
}
