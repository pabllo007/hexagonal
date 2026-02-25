package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.BaseAutenticacaoEntity;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseAutenticacaoPersistenceMapperTest {

    private final BaseAutenticacaoPersistenceMapper mapper = new BaseAutenticacaoPersistenceMapper();

    @Test
    void toDomain_deveRetornarNulo_quandoEntityNulo() {
        assertNull(mapper.toDomain(null));
    }

    @Test
    void toDomain_deveMapearCampos_convertendoEnumsParaString() {
        BaseAutenticacaoEntity e = new BaseAutenticacaoEntity();
        e.setNuBaseAutenticacao(1L);
        e.setNoBaseAutenticacao("Base");
        e.setIcRecurso(SimNao.S);
        e.setIcSinav(SimNao.N);
        e.setIcAtivo(SimNao.S);

        BaseAutenticacao d = mapper.toDomain(e);

        assertEquals(1L, d.getNuBaseAutenticacao());
        assertEquals("Base", d.getNoBaseAutenticacao());
        assertEquals(SimNao.S, d.getIcRecurso());
        assertEquals(SimNao.N, d.getIcSinav());
        assertEquals(SimNao.S, d.getIcAtivo());
    }

    @Test
    void toEntity_deveMapearCampos_convertendoStringParaEnum_eCobrirBranches() {
        BaseAutenticacao domain = new BaseAutenticacao();
        domain.setNuBaseAutenticacao(10L);
        domain.setNoBaseAutenticacao("Nome");
        domain.setIcRecurso(SimNao.S);
        domain.setIcSinav(SimNao.N);
        domain.setIcAtivo(SimNao.S);

        BaseAutenticacaoEntity entity = new BaseAutenticacaoEntity();

        BaseAutenticacaoEntity res = mapper.toEntity(domain, entity);

        assertSame(entity, res);
        assertEquals(10L, res.getNuBaseAutenticacao());
        assertEquals("Nome", res.getNoBaseAutenticacao());
        assertEquals(SimNao.S, res.getIcRecurso());
        assertEquals(SimNao.N, res.getIcSinav());
        assertEquals(SimNao.S, res.getIcAtivo());
    }

    @Test
    void toEntityEnum_deveTratarNullEVazio_comoNull() {
        BaseAutenticacao domain = new BaseAutenticacao();
        domain.setNuBaseAutenticacao(1L);
        domain.setNoBaseAutenticacao("X");
        domain.setIcRecurso(null);

        BaseAutenticacaoEntity entity = new BaseAutenticacaoEntity();
        mapper.toEntity(domain, entity);

        assertNull(entity.getIcRecurso());
        assertNull(entity.getIcSinav());
        assertNull(entity.getIcAtivo());
    }

    @Test
    void toDomain_deveCobrirBranchesDoToFlag_quandoCamposNullEEnums() {
        BaseAutenticacaoEntity e = new BaseAutenticacaoEntity();
        e.setNuBaseAutenticacao(1L);
        e.setNoBaseAutenticacao("Base");

        e.setIcRecurso(null);
        e.setIcSinav(SimNao.S);
        e.setIcAtivo(SimNao.N);

        BaseAutenticacao d = mapper.toDomain(e);

        assertEquals(1L, d.getNuBaseAutenticacao());
        assertEquals("Base", d.getNoBaseAutenticacao());
        assertNull(d.getIcRecurso());
        assertEquals(SimNao.S, d.getIcSinav());
        assertEquals(SimNao.N, d.getIcAtivo());
    }

    @Test
    void toEntityForCreate_eToEntityForUpdate_devemMapearCampos() {
        BaseAutenticacao domain = new BaseAutenticacao();
        domain.setNoBaseAutenticacao("Nome");
        domain.setIcRecurso(SimNao.S);
        domain.setIcSinav(SimNao.N);
        domain.setIcAtivo(SimNao.S);

        BaseAutenticacaoEntity e1 = new BaseAutenticacaoEntity();
        BaseAutenticacaoEntity e2 = new BaseAutenticacaoEntity();

        mapper.toEntityForCreate(domain, e1);
        mapper.toEntityForUpdate(domain, e2);

        assertEquals("Nome", e1.getNoBaseAutenticacao());
        assertEquals(SimNao.S, e1.getIcRecurso());
        assertEquals(SimNao.N, e1.getIcSinav());
        assertEquals(SimNao.S, e1.getIcAtivo());

        assertEquals("Nome", e2.getNoBaseAutenticacao());
        assertEquals(SimNao.S, e2.getIcRecurso());
        assertEquals(SimNao.N, e2.getIcSinav());
        assertEquals(SimNao.S, e2.getIcAtivo());
    }
}