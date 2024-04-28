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
    @Value("${app.auth.jwt.expiration-seconds}")
    private long expirationTimeInSeconds;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInSeconds * 1000))
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
