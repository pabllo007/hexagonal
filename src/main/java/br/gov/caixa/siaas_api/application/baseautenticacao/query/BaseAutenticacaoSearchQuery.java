package br.gov.caixa.siaas_api.application.baseautenticacao.query;

import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;

public record BaseAutenticacaoSearchQuery(
        Long nuBaseAutenticacao,
        String noBaseAutenticacao,
        SimNao icRecurso,
        SimNao icSinav,
        SimNao icAtivo,
        Integer page,
        Integer size,
        String sortBy,
        String direction
) {}