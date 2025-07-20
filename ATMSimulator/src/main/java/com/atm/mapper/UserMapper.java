package com.atm.mapper;

import com.atm.model.User;
import com.atm.dto.UserDTO;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        return new UserDTO(
            user.getUserId(),
            user.getName(),
            user.getCardNumber(),
            user.getMobile(),
            user.getAccountType(),
            user.getStatus(),
            user.getAccountNumber()
        );
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User(
            dto.getUserId(),
            dto.getName(),
            dto.getCardNumber(),
            "",
            dto.getMobile(),
            dto.getAccountType(),
            dto.getStatus()
        );
        user.setAccountNumber(dto.getAccountNumber());
        return user;
    }
}