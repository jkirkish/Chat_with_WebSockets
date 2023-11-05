package com.coderscampus.chat;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.coderscampus.sockets.ChatApplication;
import com.coderscampus.sockets.domain.User;
import com.coderscampus.sockets.repository.UserRepository;
import com.coderscampus.sockets.services.UserService;

@SpringBootTest(classes = ChatApplication.class)
class ChatApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	private User testUser;

	@BeforeEach
	void setUp() {
		testUser = new User();
		testUser.setUsername("Test1");
		testUser.setPassword("Test1");
		testUser.setEmail("Test@email.com");
		testUser.setFirstName("Test1");
		testUser.setLastName("Test1");

	}

	@Test
	void should_add_registered_user_to_the_database() {

		User savedUser = userService.createUser(testUser);

		List<User> users = userService.findAll();
		assertNotNull(savedUser);
		assertEquals("Test1", savedUser.getUsername());
		assertEquals("Test1", savedUser.getPassword());
		assertEquals("Test@email.com", savedUser.getEmail());
		assertEquals("Test1", savedUser.getFirstName());
		assertEquals("Test1", savedUser.getLastName());
		boolean found = false;
		for (User user : users) {
			if (user.getUserId().equals(savedUser.getUserId())) {
				found = true;
				break;
			}
		}
		assertTrue(found, "User not found in the list");
		userService.delete(savedUser.getUserId());
	}

	@Test
	void should_delete_registered_user_from_database() {

		// Arrange
		User savedUser = userService.createUser(testUser);
		assertNotNull(savedUser);

		// Act Delete the User
		userService.delete(savedUser.getUserId());

		// Assert: Confirm the the user was deleted
		List<User> users = userService.findAll();
		assertTrue(users.isEmpty(), "User should be deleted");

	}

	@Test
	public void should_update_current_user_when_any_attribute_is_changed() {

		// Arrange
		User savedUser = userService.createUser(testUser);
		assertEquals("Test1", savedUser.getFirstName());
		assertEquals("Test1", savedUser.getUsername());
		assertEquals("Test1", savedUser.getPassword());
		assertEquals("Test@email.com", savedUser.getEmail());
		assertEquals("Test1", savedUser.getLastName());
		// Act
		testUser.setFirstName("John");
		testUser.setLastName("Doe");
		testUser.setEmail("john@email.com");
		testUser.setPassword("JohnP");
		testUser.setUsername("JohnnyBeGood");
		savedUser = userService.createUser(testUser);

		// Assert
		assertEquals("John", savedUser.getFirstName());
		assertEquals("JohnnyBeGood", savedUser.getUsername());
		assertEquals("JohnP", savedUser.getPassword());
		assertEquals("john@email.com", savedUser.getEmail());
		assertEquals("Doe", savedUser.getLastName());
		userService.delete(savedUser.getUserId());
	}

	@Test
	public void should_be_able_to_retrieve_a_user_from_the_database() {

		// Arrange
		User savedUser = userService.createUser(testUser);
		// Act
		savedUser = userService.findById(savedUser.getUserId());
		// Assert
		assertEquals("Test1", savedUser.getFirstName());
		assertEquals("Test1", savedUser.getUsername());
		assertEquals("Test1", savedUser.getPassword());
		assertEquals("Test@email.com", savedUser.getEmail());
		assertEquals("Test1", savedUser.getLastName());
		userService.delete(savedUser.getUserId());
	}
}
