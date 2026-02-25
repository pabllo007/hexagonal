package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.BaseAutenticacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseAutenticacaoValidationJpaAdapterTest {

    @Mock BaseAutenticacaoRepository repo;
    @InjectMocks BaseAutenticacaoValidationJpaAdapter adapter;

    @Test
    void existsByNomeIgnoreCase_deveDelegar() {
        when(repo.existsByNoBaseAutenticacaoIgnoreCase("Base")).thenReturn(true);
        assertTrue(adapter.existsByNomeIgnoreCase("Base"));
        verify(repo).existsByNoBaseAutenticacaoIgnoreCase("Base");
        verifyNoMoreInteractions(repo);
    }

    @Test
    void existsByNomeIgnoreCaseAndIdNot_deveDelegar() {
        when(repo.existsByNoBaseAutenticacaoIgnoreCaseAndNuBaseAutenticacaoNot("Base", 1L)).thenReturn(true);
        assertTrue(adapter.existsByNomeIgnoreCaseAndIdNot("Base", 1L));
        verify(repo).existsByNoBaseAutenticacaoIgnoreCaseAndNuBaseAutenticacaoNot("Base", 1L);
        verifyNoMoreInteractions(repo);
    }
}