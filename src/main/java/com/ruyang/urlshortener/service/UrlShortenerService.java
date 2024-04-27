package com.ruyang.urlshortener.service;

import com.ruyang.generated.model.UrlShorteningPayload;
import com.ruyang.generated.model.UrlShorteningResponse;
import com.ruyang.generated.model.User;
import com.ruyang.generated.model.UserCredentials;

public interface UrlShortenerService {
    UrlShorteningResponse createShortenedUrl(UrlShorteningPayload urlShorteningPayload);
    String authenticateUser(UserCredentials userCredentials);
    void getOriginalUrl();
    User registerUser(UserCredentials userCredentials);
}
