package com.ruyang.urlshortener.service;

import com.ruyang.generated.model.UrlShorteningResponse;
import com.ruyang.generated.model.User;
import com.ruyang.generated.model.UserCredentials;

public interface UrlShortenerService {
    UrlShorteningResponse createShortenedUrl(String originalUrl);
    String authenticateUser(UserCredentials userCredentials);
    void getOriginalUrl();
    User registerUser(UserCredentials userCredentials);
}
