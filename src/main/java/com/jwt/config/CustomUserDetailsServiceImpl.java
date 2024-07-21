package com.jwt.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      PasswordEncoder passwordEncoder =new BCryptPasswordEncoder(); 
        String encodedPassword = passwordEncoder.encode("Suzal123"); 
        if (username.equals("Suzal")) {
            return new User("Suzal", encodedPassword, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
