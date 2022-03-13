package com.example.controllers;

import java.time.ZoneId;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.dto.UserDTO;
import com.example.services.UserService;

@Path(UserController.ROOT_PATH)
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

	protected static final String ROOT_PATH = "user-controller"; // NOSONAR

	protected static final String PARAM_ID = "id";
	protected static final String PARAM_FIRST_NAME = "firstName";
	protected static final String PARAM_LAST_NAME = "lastName";
	protected static final String PARAM_DATE = "date";

	@Inject
	private UserService userService;

	@GET
	@Path("users")
	public Response getUsers() {
		return Response.ok(this.userService.getUsers()).build();
	}

	@GET
	@Path("user/{id}")
	public Response getUser(@PathParam(PARAM_ID) long id) {
		return Response.ok(this.userService.getUserById(id)).build();
	}

	@GET
	@Path("user-by-name")
	public Response getUserByName(@PathParam(PARAM_FIRST_NAME) String firstName,
			@PathParam(PARAM_LAST_NAME) String lastName) {
		return Response.ok(this.userService.getUsersByName(firstName, lastName)).build();
	}

	@GET
	@Path("user-born-before")
	public Response getUsersBornBefore(@PathParam(PARAM_DATE) Date date) {
		return Response
				.ok(this.userService.getUsersBornBefore(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))
				.build();
	}

	@POST
	@Path("save-user")
	public Response saveUser(UserDTO to) {
		return Response.ok(this.userService.saveUser(to)).build();
	}

}
