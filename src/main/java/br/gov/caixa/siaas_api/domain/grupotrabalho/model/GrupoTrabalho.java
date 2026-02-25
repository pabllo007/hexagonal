package br.gov.caixa.siaas_api.domain.grupotrabalho.model;

import java.util.Objects;

public class GrupoTrabalho {

    public static final int CEPTI_SEGURANCA_BSB = 1;
    public static final int CEPTI_SEGURANCA_RJ = 2;
    public static final int CEPTI_SEGURANCA_SP = 3;
    public static final int CETEC_PEDES = 4;

    private Integer nuGrupoTrabalho;
    private String noGrupoTrabalho;

    public GrupoTrabalho() {}

    public GrupoTrabalho(Integer nuGrupoTrabalho, String noGrupoTrabalho) {
        this.nuGrupoTrabalho = nuGrupoTrabalho;
        this.noGrupoTrabalho = noGrupoTrabalho;
    }

    public Integer getNuGrupoTrabalho() { return nuGrupoTrabalho; }
    public void setNuGrupoTrabalho(Integer nuGrupoTrabalho) { this.nuGrupoTrabalho = nuGrupoTrabalho; }

    public String getNoGrupoTrabalho() { return noGrupoTrabalho; }
    public void setNoGrupoTrabalho(String noGrupoTrabalho) { this.noGrupoTrabalho = noGrupoTrabalho; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GrupoTrabalho other)) return false;
        return Objects.equals(this.nuGrupoTrabalho, other.nuGrupoTrabalho);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nuGrupoTrabalho);
    }
}