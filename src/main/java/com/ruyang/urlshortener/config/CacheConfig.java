package com.ruyang.urlshortener.config;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {
    public static final String SHORTURL_CACHE = "shortUrlCache";

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cacheManager ->
                cacheManager.createCache(SHORTURL_CACHE, Eh107Configuration.fromEhcacheCacheConfiguration(
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
                                        ResourcePoolsBuilder.heap(this.getHeapSizeInBytes(60)))
                                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(120)))
                                .build()));
    }

    private long getHeapSizeInBytes(double percentOfHeapSize) {
        long heapSize = Runtime.getRuntime().maxMemory();
        return Math.round(heapSize * percentOfHeapSize / 100);
    }
}
