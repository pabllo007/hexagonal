package br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UnidadeGestoraResponse(
        @Schema(example = "1")
        Long nuUnidadeGestora,

        @Schema(example = "Unidade 001")
        String noUnidadeGestora,

        @Schema(example = "Desenvolvimento")
        String tipoUnidadeGestora,

        @Schema(example = "S")
        boolean icAtivo
) {}