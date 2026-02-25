package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.GrupoTrabalhoEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.TipoUnidadeGestoraEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.UnidadeGestoraEntity;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import br.gov.caixa.siaas_api.domain.grupotrabalho.model.GrupoTrabalho;
import br.gov.caixa.siaas_api.domain.tipounidadegestora.model.TipoUnidadeGestora;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;

public class UnidadeGestoraPersistenceMapper {

    public UnidadeGestora toDomain(UnidadeGestoraEntity e) {
        if (e == null) return null;

        UnidadeGestora d = new UnidadeGestora();
        d.setNuUnidadeGestora(e.getNuUnidadeGestora());

        d.setNuUnidade(e.getNuUnidade());
        d.setNuNatural(e.getNuNatural());

        d.setNoCaixaPostal(e.getNoCaixaPostal());

        TipoUnidadeGestoraEntity tipoEntity = e.getTipoUnidadeGestora();
        if (tipoEntity != null) {
            TipoUnidadeGestora tipoRef = new TipoUnidadeGestora();
            tipoRef.setNuTipoUnidadeGestora(tipoEntity.getNuTipoUnidadeGestora());
            tipoRef.setNoTipoUnidadeGestora(tipoEntity.getNoTipoUnidadeGestora());
            SimNao icGrupo = tipoEntity.getIcGrupoTrabalho();
            tipoRef.setPermiteGrupoTrabalho(icGrupo != null && icGrupo.isSim());
            d.setTipo(tipoRef);
        } else {
            d.setTipo(null);
        }

        GrupoTrabalhoEntity grupoEntity = e.getGrupoTrabalho();
        if (grupoEntity != null) {
            GrupoTrabalho grupoRef = new GrupoTrabalho();
            grupoRef.setNuGrupoTrabalho(grupoEntity.getNuGrupoTrabalho());
            grupoRef.setNoGrupoTrabalho(grupoEntity.getNoGrupoTrabalho());
            d.setGrupoTrabalho(grupoRef);
        } else {
            d.setGrupoTrabalho(null);
        }

        return d;
    }
}