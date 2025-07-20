package com.atm.dao;

import com.atm.model.Admin;

public interface AdminDAO {
    Admin getAdminByUsername(String username);
    boolean validateAdmin(String username, String password);
    boolean updateAdminPassword(String username, String newPassword);
    boolean createAdmin(Admin admin);
}