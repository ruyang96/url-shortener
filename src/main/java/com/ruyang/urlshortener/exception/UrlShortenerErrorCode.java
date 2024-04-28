package com.ruyang.urlshortener.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UrlShortenerErrorCode {
    URL_SHORTENER_0001("URL_SHORTENER_0001", "The user with this email already exists", HttpStatus.BAD_REQUEST),
    URL_SHORTENER_0002("URL_SHORTENER_0002", "The user with this email does not exist or password is wrong", HttpStatus.BAD_REQUEST),
    URL_SHORTENER_0003("URL_SHORTENER_0003", "Invalid auth token", HttpStatus.UNAUTHORIZED),
    URL_SHORTENER_0004("URL_SHORTENER_0004", "Auth token is not provided in http headers", HttpStatus.UNAUTHORIZED),
    URL_SHORTENER_0005("URL_SHORTENER_0005", "Auth token has expired", HttpStatus.UNAUTHORIZED),
    URL_SHORTENER_0006("URL_SHORTENER_0006", "Internal server error with encoded ID", HttpStatus.INTERNAL_SERVER_ERROR),
    URL_SHORTENER_0007("URL_SHORTENER_0007", "Invalid short url", HttpStatus.BAD_REQUEST),
    URL_SHORTENER_0008("URL_SHORTENER_0008", "Please provide the original url to be shortened", HttpStatus.BAD_REQUEST),

    URL_SHORTENER_9999("URL_SHORTENER_9999", "Unknown error at Url shortener", HttpStatus.INTERNAL_SERVER_ERROR);

    private String code;
    private String message;
    private HttpStatus status;
}
