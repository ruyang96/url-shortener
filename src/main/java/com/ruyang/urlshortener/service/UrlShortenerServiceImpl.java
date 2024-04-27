package com.ruyang.urlshortener.service;

import com.ruyang.generated.model.UrlShorteningResponse;
import com.ruyang.generated.model.User;
import com.ruyang.generated.model.UserCredentials;
import com.ruyang.urlshortener.utils.JwtUtil;
import com.ruyang.urlshortener.exception.UrlShortenerErrorCode;
import com.ruyang.urlshortener.exception.UrlShortenerException;
import com.ruyang.urlshortener.repository.UserRepository;
import com.ruyang.urlshortener.repository.model.UserDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UrlShorteningResponse createShortenedUrl(String originalUrl) {
        return null;
    }

    @Override
    public String authenticateUser(UserCredentials userCredentials) {
        UserDTO userDTO = convertUserCredentials(userCredentials);
        if(userRepository.findUserByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword())==null){
            throw new UrlShortenerException(UrlShortenerErrorCode.URL_SHORTENER_0002);
        }
        return JwtUtil.generateToken(userDTO.getEmail());
    }

    @Override
    public void getOriginalUrl() {

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
}
