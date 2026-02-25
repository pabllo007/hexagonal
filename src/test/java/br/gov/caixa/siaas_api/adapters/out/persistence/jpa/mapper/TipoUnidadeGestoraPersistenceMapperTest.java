package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.TipoUnidadeGestoraEntity;
import br.gov.caixa.siaas_api.domain.tipounidadegestora.model.TipoUnidadeGestora;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoUnidadeGestoraPersistenceMapperTest {

    private static final Integer NU_TIPO = 1;
    private static final String NO_TIPO = "PRODUCAO";

    @Test
    void toDomain_deveRetornarNull_quandoEntityNull() {
        TipoUnidadeGestoraPersistenceMapper mapper = new TipoUnidadeGestoraPersistenceMapper();

        TipoUnidadeGestora result = mapper.toDomain(null);

        assertNull(result);
    }

    @Test
    void toDomain_deveMapearCampos_quandoEntityNaoForNull() {
        TipoUnidadeGestoraPersistenceMapper mapper = new TipoUnidadeGestoraPersistenceMapper();

        TipoUnidadeGestoraEntity e = new TipoUnidadeGestoraEntity();
        e.setNuTipoUnidadeGestora(NU_TIPO);
        e.setNoTipoUnidadeGestora(NO_TIPO);

        TipoUnidadeGestora d = mapper.toDomain(e);

        assertNotNull(d);
        assertEquals(NU_TIPO, d.getNuTipoUnidadeGestora());
        assertEquals(NO_TIPO, d.getNoTipoUnidadeGestora());
    }
}