package br.gov.caixa.siaas_api.application.baseautenticacao.usecase;

import br.gov.caixa.siaas_api.application.baseautenticacao.port.in.ExcluirBaseAutenticacaoUseCase;
import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoDeletePort;
import br.gov.caixa.siaas_api.application.modulo.port.out.ModuloConsultaPort;
import br.gov.caixa.siaas_api.exceptions.NaoEncontradoException;
import br.gov.caixa.siaas_api.exceptions.NegocioException;

public class ExcluirBaseAutenticacaoService implements ExcluirBaseAutenticacaoUseCase {

    private final BaseAutenticacaoDeletePort deletePort;
    private final ModuloConsultaPort moduloPort;

    private static final String CODE_NOT_FOUND = "error.baseautenticacao.not-found";
    private static final String CODE_EM_USO = "error.baseautenticacao.em-uso";

    public ExcluirBaseAutenticacaoService(BaseAutenticacaoDeletePort deletePort,
                                          ModuloConsultaPort moduloPort) {
        this.deletePort = deletePort;
        this.moduloPort = moduloPort;
    }

    @Override
    public void excluir(Long id) {

        if (!deletePort.existsById(id)) {
            throw new NaoEncontradoException(CODE_NOT_FOUND, id);
        }

        if (moduloPort.existeModuloUsandoBaseAutenticacao(id)) {
            throw new NegocioException(CODE_EM_USO);
        }

        deletePort.deleteById(id);
    }
}