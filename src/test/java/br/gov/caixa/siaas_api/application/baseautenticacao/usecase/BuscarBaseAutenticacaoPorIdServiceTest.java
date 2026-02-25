package br.gov.caixa.siaas_api.application.baseautenticacao.usecase;

import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoPort;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.exceptions.NaoEncontradoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarBaseAutenticacaoPorIdServiceTest {

    @Mock
    private BaseAutenticacaoPort basePort;

    @InjectMocks
    private BuscarBaseAutenticacaoPorIdService service;

    @Test
    void buscar_deveRetornarBase_quandoExiste() {
        BaseAutenticacao base = new BaseAutenticacao();
        base.setNuBaseAutenticacao(1L);

        when(basePort.findById(1L)).thenReturn(Optional.of(base));

        BaseAutenticacao result = service.buscar(1L);

        assertSame(base, result);
        verify(basePort).findById(1L);
        verifyNoMoreInteractions(basePort);
    }

    @Test
    void buscar_deveLancarNaoEncontrado_quandoNaoExiste() {
        when(basePort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NaoEncontradoException.class, () -> service.buscar(1L));

        verify(basePort).findById(1L);
        verifyNoMoreInteractions(basePort);
    }
}