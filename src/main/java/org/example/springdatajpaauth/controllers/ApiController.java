package org.example.springdatajpaauth.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.springdatajpaauth.db.AbstractUserBody;
import org.example.springdatajpaauth.db.CustomUserDetailsService;
import org.example.springdatajpaauth.db.UserClass;
import org.example.springdatajpaauth.db.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            String token = jwtService.generateRefreshToken(username);

            Cookie cookie = new Cookie("RefreshToken", null);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(60*60*24*2); // Two days

            cookie.setValue(token);
            response.addCookie(cookie);

            return ResponseEntity.ok("");
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
    // For AccessToken in future
//   @PostMapping("/api/getRefreshToken")
//    public ResponseEntity getRefreshToken(HttpServletResponse response, @Valid @RequestBody AbstractUserBody user){
//        try{
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
//            String token = jwtService.generateRefreshToken(user.getUsername());
//            return ResponseEntity.status(HttpStatus.OK).body(token);
//        } catch (AuthenticationException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username or password is incorrect");
//        }
//   }
}
