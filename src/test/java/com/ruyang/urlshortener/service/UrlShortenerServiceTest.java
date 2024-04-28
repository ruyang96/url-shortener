package com.ruyang.urlshortener.service;

import com.ruyang.generated.model.UrlShorteningPayload;
import com.ruyang.generated.model.User;
import com.ruyang.generated.model.UserCredentials;
import com.ruyang.urlshortener.exception.UrlShortenerErrorCode;
import com.ruyang.urlshortener.exception.UrlShortenerException;
import com.ruyang.urlshortener.repository.UrlRepository;
import com.ruyang.urlshortener.repository.UserRepository;
import com.ruyang.urlshortener.repository.model.UrlDTO;
import com.ruyang.urlshortener.repository.model.UserDTO;
import com.ruyang.urlshortener.utils.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceTest {
    private static final String EMAIL = "test@email.com";
    private static final String PASS = "pass";
    @Mock
    private UserRepository userRepository;
    @Mock
    private UrlRepository urlRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private JwtUtil jwtUtil;
    @InjectMocks
    private UrlShortenerServiceImpl urlShortenerService;

    @BeforeEach
    void setup() {
        reset(userRepository, urlRepository, modelMapper, jwtUtil);
    }

    @Test
    void testRegisterUser() {
        when(userRepository.findByEmail(any())).thenReturn(null);
        when(userRepository.save(any())).thenReturn(new UserDTO());
        when(modelMapper.map(any(), any())).thenReturn(new User());

        urlShortenerService.registerUser(mockUserCredentials());

        verify(userRepository, times(1)).findByEmail(any());
        verify(userRepository, times(1)).save(any());
        verify(modelMapper, times(1)).map(any(), any());
    }

    @Test
    void testRegisterUserWhenUserAlreadyExists() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(new UserDTO());
        var userCredentials = mockUserCredentials();

        UrlShortenerException exception = Assertions.assertThrows(UrlShortenerException.class, () -> urlShortenerService.registerUser(userCredentials));

        Assertions.assertEquals(UrlShortenerErrorCode.URL_SHORTENER_0001, exception.getErrorCode());
        verify(userRepository, times(1)).findByEmail(EMAIL);
    }

    @Test
    void testAuthenticateUser() {
        String hashedPass = String.valueOf(PASS.hashCode());
        when(userRepository.findUserByEmailAndPassword(EMAIL, hashedPass)).thenReturn(new UserDTO());
        String generateToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJydXlhbm";
        when(jwtUtil.generateToken(EMAIL)).thenReturn(generateToken);

        String token = urlShortenerService.authenticateUser(mockUserCredentials());

        Assertions.assertEquals(generateToken, token);
        verify(userRepository, times(1)).findUserByEmailAndPassword(EMAIL, hashedPass);
        verify(jwtUtil, times(1)).generateToken(EMAIL);
    }

    @Test
    void testAuthenticateUserWhenUserNotExist() {
        String hashedPass = String.valueOf(PASS.hashCode());
        when(userRepository.findUserByEmailAndPassword(EMAIL, hashedPass)).thenReturn(null);
        var userCredentials = mockUserCredentials();

        UrlShortenerException exception = Assertions.assertThrows(UrlShortenerException.class, () -> urlShortenerService.authenticateUser(userCredentials));

        Assertions.assertEquals(UrlShortenerErrorCode.URL_SHORTENER_0002, exception.getErrorCode());
        verify(userRepository, times(1)).findUserByEmailAndPassword(EMAIL, hashedPass);
    }

    @Test
    void testCreateShortenedUrl() {
        when(modelMapper.map(any(), any())).thenReturn(mockUrlDTO());
        when(urlRepository.save(any())).thenReturn(mockUrlDTO());

        var reponse = urlShortenerService.createShortenedUrl(new UrlShorteningPayload().originalUrl("google.com"));

        verify(urlRepository, times(1)).save(any());
        verify(modelMapper, times(1)).map(any(), any());
        Assertions.assertNotNull(reponse);
        Assertions.assertEquals("google.com", reponse.getOriginalUrl());
        Assertions.assertEquals("8G", reponse.getShortenedUrl());
        Assertions.assertNotNull(reponse.getLinks().getGetUrl());
        Assertions.assertEquals("/api/url/8G", reponse.getLinks().getGetUrl().getHref());
    }

    @Test
    void testCreateShortenedUrlWhenUrlEmpty() {
        var payload = new UrlShorteningPayload().originalUrl("");
        UrlShortenerException exception = Assertions.assertThrows(UrlShortenerException.class, () -> urlShortenerService.createShortenedUrl(payload));

        Assertions.assertEquals(UrlShortenerErrorCode.URL_SHORTENER_0008, exception.getErrorCode());
    }

    @Test
    void testGetOriginalUrl() {
        when(urlRepository.findById(1000L)).thenReturn(Optional.of(mockUrlDTO()));

        var reponse = urlShortenerService.getOriginalUrl("8G");

        Assertions.assertNotNull(reponse);
        Assertions.assertEquals("google.com", reponse);
        verify(urlRepository, times(1)).findById(1000L);
    }

    @Test
    void testGetOriginalUrlWhenUrlNotExist() {
        when(urlRepository.findById(1000L)).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(UrlShortenerException.class, () -> urlShortenerService.getOriginalUrl("8G"));

        Assertions.assertEquals(UrlShortenerErrorCode.URL_SHORTENER_0007, exception.getErrorCode());
        verify(urlRepository, times(1)).findById(1000L);
    }

    private UserCredentials mockUserCredentials() {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setEmail(EMAIL);
        userCredentials.setPassword(PASS);
        return userCredentials;
    }

    private UrlDTO mockUrlDTO() {
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.setOriginalUrl("google.com");
        urlDTO.setId(1000L);
        return urlDTO;
    }
}
