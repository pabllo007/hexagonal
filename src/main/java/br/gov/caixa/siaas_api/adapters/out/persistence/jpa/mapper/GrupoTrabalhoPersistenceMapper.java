package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.GrupoTrabalhoEntity;
import br.gov.caixa.siaas_api.domain.grupotrabalho.model.GrupoTrabalho;

public class GrupoTrabalhoPersistenceMapper {

    public GrupoTrabalho toDomain(GrupoTrabalhoEntity e) {
        if (e == null) return null;

        GrupoTrabalho d = new GrupoTrabalho();
        d.setNuGrupoTrabalho(e.getNuGrupoTrabalho());
        d.setNoGrupoTrabalho(e.getNoGrupoTrabalho());
        return d;
    }
}