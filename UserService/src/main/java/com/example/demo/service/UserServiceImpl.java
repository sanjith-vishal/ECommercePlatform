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

	// Saves a user to the repository.
	// Logs the attempt to save the user.
	// Returns a success or failure message based on the save operation.
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

	// Updates an existing user in the repository.
	// Logs the update operation.
	// Returns the updated user object.
	@Override
	public User updateUser(User user) {
		log.info("In UserServiceImpl updateUser method....");
		User updatedUser = repository.save(user);
		log.info("User with email {} updated successfully", user.getEmail());
		return updatedUser;
	}

	// Retrieves a user by their ID from the repository.
	// Logs the retrieval operation.
	// Throws an exception if the user is not found.
	@Override
	public User getUserById(int userId) {
		log.info("In UserServiceImpl getUserById method....");
		return repository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
	}

	// Retrieves all users from the repository.
	// Logs the number of users retrieved.
	@Override
	public List<User> getAllUsers() {
		log.info("In UserServiceImpl getAllUsers method....");
		List<User> users = repository.findAll();
		log.info("Retrieved {} users", users.size());
		return users;
	}

	// Deletes a user by their ID if they exist in the repository.
	// Logs the deletion attempt and result.
	// Throws an exception if the user is not found.
	// Returns a success message if deletion is successful.
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