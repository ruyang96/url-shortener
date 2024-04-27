package com.ruyang.urlshortener.repository;

import com.ruyang.urlshortener.repository.model.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDTO, Long> {
}
