package br.gov.caixa.siaas_api.domain.unidadeGestora.model;

import br.gov.caixa.siaas_api.domain.grupotrabalho.model.GrupoTrabalho;
import br.gov.caixa.siaas_api.domain.tipounidadegestora.model.TipoUnidadeGestora;

public class UnidadeGestora {
    private Long nuUnidadeGestora;
    private String noUnidadeGestora;
    private Integer nuUnidade;
    private Integer nuNatural;
    private String noCaixaPostal;
    private TipoUnidadeGestora tipo;
    private GrupoTrabalho grupoTrabalho;
    private boolean ativo;

    public Long getNuUnidadeGestora() { return nuUnidadeGestora; }
    public void setNuUnidadeGestora(Long nuUnidadeGestora) { this.nuUnidadeGestora = nuUnidadeGestora; }

    public String getNoUnidadeGestora() {
        return noUnidadeGestora;
    }

    public void setNoUnidadeGestora(String noUnidadeGestora) {
        this.noUnidadeGestora = noUnidadeGestora;
    }
    public Integer getNuUnidade() { return nuUnidade; }
    public void setNuUnidade(Integer nuUnidade) { this.nuUnidade = nuUnidade; }

    public Integer getNuNatural() { return nuNatural; }
    public void setNuNatural(Integer nuNatural) { this.nuNatural = nuNatural; }

    public TipoUnidadeGestora getTipo() { return tipo; }
    public void setTipo(TipoUnidadeGestora tipo) { this.tipo = tipo; }

    public GrupoTrabalho getGrupoTrabalho() { return grupoTrabalho; }
    public void setGrupoTrabalho(GrupoTrabalho grupoTrabalho) { this.grupoTrabalho = grupoTrabalho; }

    public String getNoCaixaPostal() { return noCaixaPostal; }
    public void setNoCaixaPostal(String noCaixaPostal) { this.noCaixaPostal = noCaixaPostal; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

}