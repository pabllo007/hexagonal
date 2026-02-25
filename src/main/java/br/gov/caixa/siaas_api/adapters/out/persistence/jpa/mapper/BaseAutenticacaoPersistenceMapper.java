package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.BaseAutenticacaoEntity;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;

public class BaseAutenticacaoPersistenceMapper {

    public BaseAutenticacao toDomain(BaseAutenticacaoEntity entity) {
        if (entity == null) return null;

        BaseAutenticacao d = new BaseAutenticacao();
        d.setNuBaseAutenticacao(entity.getNuBaseAutenticacao());
        d.setNoBaseAutenticacao(entity.getNoBaseAutenticacao());
        d.setIcRecurso(entity.getIcRecurso());
        d.setIcSinav(entity.getIcSinav());
        d.setIcAtivo(entity.getIcAtivo());
        return d;
    }

    public BaseAutenticacaoEntity toEntity(BaseAutenticacao domain, BaseAutenticacaoEntity entity) {
        entity.setNuBaseAutenticacao(domain.getNuBaseAutenticacao());
        entity.setNoBaseAutenticacao(domain.getNoBaseAutenticacao());
        entity.setIcRecurso(domain.getIcRecurso());
        entity.setIcSinav(domain.getIcSinav());
        entity.setIcAtivo(domain.getIcAtivo());
        return entity;
    }

    public BaseAutenticacaoEntity toEntityForCreate(BaseAutenticacao domain, BaseAutenticacaoEntity entity) {
        entity.setNoBaseAutenticacao(domain.getNoBaseAutenticacao());
        entity.setIcRecurso(domain.getIcRecurso());
        entity.setIcSinav(domain.getIcSinav());
        entity.setIcAtivo(domain.getIcAtivo());
        return entity;
    }

    public BaseAutenticacaoEntity toEntityForUpdate(BaseAutenticacao domain, BaseAutenticacaoEntity entity) {
        entity.setNoBaseAutenticacao(domain.getNoBaseAutenticacao());
        entity.setIcRecurso(domain.getIcRecurso());
        entity.setIcSinav(domain.getIcSinav());
        entity.setIcAtivo(domain.getIcAtivo());
        return entity;
    }
}