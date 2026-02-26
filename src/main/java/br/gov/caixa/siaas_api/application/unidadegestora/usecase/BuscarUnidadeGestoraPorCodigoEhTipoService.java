package br.gov.caixa.siaas_api.application.unidadegestora.usecase;

import br.gov.caixa.siaas_api.application.unidadegestora.port.in.BuscarPorCodigoETipoUseCase;
import br.gov.caixa.siaas_api.application.unidadegestora.port.in.BuscarUnidadeGestoraPorIdUseCase;
import br.gov.caixa.siaas_api.application.unidadegestora.port.out.UnidadeGestoraPort;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;
import br.gov.caixa.siaas_api.exceptions.NaoEncontradoException;

public class BuscarUnidadeGestoraPorCodigoEhTipoService implements BuscarPorCodigoETipoUseCase {

    private final UnidadeGestoraPort unidadeGestoraPort;
    private static final String CODE_NOT_FOUND = "error.baseautenticacao.not-found";
    public BuscarUnidadeGestoraPorCodigoEhTipoService(UnidadeGestoraPort unidadeGestoraPort) {
        this.unidadeGestoraPort = unidadeGestoraPort;
    }

    @Override
    public UnidadeGestora buscarPorCodigoEhTipo(Long nuUnidade, Long tipo) {
        return  unidadeGestoraPort.buscarPorCodigoEhTipo(nuUnidade, tipo)
                .orElseThrow(() -> new NaoEncontradoException(CODE_NOT_FOUND, nuUnidade));
    }
}
