package br.gov.caixa.siaas_api.application.baseautenticacao.usecase;

import br.gov.caixa.siaas_api.application.baseautenticacao.port.in.InativarBaseAutenticacaoUseCase;
import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoPort;
import br.gov.caixa.siaas_api.application.modulo.port.out.ModuloConsultaPort;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import br.gov.caixa.siaas_api.exceptions.NaoEncontradoException;
import br.gov.caixa.siaas_api.exceptions.NegocioException;

public class InativarBaseAutenticacaoService implements InativarBaseAutenticacaoUseCase {

    private final BaseAutenticacaoPort basePort;
    private final ModuloConsultaPort moduloPort;

    private static final String CODE_NOT_FOUND = "error.baseautenticacao.not-found";
    private static final String CODE_EM_USO = "error.baseautenticacao.em-uso";

    public InativarBaseAutenticacaoService(BaseAutenticacaoPort basePort, ModuloConsultaPort moduloPort) {
        this.basePort = basePort;
        this.moduloPort = moduloPort;
    }

    @Override
    public BaseAutenticacao inativar(Long id) {
        BaseAutenticacao base = basePort.findById(id)
                .orElseThrow(() -> new NaoEncontradoException(CODE_NOT_FOUND, id));

        if (base.getIcAtivo() == SimNao.N) return base;

        if (moduloPort.existeModuloUsandoBaseAutenticacao(id)) {
            throw new NegocioException(CODE_EM_USO);
        }

        base.setIcAtivo(SimNao.N);
        return basePort.update(base);
    }
}