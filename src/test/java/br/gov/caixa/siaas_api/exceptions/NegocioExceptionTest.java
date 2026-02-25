package br.gov.caixa.siaas_api.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NegocioExceptionTest {

    @Test
    void deveExporCodigoEArgumentos() {
        NegocioException ex =
                new NegocioException("erro.codigo", 1L, "abc");

        assertEquals("erro.codigo", ex.getCode());
        assertArrayEquals(new Object[]{1L, "abc"}, ex.getArgs());
    }
}
