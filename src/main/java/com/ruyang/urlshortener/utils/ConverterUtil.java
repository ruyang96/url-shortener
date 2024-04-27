package com.ruyang.urlshortener.utils;

import com.ruyang.generated.model.Error;
import com.ruyang.urlshortener.exception.UrlShortenerErrorCode;

public class ConverterUtil {
    private ConverterUtil(){
        throw new IllegalStateException("Utility class");
    }

    public static Error createErrorfromErrorCode(UrlShortenerErrorCode errorCode){
        return new Error()
                .code(errorCode.getCode())
                .status(errorCode.getStatus().value())
                .message(errorCode.getMessage());
    }
}
