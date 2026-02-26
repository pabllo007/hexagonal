package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.UnidadeGestoraEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.UnidadeGestoraPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.UnidadeGestoraRepository;
import br.gov.caixa.siaas_api.application.unidadegestora.query.UnidadeGestoraSearchQuery;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnidadeGestoraJpaAdapterTest {

    private static final Long NU_UNIDADE_GESTORA = 10L;
    private static final Long CODIGO = 1L;
    private static final Long TIPO = 2L;
    private static final String NOME_UG_CENTRAL = "UG CENTRAL";
    private static final String EMAIL_CAIXA_POSTAL = "siaas@caixa.gov.br";
    private static final String SORT_NU_UNIDADE_GESTORA = "nuUnidadeGestora";
    private static final String SORT_NU_TIPO_UNIDADE = "nuTipoUnidade";
    private static final String DIRECAO_ASC = "ASC";
    private static final String CAMPO_TIPO_UG = "tipoUnidadeGestora";
    private static final String CAMPO_NU_TIPO_UG = "nuTipoUnidadeGestora";
    private static final String SORT_PATH_NU_TIPO_UG = "tipoUnidadeGestora.nuTipoUnidadeGestora";

    @Test
    void findById_deveRetornarOptionalVazio_quandoRepositoryNaoEncontrar() {
        UnidadeGestoraRepository repository = mock(UnidadeGestoraRepository.class);
        UnidadeGestoraPersistenceMapper mapper = mock(UnidadeGestoraPersistenceMapper.class);
        UnidadeGestoraJpaAdapter adapter = new UnidadeGestoraJpaAdapter(repository, mapper);

        when(repository.findByNuUnidadeGestora(NU_UNIDADE_GESTORA)).thenReturn(Optional.empty());

        Optional<UnidadeGestora> result = adapter.findById(NU_UNIDADE_GESTORA);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository, times(1)).findByNuUnidadeGestora(NU_UNIDADE_GESTORA);
        verifyNoInteractions(mapper);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findById_deveMapearEntityParaDomain_quandoEncontrar() {
        UnidadeGestoraRepository repository = mock(UnidadeGestoraRepository.class);
        UnidadeGestoraPersistenceMapper mapper = mock(UnidadeGestoraPersistenceMapper.class);
        UnidadeGestoraJpaAdapter adapter = new UnidadeGestoraJpaAdapter(repository, mapper);

        UnidadeGestoraEntity entity = new UnidadeGestoraEntity();
        UnidadeGestora domain = new UnidadeGestora();
        domain.setNuUnidadeGestora(NU_UNIDADE_GESTORA);

        when(repository.findByNuUnidadeGestora(NU_UNIDADE_GESTORA))
                .thenReturn(Optional.of(entity));

        when(mapper.toDomain(entity)).thenReturn(domain);

        Optional<UnidadeGestora> result = adapter.findById(NU_UNIDADE_GESTORA);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertSame(domain, result.get());

        verify(repository, times(1)).findByNuUnidadeGestora(NU_UNIDADE_GESTORA);
        verify(mapper, times(1)).toDomain(entity);
        verifyNoMoreInteractions(repository, mapper);
    }

    // -------------------------------------------------------------------------
    // NOVOS TESTES: buscarPorCodigoEhTipo
    // -------------------------------------------------------------------------

    @Test
    void buscarPorCodigoEhTipo_deveRetornarOptionalVazio_quandoRepositoryNaoEncontrar() {
        UnidadeGestoraRepository repository = mock(UnidadeGestoraRepository.class);
        UnidadeGestoraPersistenceMapper mapper = mock(UnidadeGestoraPersistenceMapper.class);
        UnidadeGestoraJpaAdapter adapter = new UnidadeGestoraJpaAdapter(repository, mapper);

        when(repository.findByNuUnidadeEhTipoUnidadeGestora(CODIGO, TIPO))
                .thenReturn(Optional.empty());

        Optional<UnidadeGestora> result = adapter.buscarPorCodigoEhTipo(CODIGO, TIPO);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository, times(1))
                .findByNuUnidadeEhTipoUnidadeGestora(CODIGO, TIPO);

        verifyNoInteractions(mapper);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void buscarPorCodigoEhTipo_deveMapearEntityParaDomain_quandoEncontrar() {
        UnidadeGestoraRepository repository = mock(UnidadeGestoraRepository.class);
        UnidadeGestoraPersistenceMapper mapper = mock(UnidadeGestoraPersistenceMapper.class);
        UnidadeGestoraJpaAdapter adapter = new UnidadeGestoraJpaAdapter(repository, mapper);

        UnidadeGestoraEntity entity = new UnidadeGestoraEntity();
        UnidadeGestora domain = new UnidadeGestora();
        domain.setNuUnidadeGestora(CODIGO);

        when(repository.findByNuUnidadeEhTipoUnidadeGestora(CODIGO, TIPO))
                .thenReturn(Optional.of(entity));

        when(mapper.toDomain(entity)).thenReturn(domain);

        Optional<UnidadeGestora> result = adapter.buscarPorCodigoEhTipo(CODIGO, TIPO);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertSame(domain, result.get());

        verify(repository, times(1))
                .findByNuUnidadeEhTipoUnidadeGestora(CODIGO, TIPO);

        verify(mapper, times(1)).toDomain(entity);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void search_deveRetornarPageResultMapeado() {
        UnidadeGestoraRepository repository = mock(UnidadeGestoraRepository.class);
        UnidadeGestoraPersistenceMapper mapper = mock(UnidadeGestoraPersistenceMapper.class);
        UnidadeGestoraJpaAdapter adapter = new UnidadeGestoraJpaAdapter(repository, mapper);

        UnidadeGestoraEntity entity = new UnidadeGestoraEntity();
        UnidadeGestora domain = new UnidadeGestora();
        domain.setNuUnidadeGestora(NU_UNIDADE_GESTORA);

        when(repository.findAll(any(org.springframework.data.jpa.domain.Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(java.util.List.of(entity), PageRequest.of(0, 10), 1));
        when(mapper.toDomain(entity)).thenReturn(domain);

        var result = adapter.search(new UnidadeGestoraSearchQuery(1, NOME_UG_CENTRAL, EMAIL_CAIXA_POSTAL, 2, SimNao.SIM, 0, 10, SORT_NU_UNIDADE_GESTORA, DIRECAO_ASC));

        assertEquals(1, result.content().size());
        assertSame(domain, result.content().get(0));
        assertEquals(1, result.totalElements());
        verify(repository, times(1)).findAll(any(org.springframework.data.jpa.domain.Specification.class), any(org.springframework.data.domain.Pageable.class));
        verify(mapper, times(1)).toDomain(entity);
    }

    @Test
    void search_deveTraduzirOrdenacaoNuTipoUnidade() {
        UnidadeGestoraRepository repository = mock(UnidadeGestoraRepository.class);
        UnidadeGestoraPersistenceMapper mapper = mock(UnidadeGestoraPersistenceMapper.class);
        UnidadeGestoraJpaAdapter adapter = new UnidadeGestoraJpaAdapter(repository, mapper);

        when(repository.findAll(any(org.springframework.data.jpa.domain.Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(java.util.List.of(), PageRequest.of(0, 10), 0));

        adapter.search(new UnidadeGestoraSearchQuery(1, null, null, null, null, 0, 10, SORT_NU_TIPO_UNIDADE, DIRECAO_ASC));

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(repository, times(1)).findAll(any(org.springframework.data.jpa.domain.Specification.class), pageableCaptor.capture());

        String sortField = pageableCaptor.getValue().getSort().stream().findFirst().orElseThrow().getProperty();
        assertEquals(SORT_PATH_NU_TIPO_UG, sortField);
    }

    @Test
    void search_quandoSortByNulo_deveUsarOrdenacaoPadrao() {
        UnidadeGestoraRepository repository = mock(UnidadeGestoraRepository.class);
        UnidadeGestoraPersistenceMapper mapper = mock(UnidadeGestoraPersistenceMapper.class);
        UnidadeGestoraJpaAdapter adapter = new UnidadeGestoraJpaAdapter(repository, mapper);

        when(repository.findAll(any(org.springframework.data.jpa.domain.Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(java.util.List.of(), PageRequest.of(0, 10), 0));

        adapter.search(new UnidadeGestoraSearchQuery(1, null, null, null, null, 0, 10, null, DIRECAO_ASC));

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(repository, times(1)).findAll(any(org.springframework.data.jpa.domain.Specification.class), pageableCaptor.capture());

        Sort.Order order = pageableCaptor.getValue().getSort().stream().findFirst().orElseThrow();
        assertEquals(SORT_NU_UNIDADE_GESTORA, order.getProperty());
        assertEquals(Sort.Direction.ASC, order.getDirection());
    }

    @Test
    void search_quandoTipoInformado_deveAdicionarPredicadoNoSpecification() {
        UnidadeGestoraRepository repository = mock(UnidadeGestoraRepository.class);
        UnidadeGestoraPersistenceMapper mapper = mock(UnidadeGestoraPersistenceMapper.class);
        UnidadeGestoraJpaAdapter adapter = new UnidadeGestoraJpaAdapter(repository, mapper);

        when(repository.findAll(any(org.springframework.data.jpa.domain.Specification.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(java.util.List.of(), PageRequest.of(0, 10), 0));

        adapter.search(new UnidadeGestoraSearchQuery(1, null, null, 2, SimNao.SIM, 0, 10, SORT_NU_UNIDADE_GESTORA, DIRECAO_ASC));

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Specification<UnidadeGestoraEntity>> specCaptor = ArgumentCaptor.forClass(Specification.class);
        verify(repository, times(1)).findAll(specCaptor.capture(), any(Pageable.class));

        @SuppressWarnings("unchecked")
        Root<UnidadeGestoraEntity> root = mock(Root.class);
        CriteriaQuery<?> cq = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        @SuppressWarnings("unchecked")
        Path<Object> tipoPath = mock(Path.class);
        @SuppressWarnings("unchecked")
        Path<Object> nuTipoPath = mock(Path.class);
        Predicate predicate = mock(Predicate.class);

        when(root.get(CAMPO_TIPO_UG)).thenReturn((Path) tipoPath);
        when(tipoPath.get(CAMPO_NU_TIPO_UG)).thenReturn(nuTipoPath);
        when(cb.equal(nuTipoPath, 2)).thenReturn(predicate);

        specCaptor.getValue().toPredicate(root, cq, cb);

        verify(root, atLeastOnce()).get(CAMPO_TIPO_UG);
        verify(tipoPath, times(1)).get(CAMPO_NU_TIPO_UG);
        verify(cb, times(1)).equal(nuTipoPath, 2);
    }
}
