package com.ruyang.urlshortener.utils;

import com.ruyang.generated.model.Error;
import com.ruyang.urlshortener.exception.UrlShortenerErrorCode;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class ConverterUtil {
    private ConverterUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Error createErrorfromErrorCode(UrlShortenerErrorCode errorCode) {
        return new Error()
                .code(errorCode.getCode())
                .status(errorCode.getStatus().value())
                .message(errorCode.getMessage());
    }

    public static URI getUri(String originalUrl) {
        var uri = URI.create(originalUrl);

        if (uri.getScheme() == null) {
            UriComponentsBuilder.fromUri(uri).scheme("https");
            uri = UriComponentsBuilder.newInstance().scheme("https").host(originalUrl).build().toUri();

        }
        return uri;
    }
}
