package org.example.springdatajpaauth.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.springdatajpaauth.db.AbstractUserBody;
import org.example.springdatajpaauth.db.CustomUserDetailsService;
import org.example.springdatajpaauth.db.UserClass;
import org.example.springdatajpaauth.db.UserService;
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

import java.io.IOException;

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
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(HttpServletResponse response, @RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String accessToken = jwtService.generateAccessToken(username);

            Cookie cookie = new Cookie("Token", accessToken);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setMaxAge(60 * 60 * 24 * 7); // Seven days
            response.addCookie(cookie);
            response.sendRedirect("/");
            return new ResponseEntity("Logined",HttpStatus.OK);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/register")
    public ResponseEntity register(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password, @RequestParam(name = "confirmPassword") String confirmPassword) {
        if(!password.equals(confirmPassword)) return new ResponseEntity("Passwords are not same",HttpStatus.INTERNAL_SERVER_ERROR);
        if(userService.existsByUsername(username)) return new ResponseEntity<>("Entered username is exists",HttpStatus.CONFLICT);

        UserClass user = new UserClass(username, password, "user");
        userService.saveUser(user);

        return new ResponseEntity("User created",HttpStatus.OK);
    }
}
