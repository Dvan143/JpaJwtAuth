package org.example.springdatajpaauth.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/register")
    public String register(){
        return "register";
    }
    @GetMapping("/greeting")
    public String greeting(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username",username);
        return "greeting";
    }
}
