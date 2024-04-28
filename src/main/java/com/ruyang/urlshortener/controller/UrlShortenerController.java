package com.ruyang.urlshortener.controller;

import com.ruyang.generated.api.UrlShortenerApi;
import com.ruyang.generated.model.UrlShorteningPayload;
import com.ruyang.generated.model.UrlShorteningResponse;
import com.ruyang.generated.model.User;
import com.ruyang.generated.model.UserCredentials;
import com.ruyang.urlshortener.service.UrlShortenerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class UrlShortenerController implements UrlShortenerApi {
    private final UrlShortenerService urlShortenerService;

    @Override
    public ResponseEntity<String> authenticateUser(UserCredentials userCredentials) {
        return ResponseEntity.ok(urlShortenerService.authenticateUser(userCredentials));
    }

    @Override
    public ResponseEntity<UrlShorteningResponse> createShortenedUrl(UrlShorteningPayload urlShorteningPayload) {
        return ResponseEntity.ok(urlShortenerService.createShortenedUrl(urlShorteningPayload));
    }

    @Override
    public ResponseEntity<Void> getOriginalUrl(String urlId) {
        String originalUrl = urlShortenerService.getOriginalUrl(urlId);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
    }

    @Override
    public ResponseEntity<User> registerUser(UserCredentials userCredentials) {
        return ResponseEntity.ok(urlShortenerService.registerUser(userCredentials));
    }
}
