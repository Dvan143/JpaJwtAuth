package org.example.springdatajpaauth.db;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class DbServicesTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    public void existUserMethodWorks(){
        userRepository.save(new UserClass("FirstTestName","TestPassword"));
        assertTrue(userRepository.existsByUsername("FirstTestName"));
        assertTrue(userService.existsByUsername("FirstTestName"));
    }

    @Test
    public void shouldCreatingUser(){
        userRepository.save(new UserClass("FirstTestName","TestPassword"));
        userRepository.save(new UserClass("SecondTestName","TestPassword"));

        assertTrue(userRepository.existsByUsername("FirstTestName"));
        assertTrue(userRepository.existsByUsername("SecondTestName"));
    }

    @Test
    public void shouldReturnUserByUsername(){
        String name = "name";
        UserClass user = new UserClass(name,"TestPassword");
        userRepository.save(user);

        assertEquals(user, userRepository.getUserByUsername(name));
        assertEquals(user, userService.getUserByUsername(name));
    }
}