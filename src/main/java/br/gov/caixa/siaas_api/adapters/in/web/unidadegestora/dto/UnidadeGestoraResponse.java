package br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UnidadeGestoraResponse(
        @Schema(example = "1")
        Long nuUnidadeGestora,

        @Schema(example = "Unidade 001")
        String noUnidadeGestora,

        @Schema(example = "S")
        boolean icAtivo,

        @Schema(example = "1234")
        Integer nuUnidade,

        @Schema(example = "1")
        Integer nuTipoUnidade,

        @Schema(example = "Desenvolvimento")
        String notipoUnidadeGestora,

        @Schema(example = "1")
        Integer nuGrupoTrabalho,

        @Schema(example = "CEDES XPTO")
        String noGrupoTrabalho,

        @Schema(example = "siaas@xpto.gov.br")
         String noCaixaPostal

) {}