package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.UnidadeGestoraEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.UnidadeGestoraPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.UnidadeGestoraRepository;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnidadeGestoraJpaAdapterTest {

    private static final Long NU_UNIDADE_GESTORA = 10L;

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

        when(repository.findByNuUnidadeGestora(NU_UNIDADE_GESTORA)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        Optional<UnidadeGestora> result = adapter.findById(NU_UNIDADE_GESTORA);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertSame(domain, result.get());

        verify(repository, times(1)).findByNuUnidadeGestora(NU_UNIDADE_GESTORA);
        verify(mapper, times(1)).toDomain(entity);
        verifyNoMoreInteractions(repository, mapper);
    }
}