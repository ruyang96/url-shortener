package com.ruyang.urlshortener.controller;

import com.ruyang.generated.api.UrlShortenerApi;
import com.ruyang.generated.model.UrlShorteningPayload;
import com.ruyang.generated.model.UrlShorteningResponse;
import com.ruyang.generated.model.User;
import com.ruyang.generated.model.UserCredentials;
import com.ruyang.urlshortener.service.UrlShortenerServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class UrlShortenerController implements UrlShortenerApi {
    private final UrlShortenerServiceImpl urlShortenerServiceImpl;

    @Override
    public ResponseEntity<String> authenticateUser(UserCredentials userCredentials) {
        return ResponseEntity.ok(urlShortenerServiceImpl.authenticateUser(userCredentials));
    }

    @Override
    public ResponseEntity<UrlShorteningResponse> createShortenedUrl(UrlShorteningPayload urlShorteningPayload) {
        return ResponseEntity.ok(urlShortenerServiceImpl.createShortenedUrl(urlShorteningPayload));
    }

    @Override
    public ResponseEntity<Void> getOriginalUrl(String urlId) {
        urlShortenerServiceImpl.getOriginalUrl();
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<User> registerUser(UserCredentials userCredentials) {
        return ResponseEntity.ok(urlShortenerServiceImpl.registerUser(userCredentials));
    }
}
