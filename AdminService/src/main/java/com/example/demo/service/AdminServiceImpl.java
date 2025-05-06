package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository repository;

    @Override
    public String saveAdmin(Admin admin) {
        repository.save(admin);
        return "Admin saved successfully.";
    }

    @Override
    public Admin updateAdmin(Admin admin) {
        return repository.save(admin);
    }

    @Override
    public Admin getAdminById(int id) {
        Optional<Admin> optional = repository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return repository.findAll();
    }

    @Override
    public String deleteAdminById(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Admin deleted successfully.";
        } else {
            return "Admin not found.";
        }
    }
}
