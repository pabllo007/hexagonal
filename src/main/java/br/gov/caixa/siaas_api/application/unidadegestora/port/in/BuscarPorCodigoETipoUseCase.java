package br.gov.caixa.siaas_api.application.unidadegestora.port.in;

import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;

public interface BuscarPorCodigoETipoUseCase {
    UnidadeGestora buscarPorCodigoEhTipo(Long nuUnidade, Long tipo);
}
