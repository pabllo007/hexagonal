package br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.rest;

import br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.dto.UnidadeGestoraResponse;
import br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.mapper.UnidadeGestoraWebMapper;
import br.gov.caixa.siaas_api.application.unidadegestora.port.in.BuscarPorCodigoETipoUseCase;
import br.gov.caixa.siaas_api.application.unidadegestora.port.in.BuscarUnidadeGestoraPorIdUseCase;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UnidadeGestoraControllerTest {

    private static final Long ID = 10L;
    private static final Long CODIGO = 1L;
    private static final Long TIPO = 1L;

    private static final UnidadeGestoraResponse RESPONSE =
            new UnidadeGestoraResponse(
                    10L,
                    "Unidade 001",
                    true,
                    1,
                    2,
                    "Desenvolvimento",
                    3,
                    "CEDES",
                    "cedes@xpto.gov.br"
            );

    @Test
    void buscarPorId_deveChamarUseCase_eMapearResponse() {
        // arrange
        BuscarUnidadeGestoraPorIdUseCase useCase = mock(BuscarUnidadeGestoraPorIdUseCase.class);
        BuscarPorCodigoETipoUseCase buscarPorCodigoETipoUseCase = mock(BuscarPorCodigoETipoUseCase.class);
        UnidadeGestoraWebMapper mapper = mock(UnidadeGestoraWebMapper.class);
        UnidadeGestoraController controller = new UnidadeGestoraController(useCase, buscarPorCodigoETipoUseCase, mapper);

        UnidadeGestora domain = new UnidadeGestora();
        domain.setNuUnidadeGestora(ID);

        when(useCase.buscar(ID)).thenReturn(domain);
        when(mapper.toResponse(domain)).thenReturn(RESPONSE);

        // act
        UnidadeGestoraResponse result = controller.buscarPorId(ID);

        // assert
        assertSame(RESPONSE, result);

        verify(useCase, times(1)).buscar(ID);
        verify(mapper, times(1)).toResponse(domain);
        verifyNoMoreInteractions(useCase, mapper);
        verifyNoInteractions(buscarPorCodigoETipoUseCase);
    }

    @Test
    void buscarPorId_devePropagarExcecao_quandoUseCaseLancar() {
        // arrange
        BuscarUnidadeGestoraPorIdUseCase useCase = mock(BuscarUnidadeGestoraPorIdUseCase.class);
        BuscarPorCodigoETipoUseCase buscarPorCodigoETipoUseCase = mock(BuscarPorCodigoETipoUseCase.class);
        UnidadeGestoraWebMapper mapper = mock(UnidadeGestoraWebMapper.class);
        UnidadeGestoraController controller = new UnidadeGestoraController(useCase, buscarPorCodigoETipoUseCase, mapper);

        RuntimeException ex = new RuntimeException("erro");
        when(useCase.buscar(ID)).thenThrow(ex);

        // act
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> controller.buscarPorId(ID));

        // assert
        assertSame(ex, thrown);

        verify(useCase, times(1)).buscar(ID);
        verifyNoInteractions(mapper);
        verifyNoMoreInteractions(useCase);
        verifyNoInteractions(buscarPorCodigoETipoUseCase);
    }

    @Test
    void buscarPorCodigoETipo_deveChamarUseCase_eMapearResponse() {
        // arrange
        BuscarUnidadeGestoraPorIdUseCase buscarPorIdUseCase = mock(BuscarUnidadeGestoraPorIdUseCase.class);
        BuscarPorCodigoETipoUseCase buscarPorCodigoETipoUseCase = mock(BuscarPorCodigoETipoUseCase.class);
        UnidadeGestoraWebMapper mapper = mock(UnidadeGestoraWebMapper.class);
        UnidadeGestoraController controller = new UnidadeGestoraController(buscarPorIdUseCase, buscarPorCodigoETipoUseCase, mapper);

        UnidadeGestora domain = new UnidadeGestora();
        domain.setNuUnidadeGestora(ID);

        when(buscarPorCodigoETipoUseCase.buscarPorCodigoEhTipo(CODIGO, TIPO)).thenReturn(domain);
        when(mapper.toResponse(domain)).thenReturn(RESPONSE);

        // act
        UnidadeGestoraResponse result = controller.buscarPorCodigoETipo(CODIGO, TIPO);

        // assert
        assertSame(RESPONSE, result);

        verify(buscarPorCodigoETipoUseCase, times(1)).buscarPorCodigoEhTipo(CODIGO, TIPO);
        verify(mapper, times(1)).toResponse(domain);
        verifyNoMoreInteractions(buscarPorCodigoETipoUseCase, mapper);
        verifyNoInteractions(buscarPorIdUseCase);
    }

    @Test
    void buscarPorCodigoETipo_devePropagarExcecao_quandoUseCaseLancar() {
        // arrange
        BuscarUnidadeGestoraPorIdUseCase buscarPorIdUseCase = mock(BuscarUnidadeGestoraPorIdUseCase.class);
        BuscarPorCodigoETipoUseCase buscarPorCodigoETipoUseCase = mock(BuscarPorCodigoETipoUseCase.class);
        UnidadeGestoraWebMapper mapper = mock(UnidadeGestoraWebMapper.class);
        UnidadeGestoraController controller = new UnidadeGestoraController(buscarPorIdUseCase, buscarPorCodigoETipoUseCase, mapper);

        RuntimeException ex = new RuntimeException("erro ao buscar por código e tipo");
        when(buscarPorCodigoETipoUseCase.buscarPorCodigoEhTipo(CODIGO, TIPO)).thenThrow(ex);

        // act
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> controller.buscarPorCodigoETipo(CODIGO, TIPO)
        );

        // assert
        assertSame(ex, thrown);

        verify(buscarPorCodigoETipoUseCase, times(1)).buscarPorCodigoEhTipo(CODIGO, TIPO);
        verifyNoInteractions(mapper);
        verifyNoMoreInteractions(buscarPorCodigoETipoUseCase);
        verifyNoInteractions(buscarPorIdUseCase);
    }
}