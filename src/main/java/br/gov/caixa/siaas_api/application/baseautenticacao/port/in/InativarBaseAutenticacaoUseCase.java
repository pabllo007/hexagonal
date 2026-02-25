package br.gov.caixa.siaas_api.application.baseautenticacao.port.in;

import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;

public interface InativarBaseAutenticacaoUseCase {
    BaseAutenticacao inativar(Long id);
}