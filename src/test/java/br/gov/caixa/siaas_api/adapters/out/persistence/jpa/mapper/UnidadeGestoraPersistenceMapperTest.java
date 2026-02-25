package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.GrupoTrabalhoEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.TipoUnidadeGestoraEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.UnidadeGestoraEntity;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnidadeGestoraPersistenceMapperTest {

    private static final Long NU_UNIDADE_GESTORA = 10L;
    private static final Integer NU_UNIDADE = 123;
    private static final Integer NU_NATURAL = 456;
    private static final String NO_CAIXA_POSTAL = "CP-001";

    private static final Integer NU_TIPO = 5;
    private static final String NO_TIPO = "PRODUCAO";

    private static final Integer NU_GRUPO = 1;
    private static final String NO_GRUPO = "CEPTI SEGURANCA BSB";

    @Test
    void toDomain_deveRetornarNull_quandoEntityNull() {
        UnidadeGestoraPersistenceMapper mapper = new UnidadeGestoraPersistenceMapper();

        UnidadeGestora result = mapper.toDomain(null);

        assertNull(result);
    }

    @Test
    void toDomain_deveMapearCamposBasicos_quandoSemTipoESemGrupo() {
        UnidadeGestoraPersistenceMapper mapper = new UnidadeGestoraPersistenceMapper();

        UnidadeGestoraEntity e = new UnidadeGestoraEntity();
        e.setNuUnidadeGestora(NU_UNIDADE_GESTORA);
        e.setNuUnidade(NU_UNIDADE);
        e.setNuNatural(NU_NATURAL);
        e.setNoCaixaPostal(NO_CAIXA_POSTAL);
        e.setTipoUnidadeGestora(null);
        e.setGrupoTrabalho(null);

        UnidadeGestora d = mapper.toDomain(e);

        assertNotNull(d);
        assertEquals(NU_UNIDADE_GESTORA, d.getNuUnidadeGestora());
        assertEquals(NU_UNIDADE, d.getNuUnidade());
        assertEquals(NU_NATURAL, d.getNuNatural());
        assertEquals(NO_CAIXA_POSTAL, d.getNoCaixaPostal());
        assertNull(d.getTipo());
        assertNull(d.getGrupoTrabalho());
    }

    @Test
    void toDomain_deveMapearTipo_quandoTipoExistir_ePermiteGrupoTrabalhoTrue_quandoSim() {
        UnidadeGestoraPersistenceMapper mapper = new UnidadeGestoraPersistenceMapper();

        TipoUnidadeGestoraEntity tipo = new TipoUnidadeGestoraEntity();
        tipo.setNuTipoUnidadeGestora(NU_TIPO);
        tipo.setNoTipoUnidadeGestora(NO_TIPO);
        tipo.setIcGrupoTrabalho(SimNao.S);

        UnidadeGestoraEntity e = new UnidadeGestoraEntity();
        e.setNuUnidadeGestora(NU_UNIDADE_GESTORA);
        e.setNuUnidade(NU_UNIDADE);
        e.setNuNatural(NU_NATURAL);
        e.setNoCaixaPostal(NO_CAIXA_POSTAL);
        e.setTipoUnidadeGestora(tipo);
        e.setGrupoTrabalho(null);

        UnidadeGestora d = mapper.toDomain(e);

        assertNotNull(d);
        assertNotNull(d.getTipo());
        assertEquals(NU_TIPO, d.getTipo().getNuTipoUnidadeGestora());
        assertEquals(NO_TIPO, d.getTipo().getNoTipoUnidadeGestora());
        assertTrue(d.getTipo().isPermiteGrupoTrabalho());

        assertNull(d.getGrupoTrabalho());
    }

    @Test
    void toDomain_deveMapearTipo_quandoTipoExistir_ePermiteGrupoTrabalhoFalse_quandoNao() {
        UnidadeGestoraPersistenceMapper mapper = new UnidadeGestoraPersistenceMapper();

        TipoUnidadeGestoraEntity tipo = new TipoUnidadeGestoraEntity();
        tipo.setNuTipoUnidadeGestora(NU_TIPO);
        tipo.setNoTipoUnidadeGestora(NO_TIPO);
        tipo.setIcGrupoTrabalho(SimNao.N);

        UnidadeGestoraEntity e = new UnidadeGestoraEntity();
        e.setNuUnidadeGestora(NU_UNIDADE_GESTORA);
        e.setNuUnidade(NU_UNIDADE);
        e.setNuNatural(NU_NATURAL);
        e.setNoCaixaPostal(NO_CAIXA_POSTAL);
        e.setTipoUnidadeGestora(tipo);
        e.setGrupoTrabalho(null);

        UnidadeGestora d = mapper.toDomain(e);

        assertNotNull(d);
        assertNotNull(d.getTipo());
        assertEquals(NU_TIPO, d.getTipo().getNuTipoUnidadeGestora());
        assertEquals(NO_TIPO, d.getTipo().getNoTipoUnidadeGestora());
        assertFalse(d.getTipo().isPermiteGrupoTrabalho());

        assertNull(d.getGrupoTrabalho());
    }

    @Test
    void toDomain_deveMapearTipo_quandoIcGrupoTrabalhoNull_ePermiteGrupoTrabalhoFalse() {
        UnidadeGestoraPersistenceMapper mapper = new UnidadeGestoraPersistenceMapper();

        TipoUnidadeGestoraEntity tipo = new TipoUnidadeGestoraEntity();
        tipo.setNuTipoUnidadeGestora(NU_TIPO);
        tipo.setNoTipoUnidadeGestora(NO_TIPO);
        tipo.setIcGrupoTrabalho(null);

        UnidadeGestoraEntity e = new UnidadeGestoraEntity();
        e.setNuUnidadeGestora(NU_UNIDADE_GESTORA);
        e.setNuUnidade(NU_UNIDADE);
        e.setNuNatural(NU_NATURAL);
        e.setNoCaixaPostal(NO_CAIXA_POSTAL);
        e.setTipoUnidadeGestora(tipo);
        e.setGrupoTrabalho(null);

        UnidadeGestora d = mapper.toDomain(e);

        assertNotNull(d);
        assertNotNull(d.getTipo());
        assertEquals(NU_TIPO, d.getTipo().getNuTipoUnidadeGestora());
        assertEquals(NO_TIPO, d.getTipo().getNoTipoUnidadeGestora());
        assertFalse(d.getTipo().isPermiteGrupoTrabalho());

        assertNull(d.getGrupoTrabalho());
    }

    @Test
    void toDomain_deveMapearGrupoTrabalho_quandoGrupoExistir() {
        UnidadeGestoraPersistenceMapper mapper = new UnidadeGestoraPersistenceMapper();

        GrupoTrabalhoEntity grupo = new GrupoTrabalhoEntity();
        grupo.setNuGrupoTrabalho(NU_GRUPO);
        grupo.setNoGrupoTrabalho(NO_GRUPO);

        UnidadeGestoraEntity e = new UnidadeGestoraEntity();
        e.setNuUnidadeGestora(NU_UNIDADE_GESTORA);
        e.setNuUnidade(NU_UNIDADE);
        e.setNuNatural(NU_NATURAL);
        e.setNoCaixaPostal(NO_CAIXA_POSTAL);
        e.setTipoUnidadeGestora(null);
        e.setGrupoTrabalho(grupo);

        UnidadeGestora d = mapper.toDomain(e);

        assertNotNull(d);
        assertNotNull(d.getGrupoTrabalho());
        assertEquals(NU_GRUPO, d.getGrupoTrabalho().getNuGrupoTrabalho());
        assertEquals(NO_GRUPO, d.getGrupoTrabalho().getNoGrupoTrabalho());

        assertNull(d.getTipo());
    }

    @Test
    void toDomain_deveMapearTipoEGrupo_quandoAmbosExistirem() {
        UnidadeGestoraPersistenceMapper mapper = new UnidadeGestoraPersistenceMapper();

        TipoUnidadeGestoraEntity tipo = new TipoUnidadeGestoraEntity();
        tipo.setNuTipoUnidadeGestora(NU_TIPO);
        tipo.setNoTipoUnidadeGestora(NO_TIPO);
        tipo.setIcGrupoTrabalho(SimNao.S);

        GrupoTrabalhoEntity grupo = new GrupoTrabalhoEntity();
        grupo.setNuGrupoTrabalho(NU_GRUPO);
        grupo.setNoGrupoTrabalho(NO_GRUPO);

        UnidadeGestoraEntity e = new UnidadeGestoraEntity();
        e.setNuUnidadeGestora(NU_UNIDADE_GESTORA);
        e.setNuUnidade(NU_UNIDADE);
        e.setNuNatural(NU_NATURAL);
        e.setNoCaixaPostal(NO_CAIXA_POSTAL);
        e.setTipoUnidadeGestora(tipo);
        e.setGrupoTrabalho(grupo);

        UnidadeGestora d = mapper.toDomain(e);

        assertNotNull(d);
        assertNotNull(d.getTipo());
        assertNotNull(d.getGrupoTrabalho());

        assertEquals(NU_TIPO, d.getTipo().getNuTipoUnidadeGestora());
        assertEquals(NO_TIPO, d.getTipo().getNoTipoUnidadeGestora());
        assertTrue(d.getTipo().isPermiteGrupoTrabalho());

        assertEquals(NU_GRUPO, d.getGrupoTrabalho().getNuGrupoTrabalho());
        assertEquals(NO_GRUPO, d.getGrupoTrabalho().getNoGrupoTrabalho());
    }
}