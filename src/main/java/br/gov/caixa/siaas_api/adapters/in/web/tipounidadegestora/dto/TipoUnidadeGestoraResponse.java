package br.gov.caixa.siaas_api.adapters.in.web.tipounidadegestora.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TipoUnidadeGestoraResponse(
        @Schema(example = "1")
        Integer nuTipoUnidadeGestora,

        @Schema(example = "Tipo Unidade Gestora 001")
        String noTipoUnidadeGestora
) {}