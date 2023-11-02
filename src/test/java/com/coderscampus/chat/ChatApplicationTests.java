package com.coderscampus.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.coderscampus.sockets.repository.UserRepository;
import com.coderscampus.sockets.services.UserService;
import com.coderscampus.sockets.domain.User;

@SpringBootTest
@Transactional
class ChatApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Test
	void should_add_registered_user_to_the_database() {

		User user = new User();
		user.setUsername("Test1");
		user.setPassword("Test1");

		User savedUser = userService.createUser(user);

		User retrievedUser = userRepository.findByUsername("testUser");

		assertNotNull(retrievedUser);
		assertEquals("testUser", retrievedUser.getUsername());

	}

}
