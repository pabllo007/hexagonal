package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity;

import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnidadeGestoraEntityTest {

    private static final Long NU_UNIDADE_GESTORA = 10L;
    private static final Integer NU_UNIDADE = 123;
    private static final Integer NU_NATURAL = 456;
    private static final String NO_CAIXA_POSTAL = "CP-001";

    private static final String ENTITY_CLASS_NAME = "UnidadeGestoraEntity";
    private static final String BUILDER_CLASS_NAME = "UnidadeGestoraEntityBuilder";

    @Test
    void gettersSetters_devemPersistirValores() {
        UnidadeGestoraEntity e = new UnidadeGestoraEntity();

        TipoUnidadeGestoraEntity tipo = new TipoUnidadeGestoraEntity();
        GrupoTrabalhoEntity grupo = new GrupoTrabalhoEntity();

        e.setNuUnidadeGestora(NU_UNIDADE_GESTORA);
        e.setNuUnidade(NU_UNIDADE);
        e.setNuNatural(NU_NATURAL);
        e.setIcAtivo(SimNao.S);
        e.setNoCaixaPostal(NO_CAIXA_POSTAL);
        e.setTipoUnidadeGestora(tipo);
        e.setGrupoTrabalho(grupo);

        assertEquals(NU_UNIDADE_GESTORA, e.getNuUnidadeGestora());
        assertEquals(NU_UNIDADE, e.getNuUnidade());
        assertEquals(NU_NATURAL, e.getNuNatural());
        assertEquals(SimNao.S, e.getIcAtivo());
        assertEquals(NO_CAIXA_POSTAL, e.getNoCaixaPostal());
        assertSame(tipo, e.getTipoUnidadeGestora());
        assertSame(grupo, e.getGrupoTrabalho());
    }

    @Test
    void builder_deveExecutarFluxoCompleto_inclusiveToStringDoBuilder() {
        TipoUnidadeGestoraEntity tipo = new TipoUnidadeGestoraEntity();
        GrupoTrabalhoEntity grupo = new GrupoTrabalhoEntity();

        UnidadeGestoraEntity.UnidadeGestoraEntityBuilder builder = UnidadeGestoraEntity.builder()
                .nuUnidadeGestora(NU_UNIDADE_GESTORA)
                .nuUnidade(NU_UNIDADE)
                .nuNatural(NU_NATURAL)
                .icAtivo(SimNao.N)
                .noCaixaPostal(NO_CAIXA_POSTAL)
                .tipoUnidadeGestora(tipo)
                .grupoTrabalho(grupo);

        String builderString = builder.toString();
        assertNotNull(builderString);
        assertFalse(builderString.isBlank());
        assertTrue(builderString.contains(BUILDER_CLASS_NAME));

        UnidadeGestoraEntity e = builder.build();

        assertEquals(NU_UNIDADE_GESTORA, e.getNuUnidadeGestora());
        assertEquals(NU_UNIDADE, e.getNuUnidade());
        assertEquals(NU_NATURAL, e.getNuNatural());
        assertEquals(SimNao.N, e.getIcAtivo());
        assertEquals(NO_CAIXA_POSTAL, e.getNoCaixaPostal());
        assertSame(tipo, e.getTipoUnidadeGestora());
        assertSame(grupo, e.getGrupoTrabalho());

        String entityString = e.toString();
        assertNotNull(entityString);
        assertFalse(entityString.isBlank());
        assertTrue(entityString.contains(ENTITY_CLASS_NAME));
    }

    @Test
    void allArgsConstructor_noArgsConstructor_devemFuncionar() {
        TipoUnidadeGestoraEntity tipo = new TipoUnidadeGestoraEntity();
        GrupoTrabalhoEntity grupo = new GrupoTrabalhoEntity();

        UnidadeGestoraEntity e = new UnidadeGestoraEntity(
                NU_UNIDADE_GESTORA,
                NU_UNIDADE,
                NU_NATURAL,
                SimNao.S,
                NO_CAIXA_POSTAL,
                tipo,
                grupo
        );

        assertEquals(NU_UNIDADE_GESTORA, e.getNuUnidadeGestora());
        assertEquals(NU_UNIDADE, e.getNuUnidade());
        assertEquals(NU_NATURAL, e.getNuNatural());
        assertEquals(SimNao.S, e.getIcAtivo());
        assertEquals(NO_CAIXA_POSTAL, e.getNoCaixaPostal());
        assertSame(tipo, e.getTipoUnidadeGestora());
        assertSame(grupo, e.getGrupoTrabalho());

        UnidadeGestoraEntity vazio = new UnidadeGestoraEntity();
        assertNotNull(vazio);
    }
}