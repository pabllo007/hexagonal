package br.gov.caixa.siaas_api.application.baseautenticacao.usecase;

import br.gov.caixa.siaas_api.application.baseautenticacao.port.in.AtivarBaseAutenticacaoUseCase;
import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoPort;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import br.gov.caixa.siaas_api.exceptions.NaoEncontradoException;

public class AtivarBaseAutenticacaoService implements AtivarBaseAutenticacaoUseCase {

    private final BaseAutenticacaoPort basePort;

    private static final String CODE_NOT_FOUND = "error.baseautenticacao.not-found";

    public AtivarBaseAutenticacaoService(BaseAutenticacaoPort basePort) {
        this.basePort = basePort;
    }

    @Override
    public BaseAutenticacao ativar(Long id) {
        BaseAutenticacao base = basePort.findById(id)
                .orElseThrow(() -> new NaoEncontradoException(CODE_NOT_FOUND, id));

        if (base.getIcAtivo() == SimNao.S) {
            return base;
        }

        base.setIcAtivo(SimNao.S);
        return basePort.update(base);
    }
}