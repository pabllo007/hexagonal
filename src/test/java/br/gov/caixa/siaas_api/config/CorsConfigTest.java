package br.gov.caixa.siaas_api.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.junit.jupiter.api.Assertions.*;

class CorsConfigTest {

    @Test
    void addCorsMappings_naoDeveLancarExcecao() {
        CorsConfig cfg = new CorsConfig();
        CorsRegistry registry = new CorsRegistry();

        assertDoesNotThrow(() -> cfg.addCorsMappings(registry));
    }
}