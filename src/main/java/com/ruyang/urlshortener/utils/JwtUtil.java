package com.ruyang.urlshortener.utils;

import com.ruyang.urlshortener.exception.UrlShortenerErrorCode;
import com.ruyang.urlshortener.exception.UrlShortenerException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${app.auth.jwt.secret-key}")
    private String secret;
    private static final long EXPIRATION_TIME_IN_SECONDS = 3600; // 1 hour

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_IN_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e){
            throw new UrlShortenerException(UrlShortenerErrorCode.URL_SHORTENER_0005);
        }
        catch (Exception e) {
            throw new UrlShortenerException(UrlShortenerErrorCode.URL_SHORTENER_0003);
        }
    }
}
