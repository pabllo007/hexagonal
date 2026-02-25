package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoValidationPort;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.BaseAutenticacaoRepository;

public class BaseAutenticacaoValidationJpaAdapter implements BaseAutenticacaoValidationPort {

    private final BaseAutenticacaoRepository repo;

    public BaseAutenticacaoValidationJpaAdapter(BaseAutenticacaoRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean existsByNomeIgnoreCase(String nome) {
        return repo.existsByNoBaseAutenticacaoIgnoreCase(nome);
    }

    @Override
    public boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id) {
        return repo.existsByNoBaseAutenticacaoIgnoreCaseAndNuBaseAutenticacaoNot(nome, id);
    }
}