package br.gov.caixa.siaas_api.application.baseautenticacao.usecase;

import br.gov.caixa.siaas_api.application.baseautenticacao.port.in.PesquisarBaseAutenticacaoUseCase;
import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoSearchPort;
import br.gov.caixa.siaas_api.application.baseautenticacao.query.BaseAutenticacaoSearchQuery;
import br.gov.caixa.siaas_api.application.comum.paginacao.PageResult;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;

public class PesquisarBaseAutenticacaoService implements PesquisarBaseAutenticacaoUseCase {

    private final BaseAutenticacaoSearchPort searchPort;

    public PesquisarBaseAutenticacaoService(BaseAutenticacaoSearchPort searchPort) {
        this.searchPort = searchPort;
    }

    @Override
    public PageResult<BaseAutenticacao> pesquisar(BaseAutenticacaoSearchQuery query) {
        return searchPort.search(query);
    }
}