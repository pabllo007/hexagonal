package br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnidadeGestoraResponseTest {

    private static final Long NU_UNIDADE_GESTORA = 1L;
    private static final String NO_UNIDADE_GESTORA = "Unidade 001";
    private static final String TIPO_UNIDADE_GESTORA = "Desenvolvimento";

    @Test
    void deveCriarRecord_eExporComponentesCorretamente() {
        UnidadeGestoraResponse r = new UnidadeGestoraResponse(
                NU_UNIDADE_GESTORA,
                NO_UNIDADE_GESTORA,
                TIPO_UNIDADE_GESTORA,
                true
        );

        assertEquals(NU_UNIDADE_GESTORA, r.nuUnidadeGestora());
        assertEquals(NO_UNIDADE_GESTORA, r.noUnidadeGestora());
        assertEquals(TIPO_UNIDADE_GESTORA, r.tipoUnidadeGestora());
        assertTrue(r.icAtivo());
    }

    @Test
    void deveGarantirEqualsHashCodeToStringDoRecord() {
        UnidadeGestoraResponse a = new UnidadeGestoraResponse(
                NU_UNIDADE_GESTORA,
                NO_UNIDADE_GESTORA,
                TIPO_UNIDADE_GESTORA,
                true
        );
        UnidadeGestoraResponse b = new UnidadeGestoraResponse(
                NU_UNIDADE_GESTORA,
                NO_UNIDADE_GESTORA,
                TIPO_UNIDADE_GESTORA,
                true
        );
        UnidadeGestoraResponse c = new UnidadeGestoraResponse(
                NU_UNIDADE_GESTORA,
                NO_UNIDADE_GESTORA,
                TIPO_UNIDADE_GESTORA,
                false
        );

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);

        String s = a.toString();
        assertNotNull(s);
        assertFalse(s.isBlank());
        assertTrue(s.contains("UnidadeGestoraResponse"));
    }
}