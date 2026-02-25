package br.gov.caixa.siaas_api.adapters.in.web.tipounidadegestora.mapper;

import br.gov.caixa.siaas_api.adapters.in.web.tipounidadegestora.dto.TipoUnidadeGestoraResponse;
import br.gov.caixa.siaas_api.domain.tipounidadegestora.model.TipoUnidadeGestora;

public class TipoUnidadeGestoraWebMapper {

    public TipoUnidadeGestoraResponse toResponse(TipoUnidadeGestora domain) {
        if (domain == null) return null;

        return new TipoUnidadeGestoraResponse(
                domain.getNuTipoUnidadeGestora(),
                domain.getNoTipoUnidadeGestora()
        );
    }
}