package br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseAutenticacaoResponseTest {

    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_NAME = "Base Autenticação";
    private static final String SIM = "S";
    private static final String NAO = "N";

    @Test
    void deveCriarObjetoCorretamente() {
        BaseAutenticacaoResponse response = new BaseAutenticacaoResponse(DEFAULT_ID, DEFAULT_NAME, SIM, NAO, SIM);

        assertEquals(DEFAULT_ID, response.nuBaseAutenticacao());
        assertEquals(DEFAULT_NAME, response.noBaseAutenticacao());
        assertEquals(SIM, response.icRecurso());
        assertEquals(NAO, response.icSinav());
        assertEquals(SIM, response.icAtivo());
    }

    @Test
    void deveGerarEqualsCorretamente() {
        BaseAutenticacaoResponse r1 = new BaseAutenticacaoResponse(DEFAULT_ID, DEFAULT_NAME, SIM, NAO, SIM);
        BaseAutenticacaoResponse r2 = new BaseAutenticacaoResponse(DEFAULT_ID, DEFAULT_NAME, SIM, NAO, SIM);

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void devePermitirValoresNulos() {
        BaseAutenticacaoResponse response = new BaseAutenticacaoResponse(null, null, null, null, null);

        assertNull(response.nuBaseAutenticacao());
        assertNull(response.noBaseAutenticacao());
        assertNull(response.icRecurso());
        assertNull(response.icSinav());
        assertNull(response.icAtivo());
    }
}