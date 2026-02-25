package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PaginacaoUtils {

    private static final int PAGINA_PADRAO = 0;
    private static final int TAMANHO_PADRAO = 20;
    private static final String ORDENACAO_PADRAO = "id";

    private PaginacaoUtils() {}

    /**
     * Método "hexagonal-friendly": não depende de DTO web.
     * Preserva comportamento: page < 0 => 0, size <= 0 => 20,
     * sortBy em branco => defaultSort (ou "id"),
     * direction nula => ASC.
     */
    public static Pageable criarPageable(Integer page,
                                         Integer size,
                                         String sortBy,
                                         String direction,
                                         String defaultSort) {

        int pagina = (page == null || page < 0) ? PAGINA_PADRAO : page;
        int tamanho = (size == null || size <= 0) ? TAMANHO_PADRAO : size;

        String campoOrdenacao = (sortBy == null || sortBy.isBlank())
                ? obterOrdenacaoPadrao(defaultSort)
                : sortBy;

        Sort.Direction dir = (direction == null || direction.isBlank())
                ? Sort.Direction.ASC
                : Sort.Direction.fromString(direction);

        Sort sort = Sort.by(dir, campoOrdenacao);

        return PageRequest.of(pagina, tamanho, sort);
    }

    public static Pageable criarPageable(Integer page,
                                         Integer size,
                                         String sortBy,
                                         String direction) {
        return criarPageable(page, size, sortBy, direction, ORDENACAO_PADRAO);
    }

    private static String obterOrdenacaoPadrao(String campoOrdenacaoPadrao) {
        if (campoOrdenacaoPadrao == null || campoOrdenacaoPadrao.isBlank()) {
            return ORDENACAO_PADRAO;
        }
        return campoOrdenacaoPadrao;
    }
}