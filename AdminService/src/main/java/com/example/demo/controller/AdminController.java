package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Admin;
import com.example.demo.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService service;

    @PostMapping("/save")
    public String saveAdmin(@Valid @RequestBody Admin admin) {
        return service.saveAdmin(admin);
    }

    @PutMapping("/update")
    public Admin updateAdmin(@Valid @RequestBody Admin admin) {
        return service.updateAdmin(admin);
    }

    @GetMapping("/fetchById/{id}")
    public Admin getAdminById(@PathVariable int id) {
        return service.getAdminById(id);
    }

    @GetMapping("/fetchAll")
    public List<Admin> getAllAdmins() {
        return service.getAllAdmins();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable int id) {
        return service.deleteAdminById(id);
    }
}
