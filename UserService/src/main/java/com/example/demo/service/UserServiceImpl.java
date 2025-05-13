package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import lombok.NoArgsConstructor;

import java.util.List;
import com.example.demo.exceptions.UserNotFoundException;

@Service
@NoArgsConstructor
public class UserServiceImpl implements UserService {

	Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserRepository repository;

	@Override
	public String saveUser(User user) {
		log.info("In UserServiceImpl saveUser method....");
		repository.save(user);
		if (repository.save(user) != null) {
			log.info("User with email {} saved successfully", user.getEmail());
			return "User Registered Successfully!";
		} else {
			log.warn("Failed to save user with email {}", user.getEmail());
			return "User Registration Failed.";
		}
	}

	@Override
	public User updateUser(User user) {
		log.info("In UserServiceImpl updateUser method....");
		User updatedUser = repository.save(user);
		log.info("User with email {} updated successfully", user.getEmail());
		return updatedUser;
	}

	@Override
	public User getUserById(int userId) {
		log.info("In UserServiceImpl getUserById method....");
		return repository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
	}

	@Override
	public List<User> getAllUsers() {
		log.info("In UserServiceImpl getAllUsers method....");
		List<User> users = repository.findAll();
		log.info("Retrieved {} users", users.size());
		return users;
	}

	@Override
	public String deleteUserById(int userId) {
		log.info("In UserServiceImpl deleteUserById method....");
		if (!repository.existsById(userId)) {
			log.warn("User with ID {} not found", userId);
			throw new UserNotFoundException("User with ID " + userId + " not found");
		}
		repository.deleteById(userId);
		log.info("User with ID {} deleted successfully", userId);
		return "User Deleted Successfully!";
	}
}