package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.GrupoTrabalhoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoTrabalhoRepository
        extends JpaRepository<GrupoTrabalhoEntity, Long>, JpaSpecificationExecutor<GrupoTrabalhoEntity> {

}