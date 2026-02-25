package br.gov.caixa.siaas_api.application.comum.paginacao;

import java.util.List;

public record PageResult<T>(
        List<T> content,
        long totalElements,
        int totalPages,
        int page,
        int size,
        boolean first,
        boolean last
) {}