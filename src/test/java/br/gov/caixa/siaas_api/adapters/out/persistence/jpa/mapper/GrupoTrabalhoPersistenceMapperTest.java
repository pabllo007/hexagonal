package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.GrupoTrabalhoEntity;
import br.gov.caixa.siaas_api.domain.grupotrabalho.model.GrupoTrabalho;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrupoTrabalhoPersistenceMapperTest {

    private static final Integer NU_GRUPO = 1;
    private static final String NO_GRUPO = "CEPTI SEGURANCA BSB";

    @Test
    void toDomain_deveRetornarNull_quandoEntityNull() {
        GrupoTrabalhoPersistenceMapper mapper = new GrupoTrabalhoPersistenceMapper();

        GrupoTrabalho result = mapper.toDomain(null);

        assertNull(result);
    }

    @Test
    void toDomain_deveMapearCampos_quandoEntityNaoForNull() {
        GrupoTrabalhoPersistenceMapper mapper = new GrupoTrabalhoPersistenceMapper();

        GrupoTrabalhoEntity e = new GrupoTrabalhoEntity();
        e.setNuGrupoTrabalho(NU_GRUPO);
        e.setNoGrupoTrabalho(NO_GRUPO);

        GrupoTrabalho d = mapper.toDomain(e);

        assertNotNull(d);
        assertEquals(NU_GRUPO, d.getNuGrupoTrabalho());
        assertEquals(NO_GRUPO, d.getNoGrupoTrabalho());
    }
}