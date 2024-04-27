package com.ruyang.urlshortener.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UrlShortenerException extends RuntimeException{
    private final UrlShortenerErrorCode errorCode;
}
