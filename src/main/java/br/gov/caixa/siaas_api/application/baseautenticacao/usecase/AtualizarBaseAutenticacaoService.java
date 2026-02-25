package br.gov.caixa.siaas_api.application.baseautenticacao.usecase;

import br.gov.caixa.siaas_api.application.baseautenticacao.port.in.AtualizarBaseAutenticacaoUseCase;
import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoPort;
import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoValidationPort;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.exceptions.NaoEncontradoException;
import br.gov.caixa.siaas_api.exceptions.NegocioException;

public class AtualizarBaseAutenticacaoService implements AtualizarBaseAutenticacaoUseCase {

    private final BaseAutenticacaoPort basePort;
    private final BaseAutenticacaoValidationPort validationPort;

    private static final String CODE_NOT_FOUND = "error.baseautenticacao.not-found";
    private static final String CODE_DUPLICATE = "error.baseautenticacao.duplicate-name";

    public AtualizarBaseAutenticacaoService(BaseAutenticacaoPort basePort,
                                            BaseAutenticacaoValidationPort validationPort) {
        this.basePort = basePort;
        this.validationPort = validationPort;
    }

    @Override
    public BaseAutenticacao atualizar(Long id, BaseAutenticacao entrada) {

        BaseAutenticacao atual = basePort.findById(id)
                .orElseThrow(() -> new NaoEncontradoException(CODE_NOT_FOUND, id));

        String nome = entrada.getNoBaseAutenticacao();

        if (validationPort.existsByNomeIgnoreCaseAndIdNot(nome, id)) {
            throw new NegocioException(CODE_DUPLICATE, nome);
        }

        entrada.setNuBaseAutenticacao(id);
        entrada.setIcAtivo(atual.getIcAtivo());

        return basePort.update(entrada);
    }
}