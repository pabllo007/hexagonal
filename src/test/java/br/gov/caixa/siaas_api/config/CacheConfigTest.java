package br.gov.caixa.siaas_api.config;

import org.junit.jupiter.api.Test;
import org.springframework.cache.CacheManager;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class CacheConfigTest {

    @Test
    void cacheManager_deveCriarCachesConfigurados() {
        CacheConfig cacheConfig = new CacheConfig();

        CacheManager cacheManager = cacheConfig.cacheManager(Duration.ofDays(1), Duration.ofDays(2), 100);

        assertNotNull(cacheManager);
        assertNotNull(cacheManager.getCache(CacheNames.GRUPO_TRABALHO));
        assertNotNull(cacheManager.getCache(CacheNames.TIPO_UNIDADE_GESTORA));
    }
}
