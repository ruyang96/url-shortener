package com.ruyang.urlshortener.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;

class ConverterUtilTest {

    @Test
    void testGetUri(){
        String url = "https://www.baeldung.com/java-url";
        URI uri = ConverterUtil.getUri(url);
        Assertions.assertEquals(url, uri.toString());
    }

    @Test
    void testGetUriWithoutScheme(){
        String url = "google.com";
        URI uri = ConverterUtil.getUri(url);
        Assertions.assertEquals("https://google.com", uri.toString());
    }
}
