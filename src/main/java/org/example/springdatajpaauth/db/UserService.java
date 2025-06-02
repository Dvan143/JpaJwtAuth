package org.example.springdatajpaauth.db;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }
    public UserClass getUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }
    public void saveUser(UserClass userClass){
        UserClass newUser = new UserClass(userClass.getUsername(), encoder.encode(userClass.password), userClass.getRole());
        userRepository.save(newUser);
    }

    @PostConstruct
    public void run(){
        userRepository.save(new UserClass("admin", encoder.encode("admin"),"admin"));
        userRepository.save(new UserClass("bob", encoder.encode("1234"),"user"));
    }
}
