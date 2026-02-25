package br.gov.caixa.siaas_api.domain.modulo.model;

import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;

import java.util.Date;
import java.util.Objects;

public class Modulo {

    private final Long nuModulo;
    private final String sgModulo;
    private final String noModulo;
    private final SimNao icPerfilEventual;
    private final SimNao icCentralizado;
    private final SimNao icMultiplosPerfis;
    private final SimNao icSigal;
    private final Date tsCadastro;
    private final String coMatriculaCadastro;
    private final BaseAutenticacao baseAutenticacao;
    private final Long nuProducao;

    private Modulo(Builder b) {
        this.nuModulo = b.nuModulo;
        this.sgModulo = b.sgModulo;
        this.noModulo = b.noModulo;
        this.icPerfilEventual = b.icPerfilEventual;
        this.icCentralizado = b.icCentralizado;
        this.icMultiplosPerfis = b.icMultiplosPerfis;
        this.icSigal = b.icSigal;
        this.tsCadastro = b.tsCadastro;
        this.coMatriculaCadastro = b.coMatriculaCadastro;
        this.baseAutenticacao = b.baseAutenticacao;
        this.nuProducao = b.nuProducao;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long nuModulo;
        private String sgModulo;
        private String noModulo;
        private SimNao icPerfilEventual;
        private SimNao icCentralizado;
        private SimNao icMultiplosPerfis;
        private SimNao icSigal;
        private Date tsCadastro;
        private String coMatriculaCadastro;
        private BaseAutenticacao baseAutenticacao;
        private Long nuProducao;

        public Builder nuModulo(Long v) { this.nuModulo = v; return this; }
        public Builder sgModulo(String v) { this.sgModulo = v; return this; }
        public Builder noModulo(String v) { this.noModulo = v; return this; }
        public Builder icPerfilEventual(SimNao v) { this.icPerfilEventual = v; return this; }
        public Builder icCentralizado(SimNao v) { this.icCentralizado = v; return this; }
        public Builder icMultiplosPerfis(SimNao v) { this.icMultiplosPerfis = v; return this; }
        public Builder icSigal(SimNao v) { this.icSigal = v; return this; }
        public Builder tsCadastro(Date v) { this.tsCadastro = v; return this; }
        public Builder coMatriculaCadastro(String v) { this.coMatriculaCadastro = v; return this; }
        public Builder baseAutenticacao(BaseAutenticacao v) { this.baseAutenticacao = v; return this; }
        public Builder nuProducao(Long v) { this.nuProducao = v; return this; }

        public Modulo build() { return new Modulo(this); }
    }

    public Long getNuModulo() { return nuModulo; }
    public String getSgModulo() { return sgModulo; }
    public String getNoModulo() { return noModulo; }
    public SimNao getIcPerfilEventual() { return icPerfilEventual; }
    public SimNao getIcCentralizado() { return icCentralizado; }
    public SimNao getIcMultiplosPerfis() { return icMultiplosPerfis; }
    public SimNao getIcSigal() { return icSigal; }
    public Date getTsCadastro() { return tsCadastro; }
    public String getCoMatriculaCadastro() { return coMatriculaCadastro; }
    public BaseAutenticacao getBaseAutenticacao() { return baseAutenticacao; }
    public Long getNuProducao() { return nuProducao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Modulo other)) return false;
        return Objects.equals(nuModulo, other.nuModulo);
    }

    @Override
    public int hashCode() { return Objects.hash(nuModulo); }
}