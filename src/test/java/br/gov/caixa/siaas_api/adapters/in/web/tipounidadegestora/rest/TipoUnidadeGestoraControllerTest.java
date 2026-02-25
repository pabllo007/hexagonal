package br.gov.caixa.siaas_api.adapters.in.web.tipounidadegestora.rest;

import br.gov.caixa.siaas_api.adapters.in.web.tipounidadegestora.dto.TipoUnidadeGestoraResponse;
import br.gov.caixa.siaas_api.adapters.in.web.tipounidadegestora.mapper.TipoUnidadeGestoraWebMapper;
import br.gov.caixa.siaas_api.application.tipounidadegestora.port.in.PesquisarTipoUnidadeGestoraUseCase;
import br.gov.caixa.siaas_api.domain.tipounidadegestora.model.TipoUnidadeGestora;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TipoUnidadeGestoraControllerTest {

    private static final Integer NU_TIPO_1 = 1;
    private static final String NO_TIPO_1 = "Tipo 001";

    private static final Integer NU_TIPO_2 = 2;
    private static final String NO_TIPO_2 = "Tipo 002";

    private static final TipoUnidadeGestoraResponse R1 = new TipoUnidadeGestoraResponse(NU_TIPO_1, NO_TIPO_1);
    private static final TipoUnidadeGestoraResponse R2 = new TipoUnidadeGestoraResponse(NU_TIPO_2, NO_TIPO_2);

    @Test
    void pesquisar_deveChamarUseCase_eMapearLista() {
        PesquisarTipoUnidadeGestoraUseCase useCase = mock(PesquisarTipoUnidadeGestoraUseCase.class);
        TipoUnidadeGestoraWebMapper mapper = mock(TipoUnidadeGestoraWebMapper.class);
        TipoUnidadeGestoraController controller = new TipoUnidadeGestoraController(useCase, mapper);

        TipoUnidadeGestora d1 = new TipoUnidadeGestora();
        d1.setNuTipoUnidadeGestora(NU_TIPO_1);
        d1.setNoTipoUnidadeGestora(NO_TIPO_1);

        TipoUnidadeGestora d2 = new TipoUnidadeGestora();
        d2.setNuTipoUnidadeGestora(NU_TIPO_2);
        d2.setNoTipoUnidadeGestora(NO_TIPO_2);

        when(useCase.pesquisar()).thenReturn(List.of(d1, d2));
        when(mapper.toResponse(d1)).thenReturn(R1);
        when(mapper.toResponse(d2)).thenReturn(R2);

        List<TipoUnidadeGestoraResponse> result = controller.pesquisar();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertSame(R1, result.get(0));
        assertSame(R2, result.get(1));

        verify(useCase, times(1)).pesquisar();
        verify(mapper, times(1)).toResponse(d1);
        verify(mapper, times(1)).toResponse(d2);
        verifyNoMoreInteractions(useCase, mapper);
    }

    @Test
    void pesquisar_devePropagarExcecao_quandoUseCaseLancar() {
        PesquisarTipoUnidadeGestoraUseCase useCase = mock(PesquisarTipoUnidadeGestoraUseCase.class);
        TipoUnidadeGestoraWebMapper mapper = mock(TipoUnidadeGestoraWebMapper.class);
        TipoUnidadeGestoraController controller = new TipoUnidadeGestoraController(useCase, mapper);

        RuntimeException ex = new RuntimeException("erro");
        when(useCase.pesquisar()).thenThrow(ex);

        RuntimeException thrown = assertThrows(RuntimeException.class, controller::pesquisar);

        assertSame(ex, thrown);

        verify(useCase, times(1)).pesquisar();
        verifyNoInteractions(mapper);
        verifyNoMoreInteractions(useCase);
    }
}