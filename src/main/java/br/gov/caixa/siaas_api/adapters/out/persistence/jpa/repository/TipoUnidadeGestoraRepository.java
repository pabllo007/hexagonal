package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.TipoUnidadeGestoraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoUnidadeGestoraRepository
        extends JpaRepository<TipoUnidadeGestoraEntity, Long>, JpaSpecificationExecutor<TipoUnidadeGestoraEntity> {

}