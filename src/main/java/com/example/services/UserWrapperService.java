package com.example.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.example.dto.UserDTO;
import com.example.utils.rest.RestUtils;
import com.google.common.collect.ImmutableMap;

@ApplicationScoped
public class UserWrapperService extends AbstractWrapperService {

	private static final String PATH_ROOT = "userService.path.root";
	private static final String PATH_DELETE_USER_BY_ID = "userService.path.deleteUser";
	private static final String PATH_GET_USER_BY_ID = "userService.path.getUserById";
	private static final String PATH_GET_USERS_BORN_BEFORE = "userService.path.getUsersBornBefore";
	private static final String PATH_GET_USERS = "userService.path.getUsers";
	private static final String PATH_SAVE_USER = "userService.path.saveUser";

	protected static final String PARAM_ID = "userService.param.id";
	protected static final String PARAM_FIRST_NAME = "userService.param.firstName";
	protected static final String PARAM_LAST_NAME = "userService.param.lastName";
	protected static final String PARAM_DATE = "userService.param.date";

	@Inject
	private UserService userService;

	public List<UserDTO> getUsers() throws Exception {
		return getServiceListResponse(() -> this.userService.getUsers(),
				() -> RestUtils.getRestResponse(getUserServiceUrlWithRootPath(PATH_GET_USERS), UserDTO[].class));
	}

	public UserDTO saveUser(UserDTO user) throws Exception {
		return getServiceResponse(() -> this.userService.saveUser(user), () -> RestUtils
				.postRest(getUserServiceUrlWithRootPath(PATH_SAVE_USER), toJsonString(user), UserDTO.class));
	}

	public UserDTO getUserById(long id) throws Exception {
		return getServiceResponse(() -> this.userService.getUserById(id), () -> RestUtils
				.getRestResponse(getUserServiceUrlWithRootPath(PATH_GET_USER_BY_ID) + id, UserDTO.class));
	}

	public List<UserDTO> getUsersByName(String firstName, String lastName) throws Exception {
		return getServiceListResponse(() -> this.userService.getUsersByName(firstName, lastName),
				() -> RestUtils.getRestResponse(getUserServiceUrlWithRootPath(PATH_GET_USERS_BORN_BEFORE),
						ImmutableMap.of(getStringProperty(PARAM_FIRST_NAME), firstName,
								getStringProperty(PARAM_LAST_NAME), lastName),
						UserDTO[].class));
	}

	public List<UserDTO> getUsersBornBefore(LocalDate date) throws Exception {
		return getServiceListResponse(() -> this.userService.getUsersBornBefore(date),
				() -> RestUtils.getRestResponse(getUserServiceUrlWithRootPath(PATH_GET_USERS_BORN_BEFORE),
						ImmutableMap.of(getStringProperty(PARAM_DATE), toJsonString(date)), UserDTO[].class));
	}

	public void deleteUserById(long id) throws Exception {
		callService(() -> {
			this.userService.deleteUserById(id);
			return null;
		}, () -> {
			RestUtils.postRest(getUserServiceUrlWithRootPath(PATH_DELETE_USER_BY_ID) + id, String.class);
			return null;
		});
	}

	private String getUserServiceUrlWithRootPath(String postFixKey) throws IOException {
		return getStringProperty(PATH_ROOT) + getStringProperty(postFixKey);
	}
	
}
