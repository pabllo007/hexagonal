package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.BaseAutenticacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseAutenticacaoRepository
        extends JpaRepository<BaseAutenticacaoEntity, Long>, JpaSpecificationExecutor<BaseAutenticacaoEntity> {
    Optional<BaseAutenticacaoEntity> findByNoBaseAutenticacaoIgnoreCase(String noBaseAutenticacao);
    boolean existsByNoBaseAutenticacao(String noBaseAutenticacao);
    BaseAutenticacaoEntity findByNoBaseAutenticacao(String noBaseAutenticacao);
    boolean existsByNoBaseAutenticacaoIgnoreCase(String noBaseAutenticacao);
    boolean existsByNoBaseAutenticacaoIgnoreCaseAndNuBaseAutenticacaoNot(String noBaseAutenticacao, Long nuBaseAutenticacao);
}