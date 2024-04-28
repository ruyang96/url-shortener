package com.ruyang.urlshortener.utils;

import com.ruyang.urlshortener.exception.UrlShortenerErrorCode;
import com.ruyang.urlshortener.exception.UrlShortenerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Base62EncoderTest {

    @Test
    void testEncoder(){
        String expected = "Ib2";
        String result = Base62Encoder.encode(10000);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testEncoderWithLongMaxValue(){
        String expected = "7m85Y0n8LzA";
        String result = Base62Encoder.encode(Long.MAX_VALUE);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testEncoderWithNegativeValue(){
        UrlShortenerException exception = Assertions.assertThrows(UrlShortenerException.class, () -> Base62Encoder.encode(-1000));
        Assertions.assertEquals(UrlShortenerErrorCode.URL_SHORTENER_0006, exception.getErrorCode());
    }

    @Test
    void testDecoder(){
        long result = Base62Encoder.decode("Ib2");
        long expected = 10000;
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testDecoderWithMaxValue(){
        long result = Base62Encoder.decode("7m85Y0n8LzA");
        long expected = Long.MAX_VALUE;
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testDecoderWithInvalidValue(){
        UrlShortenerException exception = Assertions.assertThrows(UrlShortenerException.class, () -> Base62Encoder.decode("??!!"));
        Assertions.assertEquals(UrlShortenerErrorCode.URL_SHORTENER_0006, exception.getErrorCode());
    }

}
