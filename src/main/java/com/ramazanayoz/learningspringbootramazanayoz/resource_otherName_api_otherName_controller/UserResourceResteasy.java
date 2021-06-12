package com.ramazanayoz.learningspringbootramazanayoz.resource_otherName_api_otherName_controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ramazanayoz.learningspringbootramazanayoz.model.User;
import com.ramazanayoz.learningspringbootramazanayoz.service.UserService;

@Validated
@Component
@Path("api/v1/users")
public class UserResourceResteasy {
	
	private UserService userService;
	
	@Autowired
	public UserResourceResteasy(UserService userService) {
		this.userService = userService;
	}
	
	@GET
	@Produces("application/json")
	public List<User> fetchUser(@QueryParam("gender") String gender){
		return userService.getAllUsers(Optional.ofNullable(gender));
	}
	
	@GET
	@Produces("application/json")
	@Path("{userUid}")
	public User fetchUser(@PathParam("userUid") UUID userUid) {
        return userService
                .getUser(userUid)
                .orElseThrow(() -> new NotFoundException("user " + userUid + " not found"));
	}  
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public Response insertNewUser(@Valid @RequestBody User user) {
		int result = userService.insertUser(user);
		return getIntegerResponseEntity(result);
	}
	
	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	public Response updateUser(@RequestBody User user) {
		int result = userService.updateUser(user);
		return getIntegerResponseEntity(result);
	}
	
	@DELETE
	@Produces("application/json")
	@Path("{userUid}")
	public Response deleteUser(@PathParam("userUid") UUID userUid) {
		int result = userService.removeUser(userUid);
		return getIntegerResponseEntity(result);
	}
	
	
	private Response getIntegerResponseEntity(int result) {
		if(result == 1) {
			return Response.ok().build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	

}
