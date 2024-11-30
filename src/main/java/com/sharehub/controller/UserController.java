package com.sharehub.controller;

import com.sharehub.model.User;
import com.sharehub.service.UserService;
import com.sharehub.model.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody User user)
    {
        try
        {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(new ApiResponse<>(true, "User registered successfully", registeredUser));
        }
        catch (IllegalArgumentException e)
        {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> login(@RequestBody LoginRequest request)
    {
        if (userService.validateCredentials(request.getEmail(), request.getPassword()))
        {
            User user = userService.getUserByEmail(request.getEmail()).get();
            return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", user));
        }
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, "Invalid credentials", null));
    }

    @GetMapping("/{id}")
    //Retrieves a user by their ID.
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable Long id)
    {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(new ApiResponse<>(true, "User found", user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    //Updates a user’s information.
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody User user)
    {
        try
        {
            user.setId(id);
            User updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully", updatedUser));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id)
    {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", null));
    }
}