package com.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.config.CustomUserDetailsServiceImpl;
import com.jwt.helper.JwtUtil;
import com.jwt.model.JwtRequest;
import com.jwt.model.JwtResponse;

@RestController
public class JwtController {

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsServiceImpl;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    // @PostMapping("/token")
    // public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{

    //     System.out.println("Received request "+  jwtRequest.getUsername());

    //     try {
    //         this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
            
    //     } catch (UsernameNotFoundException e) {
    //     e.printStackTrace();
    //     throw new Exception("Bad Credentials");
    //     }

    //     UserDetails userByUsername = this.customUserDetailsServiceImpl.loadUserByUsername(jwtRequest.getUsername());
    //     String token = this.jwtUtil.generateToken(userByUsername);
    //     System.out.println("JWT "+ token);

    //     return ResponseEntity.ok(new JwtResponse(token));
        
    // }

    // @PostMapping("/token")
    // public void received(@RequestBody JwtRequest jwtRequest)
    // {
    //     System.out.println(jwtRequest);
        
    // }


    @PostMapping("/token")
public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) {
    System.out.println("Received request " + jwtRequest.getUsername());

    try {
        // Authenticate the user
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword())
        );
    } catch (UsernameNotFoundException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
    } catch (BadCredentialsException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication error");
    }

    // Load user details and generate token
    UserDetails userByUsername = customUserDetailsServiceImpl.loadUserByUsername(jwtRequest.getUsername());
    String token = jwtUtil.generateToken(userByUsername);
    System.out.println("JWT " + token);

    return ResponseEntity.ok(new JwtResponse(token));
}


}
