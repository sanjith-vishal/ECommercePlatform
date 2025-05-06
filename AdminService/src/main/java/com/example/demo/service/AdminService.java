package com.example.demo.service;

import java.util.List;
import com.example.demo.model.Admin;

public interface AdminService {
    public abstract String saveAdmin(Admin admin);
    public abstract Admin updateAdmin(Admin admin);
    public abstract Admin getAdminById(int id);
    public abstract List<Admin> getAllAdmins();
    public abstract String deleteAdminById(int id);
}
