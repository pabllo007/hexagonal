package br.gov.caixa.siaas_api.application.baseautenticacao.usecase;

import br.gov.caixa.siaas_api.application.baseautenticacao.port.in.BuscarBaseAutenticacaoPorIdUseCase;
import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoPort;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.exceptions.NaoEncontradoException;

public class BuscarBaseAutenticacaoPorIdService implements BuscarBaseAutenticacaoPorIdUseCase {

    private final BaseAutenticacaoPort basePort;

    private static final String CODE_NOT_FOUND = "error.baseautenticacao.not-found";

    public BuscarBaseAutenticacaoPorIdService(BaseAutenticacaoPort basePort) {
        this.basePort = basePort;
    }

    @Override
    public BaseAutenticacao buscar(Long id) {
        return basePort.findById(id)
                .orElseThrow(() -> new NaoEncontradoException(CODE_NOT_FOUND, id));
    }
}