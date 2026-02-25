package br.gov.caixa.siaas_api.domain.shared.enums;

import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimNaoTest {

    @Test
    void of_deveRetornarS_quandoTrue() {
        assertEquals(SimNao.S, SimNao.of(true));
    }

    @Test
    void of_deveRetornarN_quandoFalseOuNull() {
        assertEquals(SimNao.N, SimNao.of(false));
        assertEquals(SimNao.N, SimNao.of(null));
    }

    @Test
    void fromDatabase_deveAceitarSEN_comEspacos_eMinusculas() {
        assertEquals(SimNao.S, SimNao.fromDatabase("S"));
        assertEquals(SimNao.S, SimNao.fromDatabase(" s "));
        assertEquals(SimNao.N, SimNao.fromDatabase("N"));
        assertEquals(SimNao.N, SimNao.fromDatabase(" n "));
    }

    @Test
    void fromDatabase_deveRetornarNull_quandoNull() {
        assertNull(SimNao.fromDatabase(null));
    }

    @Test
    void fromDatabase_deveLancarException_quandoValorInvalido() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> SimNao.fromDatabase("X")
        );
        assertTrue(ex.getMessage().contains("Valor inválido"));
    }

    @Test
    void toDatabase_deveRetornarNomeDoEnum() {
        assertEquals("S", SimNao.S.toDatabase());
        assertEquals("N", SimNao.N.toDatabase());
    }

    @Test
    void isSim_isNao_devemRefletirValor() {
        assertTrue(SimNao.S.isSim());
        assertFalse(SimNao.S.isNao());

        assertFalse(SimNao.N.isSim());
        assertTrue(SimNao.N.isNao());
    }
}