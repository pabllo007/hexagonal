package br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.mapper;
import br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.dto.PaginacaoResponseDTO;
import br.gov.caixa.siaas_api.application.comum.paginacao.PageResult;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public final class PaginacaoMapper {

    private PaginacaoMapper() {}

    public static <E, D> PaginacaoResponseDTO<D> paraRespostaPaginada(
            Page<E> pagina, Function<E, D> mapeador) {
        List<D> conteudo = mapearConteudo(pagina, mapeador);
        return new PaginacaoResponseDTO<>(conteudo,
                pagina.getTotalElements(), pagina.getTotalPages(),
                pagina.getNumber(), pagina.getSize(),
                pagina.isFirst(), pagina.isLast());
    }

    private static <E, D> List<D> mapearConteudo(Page<E> pagina,
                                                 Function<E, D> mapeador) {
        return pagina.getContent().stream()
                .map(mapeador)
                .toList();
    }

    public static <E, D> PaginacaoResponseDTO<D> paraRespostaPaginada(
            PageResult<E> pagina,
            Function<E, D> mapeador
    ) {
        List<D> conteudo = pagina.content().stream().map(mapeador).toList();
        return new PaginacaoResponseDTO<>(
                conteudo,
                pagina.totalElements(),
                pagina.totalPages(),
                pagina.page(),
                pagina.size(),
                pagina.first(),
                pagina.last()
        );
    }
}
