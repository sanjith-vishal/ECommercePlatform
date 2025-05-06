package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public String saveUser(User user) {
        repository.save(user);
        return "User saved successfully!";
    }

    @Override
    public User updateUser(User user) {
        return repository.save(user);
    }

    @Override
    public User getUser(int userId) {
        Optional<User> optional = repository.findById(userId);
        return optional.orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public String deleteUser(int userId) {
        repository.deleteById(userId);
        return "User deleted successfully!";
    }
}