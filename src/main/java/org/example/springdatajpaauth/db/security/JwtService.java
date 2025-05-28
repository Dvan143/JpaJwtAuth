package org.example.springdatajpaauth.db.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {
    private final String accessSecret = "DKSA,mXCZ<,./,/.,f.sdf.dsf,e.,rwer.,vcxmn,#$@*7G";
    private final String refreshSecret = "DKSA,mXCZ</.,';;/.,./[][]]]3342gfdGFMmgdkflmgrFG";

    // Access
    public String generateAccessToken(String username){
        return Jwts.builder().subject(username).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+1000*60*15)).signWith(getAccessKey()).compact();
    }
    public String extractUsernameFromAccessToken(String token){
        return Jwts.parser().verifyWith(getAccessKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }
    public boolean isAccessTokenValid(String token, String username){
        if(Jwts.parser().verifyWith(getAccessKey()).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date())) return false;
        if(!Jwts.parser().verifyWith(getAccessKey()).build().parseSignedClaims(token).getPayload().getSubject().equals(username)) return false;
        return true;
    }
    // Refresh
    public String generateRefreshToken(String username){
        return Jwts.builder().subject(username).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+1000*60*60*24*2)).signWith(getRefreshKey()).compact();
    }
    public String extractUsernameFromRefreshToken(String token){
        return Jwts.parser().verifyWith(getRefreshKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }
    public boolean isRefreshTokenValid(String token, String username){
        if(Jwts.parser().verifyWith(getRefreshKey()).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date())) return false;
        if(!Jwts.parser().verifyWith(getRefreshKey()).build().parseSignedClaims(token).getPayload().getSubject().equals(username)) return false;
        return true;
    }

    public SecretKey getAccessKey(){
        return Keys.hmacShaKeyFor(accessSecret.getBytes());
    }
    public SecretKey getRefreshKey(){
        return Keys.hmacShaKeyFor(refreshSecret.getBytes());
    }
}
