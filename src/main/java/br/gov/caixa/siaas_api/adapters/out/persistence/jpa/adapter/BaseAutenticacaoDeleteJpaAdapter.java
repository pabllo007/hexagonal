package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoDeletePort;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.BaseAutenticacaoRepository;

public class BaseAutenticacaoDeleteJpaAdapter implements BaseAutenticacaoDeletePort {

    private final BaseAutenticacaoRepository repo;

    public BaseAutenticacaoDeleteJpaAdapter(BaseAutenticacaoRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean existsById(Long id) {
        return repo.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}