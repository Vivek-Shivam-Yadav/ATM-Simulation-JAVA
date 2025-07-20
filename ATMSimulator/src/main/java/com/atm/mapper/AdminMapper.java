package com.atm.mapper;

import com.atm.model.Admin;
import com.atm.dto.AdminDTO;
import com.atm.util.DateTimeUtil;

public class AdminMapper {

    public static AdminDTO toDTO(Admin admin) {
        if (admin == null) return null;

        return new AdminDTO(
            admin.getAdminId(),
            admin.getUsername(),
            admin.getRole(),
            admin.getLastLogin() != null ? 
                DateTimeUtil.formatDateTime(admin.getLastLogin()) : null
        );
    }

    public static Admin toEntity(AdminDTO dto) {
        if (dto == null) return null;

        Admin admin = new Admin(
            dto.getAdminId(),
            dto.getUsername(),
            "",
            dto.getRole()
        );

        if (dto.getLastLogin() != null) {
            admin.setLastLogin(DateTimeUtil.parseDateTime(dto.getLastLogin()));
        }

        return admin;
    }
}