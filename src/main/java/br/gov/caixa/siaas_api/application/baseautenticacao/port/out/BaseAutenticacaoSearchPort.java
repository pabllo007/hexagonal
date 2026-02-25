package br.gov.caixa.siaas_api.application.baseautenticacao.port.out;

import br.gov.caixa.siaas_api.application.baseautenticacao.query.BaseAutenticacaoSearchQuery;
import br.gov.caixa.siaas_api.application.comum.paginacao.PageResult;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;

public interface BaseAutenticacaoSearchPort {
    PageResult<BaseAutenticacao> search(BaseAutenticacaoSearchQuery query);
}