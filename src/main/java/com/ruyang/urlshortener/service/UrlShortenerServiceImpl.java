package com.ruyang.urlshortener.service;

import com.ruyang.generated.model.HttpHateoasLink;
import com.ruyang.generated.model.HttpVerb;
import com.ruyang.generated.model.UrlShorteningPayload;
import com.ruyang.generated.model.UrlShorteningResponse;
import com.ruyang.generated.model.UrlShorteningResponseLinks;
import com.ruyang.generated.model.User;
import com.ruyang.generated.model.UserCredentials;
import com.ruyang.urlshortener.config.CacheConfig;
import com.ruyang.urlshortener.exception.UrlShortenerErrorCode;
import com.ruyang.urlshortener.exception.UrlShortenerException;
import com.ruyang.urlshortener.repository.UrlRepository;
import com.ruyang.urlshortener.repository.UserRepository;
import com.ruyang.urlshortener.repository.model.UrlDTO;
import com.ruyang.urlshortener.repository.model.UserDTO;
import com.ruyang.urlshortener.utils.Base62Encoder;
import com.ruyang.urlshortener.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private final UserRepository userRepository;
    private final UrlRepository urlRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;

    @Override
    public UrlShorteningResponse createShortenedUrl(UrlShorteningPayload urlShorteningPayload) {
        UrlDTO urlDTO = urlRepository.save(modelMapper.map(urlShorteningPayload, UrlDTO.class));
        String shortUrl = Base62Encoder.encode(urlDTO.getId());
        return new UrlShorteningResponse()
                .shortenedUrl(shortUrl)
                .originalUrl(urlShorteningPayload.getOriginalUrl())
                .links(getLinks(shortUrl));
    }

    @Override
    public String authenticateUser(UserCredentials userCredentials) {
        UserDTO userDTO = convertUserCredentials(userCredentials);
        if (userRepository.findUserByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword()) == null) {
            throw new UrlShortenerException(UrlShortenerErrorCode.URL_SHORTENER_0002);
        }
        return jwtUtil.generateToken(userDTO.getEmail());
    }

    @Override
    @Cacheable(value = CacheConfig.SHORTURL_CACHE, key = "#urlId")
    public String getOriginalUrl(String urlId) {
        Long id = Base62Encoder.decode(urlId);
        UrlDTO urlDTO = urlRepository.findById(id).orElseThrow(() -> new UrlShortenerException(UrlShortenerErrorCode.URL_SHORTENER_0007));
        return urlDTO.getOriginalUrl();
    }

    @Override
    public User registerUser(UserCredentials userCredentials) {
        UserDTO userDTO = convertUserCredentials(userCredentials);
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new UrlShortenerException(UrlShortenerErrorCode.URL_SHORTENER_0001);
        }
        UserDTO createdUser = userRepository.save(userDTO);
        return modelMapper.map(createdUser, User.class);
    }

    private UserDTO convertUserCredentials(UserCredentials userCredentials) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(userCredentials.getEmail());
        userDTO.setPassword(String.valueOf(userCredentials.getPassword().hashCode()));
        return userDTO;
    }

    private UrlShorteningResponseLinks getLinks(String shortUrl) {
        UrlShorteningResponseLinks urlShorteningResponseLinks = new UrlShorteningResponseLinks();
        HttpHateoasLink httpHateoasLink = new HttpHateoasLink()
                .addVerbsItem(HttpVerb.GET)
                .href(String.format("/api/url/%s", shortUrl));
        urlShorteningResponseLinks.setGetUrl(httpHateoasLink);
        return urlShorteningResponseLinks;
    }
}
