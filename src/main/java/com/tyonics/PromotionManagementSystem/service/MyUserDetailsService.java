package com.tyonics.PromotionManagementSystem.service;

import com.tyonics.PromotionManagementSystem.model.UserPrincipal;
import com.tyonics.PromotionManagementSystem.model.Users;
import com.tyonics.PromotionManagementSystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user);
    }
}
