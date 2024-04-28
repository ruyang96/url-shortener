package com.ruyang.urlshortener.utils;

import com.ruyang.urlshortener.exception.UrlShortenerErrorCode;
import com.ruyang.urlshortener.exception.UrlShortenerException;

public class Base62Encoder {

    private static final int BASE = 62;
    private static final String DICTIONARY = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private Base62Encoder() {
        throw new IllegalStateException("Utility class");
    }

    public static String encode(long id) {
        if (id <= 0) {
            throw new UrlShortenerException(UrlShortenerErrorCode.URL_SHORTENER_0006);
        }
        StringBuilder stringBuilder = new StringBuilder();
        int remainder;
        do {
            remainder = (int) (id % BASE);
            stringBuilder.append(DICTIONARY.charAt(remainder));
            id = id / BASE;
        } while (id != 0);
        return stringBuilder.toString();
    }

    public static long decode(String url) {
        long result = 0;
        for (int i = 0; i < url.length(); i++) {
            int charIndex = DICTIONARY.indexOf(url.charAt(i));
            if (charIndex == -1) {
                throw new UrlShortenerException(UrlShortenerErrorCode.URL_SHORTENER_0006);
            }
            result = result + (long) Math.pow(BASE, i) * charIndex;
        }
        return result;
    }
}
