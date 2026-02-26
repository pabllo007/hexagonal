package br.gov.caixa.siaas_api.application.unidadegestora.query;

import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;

public record UnidadeGestoraSearchQuery(
        Integer nuUnidade,
        String noUnidadeGestora,
        String noCaixaPostal,
        Integer nuTipoUnidadeGestora,
        SimNao icAtivo,
        Integer page,
        Integer size,
        String sortBy,
        String direction
) {}
