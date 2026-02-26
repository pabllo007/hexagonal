package br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnidadeGestoraResponseTest {

    private static final Long NU_UNIDADE_GESTORA = 1L;
    private static final String NO_UNIDADE_GESTORA = "Unidade 001";
    private static final Integer NU_UNIDADE = 1;
    private static final Integer NU_TIPO_UNIDADE_GESTORA = 1;
    private static final String TIPO_UNIDADE_GESTORA = "Desenvolvimento";
    private static final Integer NU_GRUPO_TRABALHO = 1;
    private static final String NO_GRUPO_TRABALHO = "CEDES";
    private static final String NO_CAIXA_POSTAL = "cedes@xpto.gov.br";

    @Test
    void deveCriarRecord_eExporComponentesCorretamente() {
        UnidadeGestoraResponse r = new UnidadeGestoraResponse(
                NU_UNIDADE_GESTORA,
                NO_UNIDADE_GESTORA,
                true,
                NU_TIPO_UNIDADE_GESTORA,
                NU_UNIDADE,
                TIPO_UNIDADE_GESTORA,
                NU_TIPO_UNIDADE_GESTORA,
                NO_GRUPO_TRABALHO,
                NO_CAIXA_POSTAL
        );

        assertEquals(NU_UNIDADE_GESTORA, r.nuUnidadeGestora());
        assertEquals(NO_UNIDADE_GESTORA, r.noUnidadeGestora());
        assertEquals(TIPO_UNIDADE_GESTORA, r.notipoUnidadeGestora());
        assertTrue(r.icAtivo());
    }

    @Test
    void deveGarantirEqualsHashCodeToStringDoRecord() {
        UnidadeGestoraResponse a = new UnidadeGestoraResponse(
                NU_UNIDADE_GESTORA,
                NO_UNIDADE_GESTORA,
                true,
                NU_TIPO_UNIDADE_GESTORA,
                NU_UNIDADE,
                TIPO_UNIDADE_GESTORA,
                NU_TIPO_UNIDADE_GESTORA,
                NO_GRUPO_TRABALHO,
                NO_CAIXA_POSTAL
        );
        UnidadeGestoraResponse b = new UnidadeGestoraResponse(
                NU_UNIDADE_GESTORA,
                NO_UNIDADE_GESTORA,
                true,
                NU_TIPO_UNIDADE_GESTORA,
                NU_UNIDADE,
                TIPO_UNIDADE_GESTORA,
                NU_TIPO_UNIDADE_GESTORA,
                NO_GRUPO_TRABALHO,
                NO_CAIXA_POSTAL
        );
        UnidadeGestoraResponse c = new UnidadeGestoraResponse(
                NU_UNIDADE_GESTORA,
                NO_UNIDADE_GESTORA,
                false,
                NU_TIPO_UNIDADE_GESTORA,
                NU_UNIDADE,
                TIPO_UNIDADE_GESTORA,
                NU_TIPO_UNIDADE_GESTORA,
                NO_GRUPO_TRABALHO,
                NO_CAIXA_POSTAL
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