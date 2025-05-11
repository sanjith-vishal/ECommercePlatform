package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
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
        Optional<User> optional = repository.findById(userId);
        if (optional.isPresent()) {
            log.info("User found with ID: {}", userId);
            return optional.get();
        } else {
            log.warn("User with ID {} not found", userId);
            return null;
        }
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
        if (repository.existsById(userId)) {
            repository.deleteById(userId);
            log.info("User with ID {} deleted successfully", userId);
            return "User Deleted Successfully!";
        } else {
            log.warn("User with ID {} not found", userId);
            return "User Not Found.";
        }
    }
}