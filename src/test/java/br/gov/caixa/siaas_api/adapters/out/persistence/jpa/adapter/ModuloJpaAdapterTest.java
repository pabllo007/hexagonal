package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.ModuloRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModuloJpaAdapterTest {

    @Mock ModuloRepository repo;
    @InjectMocks ModuloJpaAdapter adapter;

    @Test
    void existeModuloUsandoBaseAutenticacao_deveDelegar() {
        when(repo.existeModuloUsandoBaseAutenticacao(7L)).thenReturn(true);

        assertTrue(adapter.existeModuloUsandoBaseAutenticacao(7L));

        verify(repo).existeModuloUsandoBaseAutenticacao(7L);
        verifyNoMoreInteractions(repo);
    }
}