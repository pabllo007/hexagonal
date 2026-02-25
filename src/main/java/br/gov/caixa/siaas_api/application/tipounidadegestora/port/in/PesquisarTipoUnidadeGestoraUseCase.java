package br.gov.caixa.siaas_api.application.tipounidadegestora.port.in;

import br.gov.caixa.siaas_api.domain.tipounidadegestora.model.TipoUnidadeGestora;

import java.util.List;

public interface PesquisarTipoUnidadeGestoraUseCase {
    List<TipoUnidadeGestora> pesquisar();
}
