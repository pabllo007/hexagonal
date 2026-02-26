package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.UnidadeGestoraEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnidadeGestoraRepository
        extends JpaRepository<UnidadeGestoraEntity, Long>, JpaSpecificationExecutor<UnidadeGestoraEntity> {

    @Override
    @EntityGraph(attributePaths = {"tipoUnidadeGestora", "grupoTrabalho"})
    Page<UnidadeGestoraEntity> findAll(Specification<UnidadeGestoraEntity> spec, Pageable pageable);

    @Query("""
        select ug
        from UnidadeGestoraEntity ug
        join fetch ug.tipoUnidadeGestora t
        left join fetch ug.grupoTrabalho g
        where ug.nuUnidadeGestora = :nuUnidadeGestora
    """)
    Optional<UnidadeGestoraEntity> findByNuUnidadeGestora(Long nuUnidadeGestora);

    @Query("""
        select ug
        from UnidadeGestoraEntity ug
        join fetch ug.tipoUnidadeGestora t
        left join fetch ug.grupoTrabalho g
        where ug.nuUnidade = :nuUnidade 
            and ug.tipoUnidadeGestora.nuTipoUnidadeGestora = :tipo 
    """)
    Optional<UnidadeGestoraEntity> findByNuUnidadeEhTipoUnidadeGestora(Long nuUnidade, Long tipo);

}
