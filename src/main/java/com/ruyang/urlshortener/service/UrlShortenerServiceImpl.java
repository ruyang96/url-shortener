package com.ruyang.urlshortener.service;

import com.ruyang.generated.model.UrlShorteningPayload;
import com.ruyang.generated.model.UrlShorteningResponse;
import com.ruyang.generated.model.User;
import com.ruyang.generated.model.UserCredentials;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {

    @Override
    public UrlShorteningResponse createShortenedUrl(UrlShorteningPayload urlShorteningPayload) {
        return null;
    }

    @Override
    public String authenticateUser(UserCredentials userCredentials) {
        return null;
    }

    @Override
    public void getOriginalUrl() {

    }

    @Override
    public User registerUser(UserCredentials userCredentials) {
        return null;
    }
}
