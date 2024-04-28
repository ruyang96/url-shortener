package com.ruyang.urlshortener;

import com.ruyang.urlshortener.controller.UrlShortenerController;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlShortenerApplicationTests {

    @Inject
    private UrlShortenerController urlShortenerController;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertNotNull(urlShortenerController);
    }

}
