package br.gov.caixa.siaas_api.adapters.in.web.tipounidadegestora.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoUnidadeGestoraResponseTest {

    private static final Integer NU_TIPO = 1;
    private static final String NO_TIPO = "Tipo Unidade Gestora 001";
    private static final String RECORD_NAME = "TipoUnidadeGestoraResponse";

    @Test
    void deveCriarRecord_eExporComponentesCorretamente() {
        TipoUnidadeGestoraResponse r = new TipoUnidadeGestoraResponse(NU_TIPO, NO_TIPO);

        assertEquals(NU_TIPO, r.nuTipoUnidadeGestora());
        assertEquals(NO_TIPO, r.noTipoUnidadeGestora());
    }

    @Test
    void deveGarantirEqualsHashCodeToStringDoRecord() {
        TipoUnidadeGestoraResponse a = new TipoUnidadeGestoraResponse(NU_TIPO, NO_TIPO);
        TipoUnidadeGestoraResponse b = new TipoUnidadeGestoraResponse(NU_TIPO, NO_TIPO);
        TipoUnidadeGestoraResponse c = new TipoUnidadeGestoraResponse(NU_TIPO, NO_TIPO + "X");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);

        String s = a.toString();
        assertNotNull(s);
        assertFalse(s.isBlank());
        assertTrue(s.contains(RECORD_NAME));
    }
}