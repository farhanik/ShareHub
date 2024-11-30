package com.sharehub.service;

import com.sharehub.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService
{
    User registerUser(User user);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    User updateUser(User user);
    void deleteUser(Long id);
    List<User> getAllUsers();
    boolean validateCredentials(String email, String password);
}