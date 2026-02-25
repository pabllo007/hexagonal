package br.gov.caixa.siaas_api.application.unidadegestora.usecase;

import br.gov.caixa.siaas_api.application.unidadegestora.port.out.UnidadeGestoraPort;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;
import br.gov.caixa.siaas_api.exceptions.NaoEncontradoException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarUnidadeGestoraPorIdServiceTest {

    private static final Long NU_UNIDADE_GESTORA = 10L;
    private static final String CODE_NOT_FOUND = "error.baseautenticacao.not-found";

    @Test
    void buscar_deveRetornarUnidadeGestora_quandoEncontrar() {
        UnidadeGestoraPort port = mock(UnidadeGestoraPort.class);
        BuscarUnidadeGestoraPorIdService service = new BuscarUnidadeGestoraPorIdService(port);

        UnidadeGestora ug = new UnidadeGestora();
        ug.setNuUnidadeGestora(NU_UNIDADE_GESTORA);

        when(port.findById(NU_UNIDADE_GESTORA)).thenReturn(Optional.of(ug));

        UnidadeGestora result = service.buscar(NU_UNIDADE_GESTORA);

        assertSame(ug, result);
        verify(port, times(1)).findById(NU_UNIDADE_GESTORA);
        verifyNoMoreInteractions(port);
    }

    @Test
    void buscar_deveLancarNaoEncontradoException_quandoNaoEncontrar() {
        UnidadeGestoraPort port = mock(UnidadeGestoraPort.class);
        BuscarUnidadeGestoraPorIdService service = new BuscarUnidadeGestoraPorIdService(port);

        when(port.findById(NU_UNIDADE_GESTORA)).thenReturn(Optional.empty());

        NaoEncontradoException ex = assertThrows(NaoEncontradoException.class, () -> service.buscar(NU_UNIDADE_GESTORA));

        assertNotNull(ex);
        assertEquals(CODE_NOT_FOUND, ex.getCode());
        verify(port, times(1)).findById(NU_UNIDADE_GESTORA);
        verifyNoMoreInteractions(port);
    }
}