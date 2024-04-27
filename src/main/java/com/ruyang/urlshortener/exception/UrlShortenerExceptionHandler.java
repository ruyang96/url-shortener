package com.ruyang.urlshortener.exception;

import com.ruyang.generated.model.Error;
import com.ruyang.urlshortener.controller.UrlShortenerController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackageClasses = UrlShortenerController.class)
public class UrlShortenerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> exceptionHanlder(Exception ex, WebRequest webRequest){
        UrlShortenerErrorCode errorCode = UrlShortenerErrorCode.URL_SHORTENER_9999;
        if(ex instanceof UrlShortenerException urlShortenerException){
            errorCode = urlShortenerException.getErrorCode();
        } else {
            logger.error(String.format("Unknow error happened. %s", ex.getMessage()));
        }
        return super.handleExceptionInternal(ex, createErrorfromErrorCode(errorCode), new HttpHeaders(), errorCode.getStatus(), webRequest);
    }

    private Error createErrorfromErrorCode(UrlShortenerErrorCode errorCode){
        return new Error()
                .code(errorCode.getCode())
                .status(errorCode.getStatus().value())
                .message(errorCode.getMessage());
    }
}
