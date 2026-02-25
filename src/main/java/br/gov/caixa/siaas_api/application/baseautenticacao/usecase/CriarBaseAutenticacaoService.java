package br.gov.caixa.siaas_api.application.baseautenticacao.usecase;

import br.gov.caixa.siaas_api.application.baseautenticacao.port.in.CriarBaseAutenticacaoUseCase;
import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoPort;
import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoValidationPort;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import br.gov.caixa.siaas_api.exceptions.NegocioException;

public class CriarBaseAutenticacaoService implements CriarBaseAutenticacaoUseCase {

    private final BaseAutenticacaoPort basePort;
    private final BaseAutenticacaoValidationPort validationPort;

    private static final String CODE_DUPLICATE = "error.baseautenticacao.duplicate-name";

    public CriarBaseAutenticacaoService(BaseAutenticacaoPort basePort,
                                        BaseAutenticacaoValidationPort validationPort) {
        this.basePort = basePort;
        this.validationPort = validationPort;
    }

    @Override
    public BaseAutenticacao criar(BaseAutenticacao entrada) {
        String nome = entrada.getNoBaseAutenticacao();

        if (validationPort.existsByNomeIgnoreCase(nome)) {
            throw new NegocioException(CODE_DUPLICATE, nome);
        }

        entrada.setIcAtivo(SimNao.S);
        return basePort.create(entrada);
    }
}