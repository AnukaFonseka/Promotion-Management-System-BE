package com.tyonics.PromotionManagementSystem.service;

import com.tyonics.PromotionManagementSystem.model.Role;
import com.tyonics.PromotionManagementSystem.model.Users;
import com.tyonics.PromotionManagementSystem.repo.RoleRepo;
import com.tyonics.PromotionManagementSystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSevice {
    @Autowired
    private UserRepo repo;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private RoleRepo roleRepo;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user, int roleId ) {
        Role role = roleRepo.findById(roleId).orElseThrow(() -> new IllegalArgumentException("Invalid role ID"));
        user.setPassword(encoder.encode(user.getPassword()));
        user.getRoles().add(role);
        repo.save(user);
        return user;
    }

    public String verify(Users user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }

    // New: Retrieve all users
    public List<Users> getAllUsers() {
        return repo.findAll();
    }

    // New: Update user
    public Users updateUser(int id, Users userDetails) {
        Optional<Users> optionalUser = repo.findById(id);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            user.setUsername(userDetails.getUsername());
            user.setPassword(encoder.encode(userDetails.getPassword())); // Re-encode password
            return repo.save(user);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    // New: Delete user
    public String deleteUser(int id) {
        Optional<Users> optionalUser = repo.findById(id);
        if (optionalUser.isPresent()) {

            Users user = optionalUser.get();
            user.getRoles().clear();
            repo.save(user);


            repo.deleteById(id);
            return "User deleted successfully.";
        } else {
            return "User not found.";
        }
    }

}
