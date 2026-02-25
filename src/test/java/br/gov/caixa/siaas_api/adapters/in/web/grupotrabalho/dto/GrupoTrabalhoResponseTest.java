package br.gov.caixa.siaas_api.adapters.in.web.grupotrabalho.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrupoTrabalhoResponseTest {

    private static final Integer NU_GRUPO_TRABALHO = 1;
    private static final String NO_GRUPO_TRABALHO = "Grupo Trabalho 001";
    private static final String RECORD_NAME = "GrupoTrabalhoResponse";

    @Test
    void deveCriarRecord_eExporComponentesCorretamente() {
        GrupoTrabalhoResponse r = new GrupoTrabalhoResponse(NU_GRUPO_TRABALHO, NO_GRUPO_TRABALHO);

        assertEquals(NU_GRUPO_TRABALHO, r.nuGrupoTrabalho());
        assertEquals(NO_GRUPO_TRABALHO, r.noGrupoTrabalho());
    }

    @Test
    void deveGarantirEqualsHashCodeToStringDoRecord() {
        GrupoTrabalhoResponse a = new GrupoTrabalhoResponse(NU_GRUPO_TRABALHO, NO_GRUPO_TRABALHO);
        GrupoTrabalhoResponse b = new GrupoTrabalhoResponse(NU_GRUPO_TRABALHO, NO_GRUPO_TRABALHO);
        GrupoTrabalhoResponse c = new GrupoTrabalhoResponse(NU_GRUPO_TRABALHO, NO_GRUPO_TRABALHO + "X");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);

        String s = a.toString();
        assertNotNull(s);
        assertFalse(s.isBlank());
        assertTrue(s.contains(RECORD_NAME));
    }
}