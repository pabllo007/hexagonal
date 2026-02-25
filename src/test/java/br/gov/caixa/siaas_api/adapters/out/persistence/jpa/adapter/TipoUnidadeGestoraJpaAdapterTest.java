package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.TipoUnidadeGestoraEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.TipoUnidadeGestoraPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.TipoUnidadeGestoraRepository;
import br.gov.caixa.siaas_api.domain.tipounidadegestora.model.TipoUnidadeGestora;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TipoUnidadeGestoraJpaAdapterTest {

    private static final Integer NU_TIPO_1 = 1;
    private static final String NO_TIPO_1 = "Tipo 001";

    private static final Integer NU_TIPO_2 = 2;
    private static final String NO_TIPO_2 = "Tipo 002";

    @Test
    void findAll_deveRetornarListaMapeada() {
        TipoUnidadeGestoraRepository repository = mock(TipoUnidadeGestoraRepository.class);
        TipoUnidadeGestoraPersistenceMapper mapper = mock(TipoUnidadeGestoraPersistenceMapper.class);
        TipoUnidadeGestoraJpaAdapter adapter = new TipoUnidadeGestoraJpaAdapter(repository, mapper);

        TipoUnidadeGestoraEntity e1 = new TipoUnidadeGestoraEntity();
        e1.setNuTipoUnidadeGestora(NU_TIPO_1);
        e1.setNoTipoUnidadeGestora(NO_TIPO_1);

        TipoUnidadeGestoraEntity e2 = new TipoUnidadeGestoraEntity();
        e2.setNuTipoUnidadeGestora(NU_TIPO_2);
        e2.setNoTipoUnidadeGestora(NO_TIPO_2);

        TipoUnidadeGestora d1 = new TipoUnidadeGestora();
        d1.setNuTipoUnidadeGestora(NU_TIPO_1);
        d1.setNoTipoUnidadeGestora(NO_TIPO_1);

        TipoUnidadeGestora d2 = new TipoUnidadeGestora();
        d2.setNuTipoUnidadeGestora(NU_TIPO_2);
        d2.setNoTipoUnidadeGestora(NO_TIPO_2);

        List<TipoUnidadeGestoraEntity> lista = List.of(e1, e2);
        when(repository.findAll()).thenReturn(lista);
        when(mapper.toDomain(e1)).thenReturn(d1);
        when(mapper.toDomain(e2)).thenReturn(d2);

        List<TipoUnidadeGestora> result = adapter.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertSame(d1, result.get(0));
        assertSame(d2, result.get(1));

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDomain(e1);
        verify(mapper, times(1)).toDomain(e2);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void findAll_deveRetornarListaVazia_quandoRepositoryRetornarVazio() {
        TipoUnidadeGestoraRepository repository = mock(TipoUnidadeGestoraRepository.class);
        TipoUnidadeGestoraPersistenceMapper mapper = mock(TipoUnidadeGestoraPersistenceMapper.class);
        TipoUnidadeGestoraJpaAdapter adapter = new TipoUnidadeGestoraJpaAdapter(repository, mapper);

        when(repository.findAll()).thenReturn(List.of());

        List<TipoUnidadeGestora> result = adapter.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository, times(1)).findAll();
        verifyNoInteractions(mapper);
        verifyNoMoreInteractions(repository);
    }
}