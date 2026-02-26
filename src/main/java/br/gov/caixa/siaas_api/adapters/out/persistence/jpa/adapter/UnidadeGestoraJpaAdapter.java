package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.UnidadeGestoraEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.utils.PaginacaoUtils;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.utils.SpecificationBuilder;
import br.gov.caixa.siaas_api.application.comum.paginacao.PageResult;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.UnidadeGestoraPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.UnidadeGestoraRepository;
import br.gov.caixa.siaas_api.application.unidadegestora.port.out.UnidadeGestoraPort;
import br.gov.caixa.siaas_api.application.unidadegestora.query.UnidadeGestoraSearchQuery;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

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

    @Override
    public PageResult<UnidadeGestora> search(UnidadeGestoraSearchQuery query) {
        String sortBy = traduzirCampoOrdenacao(query.sortBy());

        Pageable pageable = PaginacaoUtils.criarPageable(
                query.page(),
                query.size(),
                sortBy,
                query.direction(),
                "nuUnidadeGestora"
        );

        Specification<UnidadeGestoraEntity> spec = new SpecificationBuilder<UnidadeGestoraEntity>()
                .withEqual("nuUnidade", query.nuUnidade())
                .withLike("noCaixaPostal", query.noCaixaPostal())
                .withEqual("icAtivo", query.icAtivo())
                .build();

        if (query.nuTipoUnidadeGestora() != null) {
            spec = spec.and((root, cq, cb) -> cb.equal(
                    root.get("tipoUnidadeGestora").get("nuTipoUnidadeGestora"),
                    query.nuTipoUnidadeGestora()
            ));
        }

        // Filtro por nome reservado para integração futura com API SIICO.
        // query.noUnidadeGestora()

        Page<UnidadeGestoraEntity> page = repository.findAll(spec, pageable);

        var content = page.getContent().stream().map(mapper::toDomain).toList();

        return new PageResult<>(
                content,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.isFirst(),
                page.isLast()
        );
    }

    private String traduzirCampoOrdenacao(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return sortBy;
        }

        return switch (sortBy) {
            case "nuTipoUnidade" -> "tipoUnidadeGestora.nuTipoUnidadeGestora";
            default -> sortBy;
        };
    }
}
