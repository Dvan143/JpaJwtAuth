package org.example.springdatajpaauth.db.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtServiceTest {
    @Autowired
    private JwtService jwtService;
    private String username;
    private String accessToken;
    private String refreshToken;

    @BeforeEach
    public void setUp(){
        username = "name";
        accessToken = jwtService.generateAccessToken(username);
        refreshToken = jwtService.generateRefreshToken(username);
    }

    @Test
    public void shouldTokenBeNotNull(){
        assertNotNull(accessToken);
        assertNotNull(refreshToken);
    }

    @Test
    public void shouldGenerateValidToken(){

        assertTrue(jwtService.isAccessTokenValid(accessToken,username));
        assertTrue(jwtService.isRefreshTokenValid(refreshToken,username));
    }

    @Test
    public void shouldGenerateValidUsername(){
        assertEquals(username,jwtService.extractUsernameFromAccessToken(accessToken));
        assertEquals(username,jwtService.extractUsernameFromRefreshToken(refreshToken));
    }
}
