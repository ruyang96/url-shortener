package com.ruyang.urlshortener.utils;

import com.ruyang.urlshortener.exception.UrlShortenerErrorCode;
import com.ruyang.urlshortener.exception.UrlShortenerException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET = "the-secret-key";
    private static final long EXPIRATION_TIME_IN_SECONDS = 3600; // 1 hour

    private JwtUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_IN_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static void validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET)
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
