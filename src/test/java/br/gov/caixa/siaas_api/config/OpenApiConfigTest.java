package br.gov.caixa.siaas_api.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigTest {

    @Test
    void deveInstanciar() {
        OpenApiConfig cfg = new OpenApiConfig();
        assertNotNull(cfg);
    }
}