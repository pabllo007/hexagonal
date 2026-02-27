package br.gov.caixa.siaas_api.application.tipounidadegestora.usecase;

import br.gov.caixa.siaas_api.application.tipounidadegestora.port.in.PesquisarTipoUnidadeGestoraUseCase;
import br.gov.caixa.siaas_api.application.tipounidadegestora.port.out.TipoUnidadeGestoraPort;
import br.gov.caixa.siaas_api.config.CacheNames;
import br.gov.caixa.siaas_api.domain.tipounidadegestora.model.TipoUnidadeGestora;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public class PesquisarTipoUnidadeGestoraService implements PesquisarTipoUnidadeGestoraUseCase {

    private final TipoUnidadeGestoraPort port;

    public PesquisarTipoUnidadeGestoraService(TipoUnidadeGestoraPort port) {
        this.port = port;
    }

    @Override
    @Cacheable(cacheNames = CacheNames.TIPO_UNIDADE_GESTORA, key = "'all'")
    public List<TipoUnidadeGestora> pesquisar() {
        return port.findAll();
    }
}
