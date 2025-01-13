package com.tyonics.PromotionManagementSystem.controller;
import com.tyonics.PromotionManagementSystem.model.Users;
import com.tyonics.PromotionManagementSystem.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserSevice service;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public Users register(@RequestBody Map<String, Object> userData) {
        Users user = new Users();
        user.setUsername((String) userData.get("username"));
        user.setPassword((String) userData.get("password"));
        int roleId = (Integer) userData.get("role");
        return service.register(user, roleId);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Users user) {
        String token = service.verify(user);  // Assuming this method returns the token
        Map<String, String> response = new HashMap<>();
        response.put("accessToken", token);
        return ResponseEntity.ok(response);  // Returns a JSON object with the token
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = service.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // New: Update user
    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Users updateUser(@PathVariable int id, @RequestBody Users userDetails) {
        return service.updateUser(id, userDetails);
    }

    // New: Delete user
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        return ResponseEntity.ok(service.deleteUser(id));
    }

}
