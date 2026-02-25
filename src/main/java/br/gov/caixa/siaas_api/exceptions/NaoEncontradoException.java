package br.gov.caixa.siaas_api.exceptions;

public class NaoEncontradoException extends NegocioException {

    public NaoEncontradoException(String code, Object... args) {
        super(code, args);
    }
}

