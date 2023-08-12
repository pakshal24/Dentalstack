//package com.dentalstack.auth.demo.Jwt;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class JwtUtils {
//
//    @Value("${jwt.secret}")
//    private String jwtSecret; // Set this in your application.properties
//
//    @Value("${jwt.expiration}")
//    private int jwtExpirationMs; // Set this in your application.properties
//
//    public String generateJwtToken(String phoneNumber) {
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
//
//        return Jwts.builder()
//                .setSubject(phoneNumber)
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
//}
