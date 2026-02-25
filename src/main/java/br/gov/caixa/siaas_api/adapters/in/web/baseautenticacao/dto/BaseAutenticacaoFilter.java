package br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Filtros de pesquisa para Base de Autenticação")
public record BaseAutenticacaoFilter(

        @Schema(description = "Número da Base de Autenticação", example = "123")
        Long nuBaseAutenticacao,

        @Schema(description = "Nome da Base de Autenticação", example = "SIAFI")
        String noBaseAutenticacao,

        @Schema(description = "Indicador de recurso", example = "S")
        String icRecurso,

        @Schema(description = "Indicador de SINAV", example = "N")
        String icSinav,

        @Schema(description = "Indicador de ativo", example = "S")
        String icAtivo
) {}