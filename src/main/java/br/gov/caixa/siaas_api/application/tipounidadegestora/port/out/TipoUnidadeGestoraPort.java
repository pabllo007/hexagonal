package br.gov.caixa.siaas_api.application.tipounidadegestora.port.out;


import br.gov.caixa.siaas_api.domain.tipounidadegestora.model.TipoUnidadeGestora;

import java.util.List;

public interface TipoUnidadeGestoraPort {
    List<TipoUnidadeGestora> findAll();
}