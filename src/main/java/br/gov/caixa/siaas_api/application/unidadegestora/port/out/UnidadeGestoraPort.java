package br.gov.caixa.siaas_api.application.unidadegestora.port.out;

import br.gov.caixa.siaas_api.application.comum.paginacao.PageResult;
import br.gov.caixa.siaas_api.application.unidadegestora.query.UnidadeGestoraSearchQuery;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;

import java.util.Optional;

public interface UnidadeGestoraPort {

        Optional<UnidadeGestora> findById(Long nuUnidadeGestora);
        Optional<UnidadeGestora> buscarPorCodigoEhTipo(Long nuUnidade, Long tipo);
        PageResult<UnidadeGestora> search(UnidadeGestoraSearchQuery query);

}
