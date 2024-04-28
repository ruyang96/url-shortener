package com.ruyang.urlshortener.controller;

import com.ruyang.generated.model.UrlShorteningPayload;
import com.ruyang.generated.model.UrlShorteningResponse;
import com.ruyang.generated.model.User;
import com.ruyang.generated.model.UserCredentials;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UrlShortenerControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void registerUser() throws Exception {
        var response = this.restTemplate.exchange(
                "http://localhost:" + port + "/api/user/new",
                HttpMethod.POST,
                new HttpEntity<>(getUserCredentials()),
                User.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        Assertions.assertNotNull(response.getBody().getId());
        Assertions.assertNotNull(response.getBody().getEmail());
        Assertions.assertNotNull(response.getBody().getCreatedAt());
    }

    @Test
    @Order(2)
    void testAuthenticateUser() throws Exception {
        var response = this.restTemplate.exchange(
                "http://localhost:" + port + "/api/user/auth",
                HttpMethod.POST,
                new HttpEntity<>(getUserCredentials()),
                String.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    @Order(3)
    void testCreateShortenedUrl() throws Exception {
        var token = this.restTemplate.exchange(
                "http://localhost:" + port + "/api/user/auth",
                HttpMethod.POST,
                new HttpEntity<>(getUserCredentials()),
                String.class).getBody();
        var UrlShorteningPayload = new UrlShorteningPayload().originalUrl("https://www.google.com/maps");
        var response = this.restTemplate.exchange(
                "http://localhost:" + port + "/api/url/shorten",
                HttpMethod.POST,
                new HttpEntity<>(UrlShorteningPayload, getHttpHeaders(token)),
                UrlShorteningResponse.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        Assertions.assertNotNull(response.getBody().getShortenedUrl());
        Assertions.assertNotNull(response.getBody().getOriginalUrl());
        Assertions.assertNotNull(response.getBody().getLinks().getGetUrl());
    }

    @Test
    @Order(4)
    void testGetOriginalUrl() throws Exception {
        var response = this.restTemplate.exchange(
                "http://localhost:" + port + "/api/url/1",
                HttpMethod.GET,
                null,
                Void.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.FOUND.value(), response.getStatusCode().value());
        Assertions.assertEquals("https://www.google.com/maps", response.getHeaders().getLocation().toString());
    }

    private UserCredentials getUserCredentials() {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setEmail("ruyang@email.com");
        userCredentials.setPassword("pass");
        return userCredentials;
    }

    private HttpHeaders getHttpHeaders(String bearerToken) {
        var headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setCacheControl("no-cache");
        headers.setConnection("keep-alive");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
