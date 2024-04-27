package com.ruyang.urlshortener.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UrlShortenerErrorCode {
    URL_SHORTENER_0001("URL_SHORTENER_0001", "The user with this email already exists!", HttpStatus.BAD_REQUEST),
    URL_SHORTENER_0002("URL_SHORTENER_0002", "The user with this email does not exist or password is wrong!", HttpStatus.BAD_REQUEST),
    URL_SHORTENER_9999("URL_SHORTENER_9999", "Unknown error at Url shortener", HttpStatus.INTERNAL_SERVER_ERROR);
    private String code;
    private String message;
    private HttpStatus status;
}
