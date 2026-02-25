package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrupoTrabalhoEntityTest {

    private static final Integer ID_1 = 1;
    private static final String NOME_1 = "CEPTI SEGURANCA BSB";

    private static final String ENTITY_CLASS_NAME = "GrupoTrabalhoEntity";
    private static final String BUILDER_CLASS_NAME = "GrupoTrabalhoEntityBuilder";

    @Test
    void constantesDevemTerValoresEsperados() {
        assertEquals(1, GrupoTrabalhoEntity.CEPTI_SEGURANCA_BSB);
        assertEquals(2, GrupoTrabalhoEntity.CEPTI_SEGURANCA_RJ);
        assertEquals(3, GrupoTrabalhoEntity.CEPTI_SEGURANCA_SP);
        assertEquals(4, GrupoTrabalhoEntity.CETEC_PEDES);
    }

    @Test
    void gettersSetters_devemPersistirValores() {
        GrupoTrabalhoEntity e = new GrupoTrabalhoEntity();

        e.setNuGrupoTrabalho(ID_1);
        e.setNoGrupoTrabalho(NOME_1);

        assertEquals(ID_1, e.getNuGrupoTrabalho());
        assertEquals(NOME_1, e.getNoGrupoTrabalho());
    }

    @Test
    void builder_deveExecutarFluxoCompleto_inclusiveToStringDoBuilder() {
        GrupoTrabalhoEntity.GrupoTrabalhoEntityBuilder builder = GrupoTrabalhoEntity.builder()
                .nuGrupoTrabalho(ID_1)
                .noGrupoTrabalho(NOME_1);

        String builderString = builder.toString();
        assertNotNull(builderString);
        assertFalse(builderString.isBlank());
        assertTrue(builderString.contains(BUILDER_CLASS_NAME));

        GrupoTrabalhoEntity e = builder.build();

        assertEquals(ID_1, e.getNuGrupoTrabalho());
        assertEquals(NOME_1, e.getNoGrupoTrabalho());

        String entityString = e.toString();
        assertNotNull(entityString);
        assertFalse(entityString.isBlank());
        assertTrue(entityString.contains(ENTITY_CLASS_NAME));
    }

    @Test
    void allArgsConstructor_noArgsConstructor_devemFuncionar() {
        GrupoTrabalhoEntity e = new GrupoTrabalhoEntity(ID_1, NOME_1);

        assertEquals(ID_1, e.getNuGrupoTrabalho());
        assertEquals(NOME_1, e.getNoGrupoTrabalho());

        GrupoTrabalhoEntity vazio = new GrupoTrabalhoEntity();
        assertNotNull(vazio);
    }
}