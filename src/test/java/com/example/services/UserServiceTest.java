package com.example.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.AbstractIntegrationTestBase;
import com.example.controllers.UserController;
import com.example.dao.UserDAO;
import com.example.dao.entities.User;
import com.example.dao.entities.transformers.UserTransformer;
import com.example.dto.UserDTO;
import com.example.dto.transformers.UserDTOTransformer;
import com.example.utils.CustomDateUtils;

@RunWith(Arquillian.class)
public class UserServiceTest extends AbstractIntegrationTestBase {

	@Deployment
	static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addClass(UserService.class).addClass(UserServiceImpl.class)
				.addClass(UserDTO.class).addClass(UserDTOTransformer.class).addClass(User.class).addClass(UserDAO.class)
				.addClass(UserTransformer.class).addClass(UserController.class).addClass(CustomDateUtils.class)
				.addClass(StringUtils.class).addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
	}

	@Inject
	private UserService userService;

	@Test
	public void testGetUsers() {
		this.userService.saveUser(getMockUser(1));

		List<UserDTO> users = this.userService.getUsers();
		assertNotNull(users);
		assertEquals(1, users.size());
		UserDTO user = users.get(0);
		assertEquals(getMockUser(1).getFirstName(), user.getFirstName());
	}

	@Test
	public void testFindMockUser2ByName() {
		UserDTO user = this.userService.getUsersByName(null, getMockUser(2).getLastName()).stream().findFirst()
				.orElse(null);
		assertNull(user);

		this.userService.saveUser(getMockUser(2));

		user = this.userService.getUsersByName(null, getMockUser(2).getLastName()).stream().findFirst().orElse(null);
		assertNotNull(user);
		assertEquals(getMockUser(2).getFirstName(), user.getFirstName());
	}

	@Test
	public void testFindUserById() {
		int userCount = this.userService.getUsers().size();

		// check that no user with this id exists
//		UserDTO user = this.userService.getUserById(userCount + 1);
//		assertNull(user);

		// auto-generated IDs so fetch the expected next
		this.userService.saveUser(getMockUser(3));

		UserDTO user = this.userService.getUserById(userCount + 1);
		assertNotNull(user);
		assertEquals(getMockUser(3).getFirstName(), user.getFirstName());
	}

	@Test
	public void testFindUsersBornBefore() {
		this.userService.saveUser(getMockUser(4));
		assertEquals(3, this.userService.getUsers().size());

		List<UserDTO> users = this.userService.getUsersBornBefore(LocalDate.now().minusDays(1));
		assertNotNull(users);
		assertEquals(3, users.size());
		assertTrue(users.stream().map(UserDTO::getFirstName).collect(Collectors.toList())
				.contains(getMockUser(2).getFirstName()));
	}

}
