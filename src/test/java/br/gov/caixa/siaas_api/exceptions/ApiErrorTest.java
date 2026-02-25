package br.gov.caixa.siaas_api.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiErrorTest {

    @Test
    void deveExporCamposDoRecord() {
        ApiError erro = new ApiError("codigo", "mensagem", 400);

        assertEquals("codigo", erro.code());
        assertEquals("mensagem", erro.message());
        assertEquals(400, erro.status());
    }
}
