package br.gov.caixa.siaas_api.exceptions;

public record ApiError(
        String code,
        String message,
        int status
) {}
