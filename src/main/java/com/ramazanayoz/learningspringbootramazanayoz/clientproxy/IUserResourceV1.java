package com.ramazanayoz.learningspringbootramazanayoz.clientproxy;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.ramazanayoz.learningspringbootramazanayoz.model.User;

public interface IUserResourceV1 {
	
	@GET
	@Produces("application/json")
	List<User> fetchUser(@QueryParam("gender") String gender);
	
	@GET
	@Produces("application/json")
	@Path("{userUid}")
	User fetchUser(@PathParam("userUid") UUID userUid);
	
	@POST 
	@Produces("application/json")
	@Consumes("application/json")
	void insertNewUser(@RequestBody User user);
	
	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	void updateUser(@RequestBody User user);
	
	@DELETE
	@Produces("application/json")
	@Path("{userUid}")
	void deleteUser(@PathParam("userUid") UUID userUid);

}
