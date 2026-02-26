package br.gov.caixa.siaas_api.application.unidadegestora.port.in;

import br.gov.caixa.siaas_api.application.comum.paginacao.PageResult;
import br.gov.caixa.siaas_api.application.unidadegestora.query.UnidadeGestoraSearchQuery;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;

public interface PesquisarUnidadeGestoraUseCase {
    PageResult<UnidadeGestora> pesquisar(UnidadeGestoraSearchQuery query);
}
