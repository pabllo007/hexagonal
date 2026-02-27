package br.gov.caixa.siaas_api.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(
            @Value("${app.cache.grupo-trabalho.ttl:P1D}") Duration grupoTrabalhoTtl,
            @Value("${app.cache.tipo-unidade-gestora.ttl:P1D}") Duration tipoUnidadeGestoraTtl,
            @Value("${app.cache.maximum-size:100}") long maximumSize
    ) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setAllowNullValues(false);

        cacheManager.registerCustomCache(
                CacheNames.GRUPO_TRABALHO,
                Caffeine.newBuilder()
                        .expireAfterWrite(grupoTrabalhoTtl)
                        .maximumSize(maximumSize)
                        .build()
        );

        cacheManager.registerCustomCache(
                CacheNames.TIPO_UNIDADE_GESTORA,
                Caffeine.newBuilder()
                        .expireAfterWrite(tipoUnidadeGestoraTtl)
                        .maximumSize(maximumSize)
                        .build()
        );

        return cacheManager;
    }
}
