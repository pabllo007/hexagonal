package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.UnidadeGestoraPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.UnidadeGestoraRepository;
import br.gov.caixa.siaas_api.application.unidadegestora.port.out.UnidadeGestoraPort;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;

import java.util.Optional;

public class UnidadeGestoraJpaAdapter implements UnidadeGestoraPort {

    private final UnidadeGestoraRepository repository;
    private final UnidadeGestoraPersistenceMapper mapper;

    public UnidadeGestoraJpaAdapter(UnidadeGestoraRepository repository,
                                    UnidadeGestoraPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<UnidadeGestora> findById(Long nuUnidadeGestora) {
        return repository.findByNuUnidadeGestora(nuUnidadeGestora).map(mapper::toDomain);
    }

    @Override
    public Optional<UnidadeGestora> buscarPorCodigoEhTipo(Long nuUnidade, Long tipo) {
        return repository.findByNuUnidadeEhTipoUnidadeGestora(nuUnidade, tipo)
                .map(mapper::toDomain);
    }
}