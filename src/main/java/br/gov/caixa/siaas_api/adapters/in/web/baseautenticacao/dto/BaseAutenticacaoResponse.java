package br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record BaseAutenticacaoResponse(
        @Schema(example = "1")
        Long nuBaseAutenticacao,

        @Schema(example = "Base 001")
        String noBaseAutenticacao,

        @Schema(example = "S")
        String icRecurso,

        @Schema(example = "N")
        String icSinav,

        @Schema(example = "S")
        String icAtivo
) {}