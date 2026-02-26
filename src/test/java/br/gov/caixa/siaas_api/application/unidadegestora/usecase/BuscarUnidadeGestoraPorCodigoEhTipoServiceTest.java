package br.gov.caixa.siaas_api.application.unidadegestora.usecase;

import br.gov.caixa.siaas_api.application.unidadegestora.port.out.UnidadeGestoraPort;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;
import br.gov.caixa.siaas_api.exceptions.NaoEncontradoException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarUnidadeGestoraPorCodigoEhTipoServiceTest {

    private static final Long CODIGO = 100L;
    private static final Long TIPO = 2L;
    private static final String CODE_NOT_FOUND = "error.baseautenticacao.not-found";

    @Test
    void buscarPorCodigoEhTipo_deveRetornarUnidadeGestora_quandoEncontrar() {
        UnidadeGestoraPort port = mock(UnidadeGestoraPort.class);
        BuscarUnidadeGestoraPorCodigoEhTipoService service =
                new BuscarUnidadeGestoraPorCodigoEhTipoService(port);

        UnidadeGestora ug = new UnidadeGestora();
        ug.setNuUnidadeGestora(CODIGO);

        when(port.buscarPorCodigoEhTipo(CODIGO, TIPO)).thenReturn(Optional.of(ug));

        UnidadeGestora result = service.buscarPorCodigoEhTipo(CODIGO, TIPO);

        assertSame(ug, result);
        verify(port, times(1)).buscarPorCodigoEhTipo(CODIGO, TIPO);
        verifyNoMoreInteractions(port);
    }

    @Test
    void buscarPorCodigoEhTipo_deveLancarNaoEncontradoException_quandoNaoEncontrar() {
        UnidadeGestoraPort port = mock(UnidadeGestoraPort.class);
        BuscarUnidadeGestoraPorCodigoEhTipoService service =
                new BuscarUnidadeGestoraPorCodigoEhTipoService(port);

        when(port.buscarPorCodigoEhTipo(CODIGO, TIPO)).thenReturn(Optional.empty());

        NaoEncontradoException ex = assertThrows(
                NaoEncontradoException.class,
                () -> service.buscarPorCodigoEhTipo(CODIGO, TIPO)
        );

        assertNotNull(ex);
        assertEquals(CODE_NOT_FOUND, ex.getCode());
        verify(port, times(1)).buscarPorCodigoEhTipo(CODIGO, TIPO);
        verifyNoMoreInteractions(port);
    }
}