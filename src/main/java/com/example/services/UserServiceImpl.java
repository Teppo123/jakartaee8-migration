package com.example.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.controllers.UserController;
import com.example.dao.UserDAO;
import com.example.dao.entities.transformers.UserTransformer;
import com.example.dto.UserDTO;
import com.example.dto.transformers.UserDTOTransformer;
import com.google.common.collect.Lists;

@ApplicationScoped
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	private final static UserDTO MOCK_USER_1 = UserDTO.builder().firstName("etunimi1").lastName("sukunimi1")
			.birthDate(LocalDate.now()).creationTime(LocalDateTime.now().minusDays(1)).build();

	private final static UserDTO MOCK_USER_2 = UserDTO.builder().firstName("etunimi2").lastName("sukunimi2")
			.birthDate(LocalDate.now()).creationTime(LocalDateTime.now().minusDays(2)).build();

	@Inject
	private UserDAO userDao;

	@Override
	public List<UserDTO> getUsers() {
		LOGGER.info("entering getUsers");
		return this.userDao.findUsers().stream().map(new UserDTOTransformer()::transform).collect(Collectors.toList());
	}

	@Override
	public UserDTO saveUser(UserDTO user) {
		LOGGER.info("entering saveUser");
		return Optional.ofNullable(this.userDao.saveUser(new UserTransformer().transform(user)))
				.map(new UserDTOTransformer()::transform).orElse(null);
	}

	@Override
	public UserDTO getUserById(long id) {
		LOGGER.info("getUserById, id = {}", id);
		return Optional.ofNullable(this.userDao.findUserById(id)).map(new UserDTOTransformer()::transform).orElse(null);
	}

	@Override
	public List<UserDTO> getUsersByName(String firstName, String lastName) {
		LOGGER.info(String.format("entered /user-by-name/ with firstName = \"%s\" and lastName = \"%s\"", firstName,
				lastName));
		return this.userDao.findUsersByName(firstName, lastName).stream().map(new UserDTOTransformer()::transform)
				.collect(Collectors.toList());
	}

	@Override
	public List<UserDTO> getUsersBornBefore(LocalDate localDate) {
		LOGGER.info("getUsersBornBefore, localDate = {}", localDate);
		return this.userDao.findUsersBornBefore(localDate).stream().map(new UserDTOTransformer()::transform)
				.collect(Collectors.toList());
	}

	@Override
	public void deleteUserById(long id) {
		LOGGER.info("entering deleteUserById");
		this.userDao.deleteUserById(id);
	}

}
