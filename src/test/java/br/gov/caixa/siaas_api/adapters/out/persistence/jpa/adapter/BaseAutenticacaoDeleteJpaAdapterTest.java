package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.BaseAutenticacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseAutenticacaoDeleteJpaAdapterTest {

    @Mock BaseAutenticacaoRepository repo;
    @InjectMocks BaseAutenticacaoDeleteJpaAdapter adapter;

    @Test
    void existsById_deveDelegar() {
        when(repo.existsById(1L)).thenReturn(true);
        assertTrue(adapter.existsById(1L));
        verify(repo).existsById(1L);
        verifyNoMoreInteractions(repo);
    }

    @Test
    void deleteById_deveDelegar() {
        adapter.deleteById(1L);
        verify(repo).deleteById(1L);
        verifyNoMoreInteractions(repo);
    }
}