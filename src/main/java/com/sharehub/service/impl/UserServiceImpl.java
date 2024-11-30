package com.sharehub.service.impl;

import com.sharehub.model.User;
import com.sharehub.service.UserService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private Map<Long, User> userDatabase = new HashMap<>();
    private Long currentId = 1L;

    @Override
    public User registerUser(User user)
    {
        // Basic validation
        if (getUserByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Set ID and save user
        user.setId(currentId++);
        userDatabase.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userDatabase.get(id));
    }

    @Override
    public Optional<User> getUserByEmail(String email)
    {
        return userDatabase.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public User updateUser(User user)
    {
        if (!userDatabase.containsKey(user.getId()))
        {
            throw new IllegalArgumentException("User not found");
        }
        userDatabase.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        userDatabase.remove(id);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userDatabase.values());
    }

    @Override
    public boolean validateCredentials(String email, String password)
    {
        Optional<User> userOptional = getUserByEmail(email);
        if (userOptional.isPresent())
        {
            User user = userOptional.get();
            return user.getPassword().equals(password);
        }
        return false;
    }

}