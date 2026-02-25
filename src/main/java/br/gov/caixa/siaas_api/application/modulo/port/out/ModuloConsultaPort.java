package br.gov.caixa.siaas_api.application.modulo.port.out;

public interface ModuloConsultaPort {
    boolean existeModuloUsandoBaseAutenticacao(Long idBaseAutenticacao);
}