package br.gov.caixa.siaas_api.adapters.in.web.tipounidadegestora.mapper;

import br.gov.caixa.siaas_api.adapters.in.web.tipounidadegestora.dto.TipoUnidadeGestoraResponse;
import br.gov.caixa.siaas_api.domain.tipounidadegestora.model.TipoUnidadeGestora;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoUnidadeGestoraWebMapperTest {

    private static final Integer NU_TIPO = 1;
    private static final String NO_TIPO = "Tipo Unidade Gestora 001";

    @Test
    void toResponse_deveRetornarNull_quandoDomainNull() {
        TipoUnidadeGestoraWebMapper mapper = new TipoUnidadeGestoraWebMapper();

        TipoUnidadeGestoraResponse result = mapper.toResponse(null);

        assertNull(result);
    }

    @Test
    void toResponse_deveMapearCampos_quandoDomainNaoForNull() {
        TipoUnidadeGestoraWebMapper mapper = new TipoUnidadeGestoraWebMapper();

        TipoUnidadeGestora domain = new TipoUnidadeGestora();
        domain.setNuTipoUnidadeGestora(NU_TIPO);
        domain.setNoTipoUnidadeGestora(NO_TIPO);

        TipoUnidadeGestoraResponse result = mapper.toResponse(domain);

        assertNotNull(result);
        assertEquals(NU_TIPO, result.nuTipoUnidadeGestora());
        assertEquals(NO_TIPO, result.noTipoUnidadeGestora());
    }
}