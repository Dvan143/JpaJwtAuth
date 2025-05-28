package org.example.springdatajpaauth.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springdatajpaauth.db.CustomUserDetailsService;
import org.example.springdatajpaauth.db.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    JwtService jwtService;
    @Autowired
    PasswordEncoder encoder;
    @PostMapping("/login")
    public ResponseEntity login(HttpServletResponse response, @RequestParam(name = "username") String username, @RequestParam(name = "password") String password){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            UserDetails user = customUserDetailsService.loadUserByUsername(username);
            String token = jwtService.generateAccessToken(username);

            Cookie cookie = new Cookie("AccessToken", null);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(60*60);
            cookie.setValue(token);
            response.addCookie(cookie);

            return ResponseEntity.ok("");
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
//    @PostMapping("/api/getAccessToken")
//    public ResponseEntity getAccessToken(){
//
//    }
}
