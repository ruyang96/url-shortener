package com.ruyang.urlshortener.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruyang.urlshortener.exception.UrlShortenerErrorCode;
import com.ruyang.urlshortener.exception.UrlShortenerException;
import com.ruyang.urlshortener.utils.JwtUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

import static com.ruyang.urlshortener.utils.ConverterUtil.createErrorfromErrorCode;

public class AuthFilter implements Filter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String token = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null) {
            handleAuthorizationError((HttpServletResponse) servletResponse, UrlShortenerErrorCode.URL_SHORTENER_0004);
            return;
        }
        try {
            JwtUtil.validateToken(token);
        } catch (Exception e) {
            UrlShortenerException exception = (UrlShortenerException) e;
            handleAuthorizationError((HttpServletResponse) servletResponse, exception.getErrorCode());
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void handleAuthorizationError(HttpServletResponse response, UrlShortenerErrorCode urlShortenerErrorCode) throws IOException {
        response.setContentType("application/json");
        response.setStatus(urlShortenerErrorCode.getStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString(createErrorfromErrorCode(urlShortenerErrorCode)));
    }
}
