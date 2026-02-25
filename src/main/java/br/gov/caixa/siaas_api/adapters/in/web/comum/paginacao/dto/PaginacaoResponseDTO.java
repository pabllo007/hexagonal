package br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Estrutura de resposta paginada")
public record PaginacaoResponseDTO<T>(
        @Schema(description = "Lista de registros da página atual")
        List<T> content,
        @Schema(description = "Quantidade total de registros")
        long totalElements,
        @Schema(description = "Quantidade total de páginas")
        int totalPages,
        @Schema(description = "Página atual (0-based)")
        int page,
        @Schema(description = "Quantidade de itens por página")
        int size,
        @Schema(description = "Indica se esta é a primeira página")
        boolean first,
        @Schema(description = "Indica se esta é a última página")
        boolean last
) {}
