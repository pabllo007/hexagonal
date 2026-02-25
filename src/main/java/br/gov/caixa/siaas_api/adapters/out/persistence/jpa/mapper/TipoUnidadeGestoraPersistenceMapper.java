package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.TipoUnidadeGestoraEntity;
import br.gov.caixa.siaas_api.domain.tipounidadegestora.model.TipoUnidadeGestora;

public class TipoUnidadeGestoraPersistenceMapper {

    public TipoUnidadeGestora toDomain(TipoUnidadeGestoraEntity e) {
        if (e == null) return null;

        TipoUnidadeGestora d = new TipoUnidadeGestora();
        d.setNuTipoUnidadeGestora(e.getNuTipoUnidadeGestora());
        d.setNoTipoUnidadeGestora(e.getNoTipoUnidadeGestora());
        return d;
    }
}