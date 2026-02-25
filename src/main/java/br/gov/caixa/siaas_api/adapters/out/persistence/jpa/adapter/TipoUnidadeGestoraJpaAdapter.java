package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.TipoUnidadeGestoraEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.TipoUnidadeGestoraPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.TipoUnidadeGestoraRepository;
import br.gov.caixa.siaas_api.application.tipounidadegestora.port.out.TipoUnidadeGestoraPort;
import br.gov.caixa.siaas_api.domain.tipounidadegestora.model.TipoUnidadeGestora;

import java.util.List;
import java.util.stream.Collectors;

public class TipoUnidadeGestoraJpaAdapter implements TipoUnidadeGestoraPort {

    private final TipoUnidadeGestoraRepository repository;
    private final TipoUnidadeGestoraPersistenceMapper mapper;

    public TipoUnidadeGestoraJpaAdapter(TipoUnidadeGestoraRepository repository,
                                        TipoUnidadeGestoraPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<TipoUnidadeGestora> findAll() {
        List<TipoUnidadeGestoraEntity> lista = repository.findAll();
        return lista.stream().map(mapper::toDomain).collect(Collectors.toList());
    }

}