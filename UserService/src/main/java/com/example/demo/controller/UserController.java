package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/save")
    public String saveUser(@Valid @RequestBody User user) {
        return service.saveUser(user);
    }

    @PutMapping("/update")
    public User updateUser(@Valid @RequestBody User user) {
        return service.updateUser(user);
    }

    @GetMapping("/fetchById/{id}")
    public User getUser(@PathVariable("id") int userId) {
        return service.getUserById(userId);
    }

    @GetMapping("/fetchAll")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int userId) {
        return service.deleteUserById(userId);
    }
}