package br.gov.caixa.siaas_api.application.baseautenticacao.port.out;


import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;

import java.util.Optional;

public interface BaseAutenticacaoPort {
    Optional<BaseAutenticacao> findById(Long id);
    BaseAutenticacao create(BaseAutenticacao base);

    BaseAutenticacao update(BaseAutenticacao base);
}