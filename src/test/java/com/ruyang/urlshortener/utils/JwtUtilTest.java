package com.ruyang.urlshortener.utils;

import com.ruyang.urlshortener.exception.UrlShortenerErrorCode;
import com.ruyang.urlshortener.exception.UrlShortenerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.util.ReflectionTestUtils;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JwtUtilTest {
    private JwtUtil jwtUtil = new JwtUtil();
    private String generatedToken;

    @BeforeAll
    void init() {
        ReflectionTestUtils.setField(jwtUtil, "secret", "testSecret");
        ReflectionTestUtils.setField(jwtUtil, "expirationTimeInSeconds", 3600L);
    }

    @Test
    @Order(1)
    void testGenerateToken() {
        generatedToken = jwtUtil.generateToken("testUserName");
        Assertions.assertNotNull(generatedToken);
    }

    @Test
    @Order(2)
    void testValidateToken() {
        jwtUtil.validateToken(generatedToken);
    }

    @Test
    void testValidateInvalidToken() {
        UrlShortenerException exception = Assertions.assertThrows(UrlShortenerException.class, () -> jwtUtil.validateToken("invalidToken"));
        Assertions.assertEquals(UrlShortenerErrorCode.URL_SHORTENER_0003, exception.getErrorCode());
    }

    @Test
    void testValidateExpiredToken() {
        ReflectionTestUtils.setField(jwtUtil, "expirationTimeInSeconds", -1L);
        String token = jwtUtil.generateToken("testUserName");
        UrlShortenerException exception = Assertions.assertThrows(UrlShortenerException.class, () -> jwtUtil.validateToken(token));
        Assertions.assertEquals(UrlShortenerErrorCode.URL_SHORTENER_0005, exception.getErrorCode());
    }

}
