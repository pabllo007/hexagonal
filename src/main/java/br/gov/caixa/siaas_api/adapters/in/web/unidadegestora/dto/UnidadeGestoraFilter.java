package br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Filtros de pesquisa para Unidade Gestora")
public record UnidadeGestoraFilter(

        @Schema(description = "Código da unidade", example = "1234")
        Integer nuUnidade,

        @Schema(description = "Nome da unidade (filtro reservado para integração SIICO)", example = "UNIDADE CENTRAL")
        String noUnidadeGestora,

        @Schema(description = "E-mail da caixa postal", example = "siaas@xpto.gov.br")
        String noCaixaPostal,

        @Schema(description = "Tipo da unidade gestora", example = "1")
        Integer nuTipoUnidadeGestora,

        @Schema(description = "Indicador de ativo", example = "S")
        String icAtivo
) {
}
