package br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.dto;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Parâmetros de paginação e ordenação")
public record PaginacaoRequestDTO(

        @Schema(
                description = "Número da página (inicia em 0)",
                example = "0",
                defaultValue = "0"
        )
        Integer page,

        @Schema(
                description = "Quantidade de registros por página",
                example = "20",
                defaultValue = "20"
        )
        Integer size,

        @Schema(
                description = "Campo utilizado para ordenação",
                example = "noBaseAutenticacao"
        )
        String sortBy,

        @Schema(
                description = "Direção da ordenação",
                example = "ASC",
                allowableValues = {"ASC", "DESC"},
                defaultValue = "ASC"
        )
        String direction
) {}