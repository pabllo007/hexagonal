package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.ModuloEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuloRepository
        extends JpaRepository<ModuloEntity, Long>, JpaSpecificationExecutor<ModuloEntity> {

    boolean existsByBaseAutenticacaoNuBaseAutenticacao(Long nuBaseAutenticacao);

    @Query("""
        select case when count(m) > 0 then true else false end
        from ModuloEntity m
        where m.baseAutenticacao.nuBaseAutenticacao = :idBase
    """)
    boolean existeModuloUsandoBaseAutenticacao(@Param("idBase") Long idBase);
}