package br.gov.caixa.siaas_api.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JpaRepositoriesConfigTest {

    @Test
    void deveInstanciar() {
        JpaRepositoriesConfig cfg = new JpaRepositoriesConfig();
        assertNotNull(cfg);
    }
}