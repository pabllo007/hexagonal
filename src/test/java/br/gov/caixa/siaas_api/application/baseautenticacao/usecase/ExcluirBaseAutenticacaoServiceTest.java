package br.gov.caixa.siaas_api.application.baseautenticacao.usecase;

import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoDeletePort;
import br.gov.caixa.siaas_api.application.modulo.port.out.ModuloConsultaPort;
import br.gov.caixa.siaas_api.exceptions.NaoEncontradoException;
import br.gov.caixa.siaas_api.exceptions.NegocioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExcluirBaseAutenticacaoServiceTest {

    @Mock
    private BaseAutenticacaoDeletePort deletePort;

    @Mock
    private ModuloConsultaPort moduloPort;

    @InjectMocks
    private ExcluirBaseAutenticacaoService service;

    @Test
    void excluir_deveLancarNaoEncontrado_quandoNaoExiste() {
        when(deletePort.existsById(1L)).thenReturn(false);

        assertThrows(NaoEncontradoException.class, () -> service.excluir(1L));

        verify(deletePort).existsById(1L);
        verifyNoMoreInteractions(deletePort);
        verifyNoInteractions(moduloPort);
    }

    @Test
    void excluir_deveLancarNegocio_quandoEmUso() {
        when(deletePort.existsById(1L)).thenReturn(true);
        when(moduloPort.existeModuloUsandoBaseAutenticacao(1L)).thenReturn(true);

        assertThrows(NegocioException.class, () -> service.excluir(1L));

        verify(deletePort).existsById(1L);
        verify(moduloPort).existeModuloUsandoBaseAutenticacao(1L);
        verify(deletePort, never()).deleteById(anyLong());
        verifyNoMoreInteractions(deletePort, moduloPort);
    }

    @Test
    void excluir_deveDeletar_quandoExisteENaoEstaEmUso() {
        when(deletePort.existsById(1L)).thenReturn(true);
        when(moduloPort.existeModuloUsandoBaseAutenticacao(1L)).thenReturn(false);

        service.excluir(1L);

        verify(deletePort).existsById(1L);
        verify(moduloPort).existeModuloUsandoBaseAutenticacao(1L);
        verify(deletePort).deleteById(1L);
        verifyNoMoreInteractions(deletePort, moduloPort);
    }
}