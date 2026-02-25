package br.gov.caixa.siaas_api.exceptions;

public class NegocioException extends RuntimeException {

    private final String code;
    private final transient Object[] args;

    public NegocioException(String code, Object... args) {
        super(code);
        this.code = code;
        this.args = args;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }
}