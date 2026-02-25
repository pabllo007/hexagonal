package br.gov.caixa.siaas_api.adapters.in.web.grupotrabalho.mapper;

import br.gov.caixa.siaas_api.adapters.in.web.grupotrabalho.dto.GrupoTrabalhoResponse;
import br.gov.caixa.siaas_api.domain.grupotrabalho.model.GrupoTrabalho;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrupoTrabalhoWebMapperTest {

    private static final Integer NU_GRUPO_TRABALHO = 1;
    private static final String NO_GRUPO_TRABALHO = "Grupo Trabalho 001";

    @Test
    void toResponse_deveRetornarNull_quandoDomainNull() {
        GrupoTrabalhoWebMapper mapper = new GrupoTrabalhoWebMapper();

        GrupoTrabalhoResponse result = mapper.toResponse(null);

        assertNull(result);
    }

    @Test
    void toResponse_deveMapearCampos_quandoDomainNaoForNull() {
        GrupoTrabalhoWebMapper mapper = new GrupoTrabalhoWebMapper();

        GrupoTrabalho domain = new GrupoTrabalho();
        domain.setNuGrupoTrabalho(NU_GRUPO_TRABALHO);
        domain.setNoGrupoTrabalho(NO_GRUPO_TRABALHO);

        GrupoTrabalhoResponse result = mapper.toResponse(domain);

        assertNotNull(result);
        assertEquals(NU_GRUPO_TRABALHO, result.nuGrupoTrabalho());
        assertEquals(NO_GRUPO_TRABALHO, result.noGrupoTrabalho());
    }
}