//package com.example.TienDatShop.util;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class JwtUtil {
//
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    @Value("${jwt.expirationMs}")
//    private long expirationMs;
//
//    private Key getSigningKey() {
//        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//    }
//
//    public String generateToken(String email, Long userId, String role) {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime expiry = now.plusMinutes(expirationMs / 60000); // convert ms → minutes
//
//        Date issuedAt = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
//        Date expiryDate = Date.from(expiry.atZone(ZoneId.systemDefault()).toInstant());
//
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("id", userId);
//        claims.put("role", role);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(email) // email làm định danh chính
//                .setIssuedAt(issuedAt)
//                .setExpiration(expiryDate)
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getSigningKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public Long extractUserId(String token) {
//        return ((Number) extractAllClaims(token).get("id")).longValue();
//    }
//
//    public String extractRole(String token) {
//        return (String) extractAllClaims(token).get("role");
//    }
//
//    public String extractEmail(String token) {
//        return extractAllClaims(token).getSubject();
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(getSigningKey())
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            return false;
//        }
//    }
//}
