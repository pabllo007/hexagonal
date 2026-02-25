package br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.rest;

import br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.dto.UnidadeGestoraResponse;
import br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.mapper.UnidadeGestoraWebMapper;
import br.gov.caixa.siaas_api.application.unidadegestora.port.in.BuscarUnidadeGestoraPorIdUseCase;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnidadeGestoraControllerTest {

    private static final Long ID = 10L;
    private static final UnidadeGestoraResponse RESPONSE =
            new UnidadeGestoraResponse(10L, "Unidade 001", "Desenvolvimento", true);

    @Test
    void buscarPorId_deveChamarUseCase_eMapearResponse() {
        BuscarUnidadeGestoraPorIdUseCase useCase = mock(BuscarUnidadeGestoraPorIdUseCase.class);
        UnidadeGestoraWebMapper mapper = mock(UnidadeGestoraWebMapper.class);
        UnidadeGestoraController controller = new UnidadeGestoraController(useCase, mapper);

        UnidadeGestora domain = new UnidadeGestora();
        domain.setNuUnidadeGestora(ID);

        when(useCase.buscar(ID)).thenReturn(domain);
        when(mapper.toResponse(domain)).thenReturn(RESPONSE);

        UnidadeGestoraResponse result = controller.buscarPorId(ID);

        assertSame(RESPONSE, result);

        verify(useCase, times(1)).buscar(ID);
        verify(mapper, times(1)).toResponse(domain);
        verifyNoMoreInteractions(useCase, mapper);
    }

    @Test
    void buscarPorId_devePropagarExcecao_quandoUseCaseLancar() {
        BuscarUnidadeGestoraPorIdUseCase useCase = mock(BuscarUnidadeGestoraPorIdUseCase.class);
        UnidadeGestoraWebMapper mapper = mock(UnidadeGestoraWebMapper.class);
        UnidadeGestoraController controller = new UnidadeGestoraController(useCase, mapper);

        RuntimeException ex = new RuntimeException("erro");
        when(useCase.buscar(ID)).thenThrow(ex);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> controller.buscarPorId(ID));

        assertSame(ex, thrown);

        verify(useCase, times(1)).buscar(ID);
        verifyNoInteractions(mapper);
        verifyNoMoreInteractions(useCase);
    }
}