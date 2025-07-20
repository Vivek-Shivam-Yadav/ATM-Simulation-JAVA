package com.atm.dao;

import com.atm.model.User;
import java.util.List;

public interface UserDAO {
    User getUserByCard(String cardNumber);
    User getUserByAccount(String accountNumber);
    boolean updateUserPin(String cardNumber, String newPin);
    boolean updateUserStatus(String cardNumber, String status);
    List<User> getAllUsers();
    boolean createUser(User user);
}