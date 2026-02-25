package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.GrupoTrabalhoEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.GrupoTrabalhoPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.UnidadeGestoraPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.GrupoTrabalhoRepository;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.UnidadeGestoraRepository;
import br.gov.caixa.siaas_api.application.grupotrabalho.port.out.GrupoTrabalhoPort;
import br.gov.caixa.siaas_api.application.unidadegestora.port.out.UnidadeGestoraPort;
import br.gov.caixa.siaas_api.domain.grupotrabalho.model.GrupoTrabalho;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GrupoTrabalhoJpaAdapter implements GrupoTrabalhoPort {

    private final GrupoTrabalhoRepository repository;
    private final GrupoTrabalhoPersistenceMapper mapper;

    public GrupoTrabalhoJpaAdapter(GrupoTrabalhoRepository repository,
                                   GrupoTrabalhoPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<GrupoTrabalho> findAll() {
        List<GrupoTrabalhoEntity> lista = repository.findAll();
        return lista.stream().map(mapper::toDomain).collect(Collectors.toList());
    }

}