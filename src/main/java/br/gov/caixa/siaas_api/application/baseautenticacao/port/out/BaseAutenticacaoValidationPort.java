package br.gov.caixa.siaas_api.application.baseautenticacao.port.out;

public interface BaseAutenticacaoValidationPort {
    boolean existsByNomeIgnoreCase(String nome);
    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);
}