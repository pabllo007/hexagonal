package br.gov.caixa.siaas_api.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NaoEncontradoExceptionTest {

    @Test
    void deveManterCodigoDaBusinessException() {
        NaoEncontradoException ex =
                new NaoEncontradoException("erro.nao.encontrado", 10L);

        assertEquals("erro.nao.encontrado", ex.getCode());
    }
}
