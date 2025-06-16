package com.example.social_media.Security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${social_media.app.secret}")
    private String APP_SECRET;

    @Value("${social_media.expires.in}")
    private Long EXPIRES_IN;

    public String generateJwtToken(Authentication auth) {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);

        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, APP_SECRET)
                .compact();
    }

    public Long getUserIdFromJwt(String token) {
        System.out.println("getUserIdFromJwt");
        Claims claims = Jwts.parser()
                .setSigningKey(APP_SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(APP_SECRET).build().parseClaimsJws(token);
            System.out.println("validate başarılı");
            return !isTokenExpired(token);
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            System.out.println("validateToken Exception: " + e.getMessage());
            System.out.println("valide başarısız");
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        System.out.println("isTokenExpired");
        Date expiration = Jwts.parser()
                .setSigningKey(APP_SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
