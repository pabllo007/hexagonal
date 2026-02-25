package br.gov.caixa.siaas_api.adapters.in.web.grupotrabalho.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record GrupoTrabalhoResponse(
        @Schema(example = "1")
        Integer nuGrupoTrabalho,

        @Schema(example = "Grupo Trabalho 001")
        String noGrupoTrabalho
) {}