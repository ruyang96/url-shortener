package com.ruyang.urlshortener.repository;

import com.ruyang.urlshortener.repository.model.UrlDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlDTO, Long> {
}
