package br.gov.caixa.siaas_api.application.tipounidadegestora.usecase;

import br.gov.caixa.siaas_api.application.tipounidadegestora.port.out.TipoUnidadeGestoraPort;
import br.gov.caixa.siaas_api.domain.tipounidadegestora.model.TipoUnidadeGestora;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PesquisarTipoUnidadeGestoraServiceTest {

    private static final Integer NU_TIPO = 1;
    private static final String NO_TIPO = "Tipo 001";

    @Test
    void pesquisar_deveRetornarListaDoPort() {
        TipoUnidadeGestoraPort port = mock(TipoUnidadeGestoraPort.class);
        PesquisarTipoUnidadeGestoraService service = new PesquisarTipoUnidadeGestoraService(port);

        TipoUnidadeGestora t = new TipoUnidadeGestora();
        t.setNuTipoUnidadeGestora(NU_TIPO);
        t.setNoTipoUnidadeGestora(NO_TIPO);

        List<TipoUnidadeGestora> lista = List.of(t);
        when(port.findAll()).thenReturn(lista);

        List<TipoUnidadeGestora> result = service.pesquisar();

        assertSame(lista, result);
        assertEquals(1, result.size());
        assertSame(t, result.get(0));

        verify(port, times(1)).findAll();
        verifyNoMoreInteractions(port);
    }
}