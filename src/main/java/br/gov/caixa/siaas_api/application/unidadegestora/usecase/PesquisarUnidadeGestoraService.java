package br.gov.caixa.siaas_api.application.unidadegestora.usecase;

import br.gov.caixa.siaas_api.application.comum.paginacao.PageResult;
import br.gov.caixa.siaas_api.application.unidadegestora.port.in.PesquisarUnidadeGestoraUseCase;
import br.gov.caixa.siaas_api.application.unidadegestora.port.out.UnidadeGestoraPort;
import br.gov.caixa.siaas_api.application.unidadegestora.query.UnidadeGestoraSearchQuery;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;

public class PesquisarUnidadeGestoraService implements PesquisarUnidadeGestoraUseCase {

    private final UnidadeGestoraPort unidadeGestoraPort;

    public PesquisarUnidadeGestoraService(UnidadeGestoraPort unidadeGestoraPort) {
        this.unidadeGestoraPort = unidadeGestoraPort;
    }

    @Override
    public PageResult<UnidadeGestora> pesquisar(UnidadeGestoraSearchQuery query) {
        return unidadeGestoraPort.search(query);
    }
}
