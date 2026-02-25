package br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record BaseAutenticacaoRequest(

        @NotBlank(message = "O nome da base de autenticação é obrigatório.")
        @Size(max = 100, message = "O nome da base de autenticação deve ter no máximo 100 caracteres.")
        @Schema(description = "Nome da Base de Autenticação", example = "Base 001")
        String noBaseAutenticacao,

        @NotBlank(message = "O indicador de recurso é obrigatório e deve ser 'S' ou 'N'.")
        @Pattern(regexp = "^[SNsn]$", message = "O indicador de recurso é obrigatório e deve ser 'S' ou 'N'.")
        @Schema(description = "Indicador de recurso", example = "S")
        String icRecurso,

        @NotBlank(message = "O indicador de SINAV é obrigatório e deve ser 'S' ou 'N'.")
        @Pattern(regexp = "^[SNsn]$", message = "O indicador de SINAV é obrigatório e deve ser 'S' ou 'N'.")
        @Schema(description = "Indicador de SINAV", example = "N")
        String icSinav
) {}