package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.GrupoTrabalhoEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.GrupoTrabalhoPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.GrupoTrabalhoRepository;
import br.gov.caixa.siaas_api.domain.grupotrabalho.model.GrupoTrabalho;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GrupoTrabalhoJpaAdapterTest {

    private static final Integer NU_GRUPO_1 = 1;
    private static final String NO_GRUPO_1 = "Grupo 001";

    private static final Integer NU_GRUPO_2 = 2;
    private static final String NO_GRUPO_2 = "Grupo 002";

    @Test
    void findAll_deveRetornarListaMapeada() {
        GrupoTrabalhoRepository repository = mock(GrupoTrabalhoRepository.class);
        GrupoTrabalhoPersistenceMapper mapper = mock(GrupoTrabalhoPersistenceMapper.class);
        GrupoTrabalhoJpaAdapter adapter = new GrupoTrabalhoJpaAdapter(repository, mapper);

        GrupoTrabalhoEntity e1 = new GrupoTrabalhoEntity();
        e1.setNuGrupoTrabalho(NU_GRUPO_1);
        e1.setNoGrupoTrabalho(NO_GRUPO_1);

        GrupoTrabalhoEntity e2 = new GrupoTrabalhoEntity();
        e2.setNuGrupoTrabalho(NU_GRUPO_2);
        e2.setNoGrupoTrabalho(NO_GRUPO_2);

        GrupoTrabalho d1 = new GrupoTrabalho();
        d1.setNuGrupoTrabalho(NU_GRUPO_1);
        d1.setNoGrupoTrabalho(NO_GRUPO_1);

        GrupoTrabalho d2 = new GrupoTrabalho();
        d2.setNuGrupoTrabalho(NU_GRUPO_2);
        d2.setNoGrupoTrabalho(NO_GRUPO_2);

        List<GrupoTrabalhoEntity> lista = List.of(e1, e2);
        when(repository.findAll()).thenReturn(lista);
        when(mapper.toDomain(e1)).thenReturn(d1);
        when(mapper.toDomain(e2)).thenReturn(d2);

        List<GrupoTrabalho> result = adapter.findAll();

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
        GrupoTrabalhoRepository repository = mock(GrupoTrabalhoRepository.class);
        GrupoTrabalhoPersistenceMapper mapper = mock(GrupoTrabalhoPersistenceMapper.class);
        GrupoTrabalhoJpaAdapter adapter = new GrupoTrabalhoJpaAdapter(repository, mapper);

        when(repository.findAll()).thenReturn(List.of());

        List<GrupoTrabalho> result = adapter.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository, times(1)).findAll();
        verifyNoInteractions(mapper);
        verifyNoMoreInteractions(repository);
    }
}