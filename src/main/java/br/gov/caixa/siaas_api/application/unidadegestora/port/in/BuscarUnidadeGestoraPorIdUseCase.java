package br.gov.caixa.siaas_api.application.unidadegestora.port.in;

import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;

public interface BuscarUnidadeGestoraPorIdUseCase {
        UnidadeGestora buscar(Long nuUnidadeGestora);
}
