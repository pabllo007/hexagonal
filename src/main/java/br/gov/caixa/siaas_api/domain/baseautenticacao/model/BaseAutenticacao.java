package br.gov.caixa.siaas_api.domain.baseautenticacao.model;

import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;

public class BaseAutenticacao {
    private Long nuBaseAutenticacao;
    private String noBaseAutenticacao;
    private SimNao icRecurso;
    private SimNao icSinav;
    private SimNao icAtivo;

    public BaseAutenticacao() {
    }

    public BaseAutenticacao(Long id, String nome, SimNao icRecurso, SimNao icSinav, SimNao icAtivo) {
        this.nuBaseAutenticacao = id;
        this.noBaseAutenticacao = nome;
        this.icRecurso = icRecurso;
        this.icSinav = icSinav;
        this.icAtivo = icAtivo;
    }

    public boolean isAtiva() {
        return icAtivo != null && icAtivo.isSim();
    }
    public void inativar() {
        this.icAtivo = SimNao.N;
    }

    public Long getNuBaseAutenticacao() {
        return nuBaseAutenticacao;
    }

    public void setNuBaseAutenticacao(Long nuBaseAutenticacao) {
        this.nuBaseAutenticacao = nuBaseAutenticacao;
    }

    public String getNoBaseAutenticacao() {
        return noBaseAutenticacao;
    }

    public void setNoBaseAutenticacao(String noBaseAutenticacao) {
        this.noBaseAutenticacao = noBaseAutenticacao;
    }

    public SimNao getIcRecurso() {
        return icRecurso;
    }

    public void setIcRecurso(SimNao icRecurso) {
        this.icRecurso = icRecurso;
    }

    public SimNao getIcSinav() {
        return icSinav;
    }

    public void setIcSinav(SimNao icSinav) {
        this.icSinav = icSinav;
    }

    public SimNao getIcAtivo() {
        return icAtivo;
    }

    public void setIcAtivo(SimNao icAtivo) {
        this.icAtivo = icAtivo;
    }
}