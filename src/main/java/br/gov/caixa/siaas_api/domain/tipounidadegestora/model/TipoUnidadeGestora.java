package br.gov.caixa.siaas_api.domain.tipounidadegestora.model;

import java.util.Objects;

public class TipoUnidadeGestora {

    public static final int DESENVOLVIMENTO = 1;
    public static final int NEGOCIO = 2;
    public static final int TI = 3;
    public static final int SEGURANCA = 4;
    public static final int PRODUCAO = 5;

    private Integer nuTipoUnidadeGestora;
    private String noTipoUnidadeGestora;
    private boolean permiteGrupoTrabalho; // icGrupoTrabalho == 'S'

    public TipoUnidadeGestora() {}

    public TipoUnidadeGestora(Integer nuTipoUnidadeGestora, String noTipoUnidadeGestora, boolean permiteGrupoTrabalho) {
        this.nuTipoUnidadeGestora = nuTipoUnidadeGestora;
        this.noTipoUnidadeGestora = noTipoUnidadeGestora;
        this.permiteGrupoTrabalho = permiteGrupoTrabalho;
    }

    public Integer getNuTipoUnidadeGestora() { return nuTipoUnidadeGestora; }
    public void setNuTipoUnidadeGestora(Integer nuTipoUnidadeGestora) { this.nuTipoUnidadeGestora = nuTipoUnidadeGestora; }

    public String getNoTipoUnidadeGestora() { return noTipoUnidadeGestora; }
    public void setNoTipoUnidadeGestora(String nome) { this.noTipoUnidadeGestora = nome; }

    public boolean isPermiteGrupoTrabalho() { return permiteGrupoTrabalho; }
    public void setPermiteGrupoTrabalho(boolean permiteGrupoTrabalho) { this.permiteGrupoTrabalho = permiteGrupoTrabalho; }

    public boolean isProducao() {
        return nuTipoUnidadeGestora != null && nuTipoUnidadeGestora == PRODUCAO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoUnidadeGestora other)) return false;
        return Objects.equals(this.nuTipoUnidadeGestora, other.nuTipoUnidadeGestora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nuTipoUnidadeGestora);
    }
}