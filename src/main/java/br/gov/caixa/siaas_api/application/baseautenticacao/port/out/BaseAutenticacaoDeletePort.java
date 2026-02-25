package br.gov.caixa.siaas_api.application.baseautenticacao.port.out;

public interface BaseAutenticacaoDeletePort {
    boolean existsById(Long id);
    void deleteById(Long id);
}