package br.gov.caixa.siaas_api.application.unidadegestora.port.out;

import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;

import java.util.Optional;

public interface UnidadeGestoraPort {

        Optional<UnidadeGestora> findById(Long nuUnidadeGestora);

}
