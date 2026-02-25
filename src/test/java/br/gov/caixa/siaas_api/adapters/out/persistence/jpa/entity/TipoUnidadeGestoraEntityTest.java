package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity;

import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoUnidadeGestoraEntityTest {

    private static final Integer ID_1 = 1;
    private static final String NOME_1 = "DESENVOLVIMENTO";

    private static final String ENTITY_CLASS_NAME = "TipoUnidadeGestoraEntity";
    private static final String BUILDER_CLASS_NAME = "TipoUnidadeGestoraEntityBuilder";

    @Test
    void constantesDevemTerValoresEsperados() {
        assertEquals(1, TipoUnidadeGestoraEntity.DESENVOLVIMENTO);
        assertEquals(2, TipoUnidadeGestoraEntity.NEGOCIO);
        assertEquals(3, TipoUnidadeGestoraEntity.TI);
        assertEquals(4, TipoUnidadeGestoraEntity.SEGURANCA);
        assertEquals(5, TipoUnidadeGestoraEntity.PRODUCAO);
    }

    @Test
    void gettersSetters_devemPersistirValores() {
        TipoUnidadeGestoraEntity e = new TipoUnidadeGestoraEntity();

        e.setNuTipoUnidadeGestora(ID_1);
        e.setNoTipoUnidadeGestora(NOME_1);
        e.setIcGrupoTrabalho(SimNao.S);

        assertEquals(ID_1, e.getNuTipoUnidadeGestora());
        assertEquals(NOME_1, e.getNoTipoUnidadeGestora());
        assertEquals(SimNao.S, e.getIcGrupoTrabalho());
    }

    @Test
    void builder_deveExecutarFluxoCompleto_inclusiveToStringDoBuilder() {
        TipoUnidadeGestoraEntity.TipoUnidadeGestoraEntityBuilder builder = TipoUnidadeGestoraEntity.builder()
                .nuTipoUnidadeGestora(ID_1)
                .noTipoUnidadeGestora(NOME_1)
                .icGrupoTrabalho(SimNao.N);

        String builderString = builder.toString();
        assertNotNull(builderString);
        assertFalse(builderString.isBlank());
        assertTrue(builderString.contains(BUILDER_CLASS_NAME));

        TipoUnidadeGestoraEntity e = builder.build();

        assertEquals(ID_1, e.getNuTipoUnidadeGestora());
        assertEquals(NOME_1, e.getNoTipoUnidadeGestora());
        assertEquals(SimNao.N, e.getIcGrupoTrabalho());

        String entityString = e.toString();
        assertNotNull(entityString);
        assertFalse(entityString.isBlank());
        assertTrue(entityString.contains(ENTITY_CLASS_NAME));
    }

    @Test
    void allArgsConstructor_noArgsConstructor_devemFuncionar() {
        TipoUnidadeGestoraEntity e = new TipoUnidadeGestoraEntity(ID_1, NOME_1, SimNao.S);

        assertEquals(ID_1, e.getNuTipoUnidadeGestora());
        assertEquals(NOME_1, e.getNoTipoUnidadeGestora());
        assertEquals(SimNao.S, e.getIcGrupoTrabalho());

        TipoUnidadeGestoraEntity vazio = new TipoUnidadeGestoraEntity();
        assertNotNull(vazio);
    }
}