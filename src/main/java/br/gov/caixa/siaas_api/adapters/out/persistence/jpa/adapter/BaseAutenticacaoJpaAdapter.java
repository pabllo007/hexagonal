package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.BaseAutenticacaoEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.BaseAutenticacaoPersistenceMapper;
import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoPort;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.BaseAutenticacaoRepository;

import java.util.Optional;

public class BaseAutenticacaoJpaAdapter implements BaseAutenticacaoPort {

    private final BaseAutenticacaoRepository repository;
    private final BaseAutenticacaoPersistenceMapper mapper;

    public BaseAutenticacaoJpaAdapter(BaseAutenticacaoRepository repository,
                                      BaseAutenticacaoPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<BaseAutenticacao> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public BaseAutenticacao create(BaseAutenticacao base) {
        var entity = new BaseAutenticacaoEntity();
        mapper.toEntityForCreate(base, entity);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public BaseAutenticacao update(BaseAutenticacao base) {
        if (base.getNuBaseAutenticacao() == null) {
            throw new IllegalArgumentException("ID é obrigatório para atualizar BaseAutenticacao.");
        }

        var entity = repository.findById(base.getNuBaseAutenticacao()).orElseThrow();
        mapper.toEntityForUpdate(base, entity);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }
}