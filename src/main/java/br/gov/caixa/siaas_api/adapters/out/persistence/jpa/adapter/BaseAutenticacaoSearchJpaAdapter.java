package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.BaseAutenticacaoEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.BaseAutenticacaoPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.BaseAutenticacaoRepository;
import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoSearchPort;
import br.gov.caixa.siaas_api.application.baseautenticacao.query.BaseAutenticacaoSearchQuery;
import br.gov.caixa.siaas_api.application.comum.paginacao.PageResult;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.utils.PaginacaoUtils;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.utils.SpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public class BaseAutenticacaoSearchJpaAdapter implements BaseAutenticacaoSearchPort {

    private final BaseAutenticacaoRepository repositorio;
    private final BaseAutenticacaoPersistenceMapper mapper;

    public BaseAutenticacaoSearchJpaAdapter(BaseAutenticacaoRepository repositorio,
                                            BaseAutenticacaoPersistenceMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    @Override
    public PageResult<BaseAutenticacao> search(BaseAutenticacaoSearchQuery q) {

        Pageable pageable = PaginacaoUtils.criarPageable(
                q.page(),
                q.size(),
                q.sortBy(),
                q.direction(),
                "nuBaseAutenticacao"
        );

        Specification<BaseAutenticacaoEntity> spec = new SpecificationBuilder<BaseAutenticacaoEntity>()
                .withEqual("nuBaseAutenticacao", q.nuBaseAutenticacao())
                .withLike("noBaseAutenticacao", q.noBaseAutenticacao())
                .withEqual("icRecurso", q.icRecurso())
                .withEqual("icSinav", q.icSinav())
                .withEqual("icAtivo", q.icAtivo())
                .build();

        Page<BaseAutenticacaoEntity> page = repositorio.findAll(spec, pageable);

        var content = page.getContent().stream()
                .map(mapper::toDomain)
                .toList();

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
}