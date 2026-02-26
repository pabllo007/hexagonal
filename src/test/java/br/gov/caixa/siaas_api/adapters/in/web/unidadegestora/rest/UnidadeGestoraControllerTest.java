package br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.rest;

import br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.dto.PaginacaoRequestDTO;
import br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.dto.PaginacaoResponseDTO;
import br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.dto.UnidadeGestoraResponse;
import br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.mapper.UnidadeGestoraWebMapper;
import br.gov.caixa.siaas_api.application.comum.paginacao.PageResult;
import br.gov.caixa.siaas_api.application.unidadegestora.port.in.BuscarPorCodigoETipoUseCase;
import br.gov.caixa.siaas_api.application.unidadegestora.port.in.BuscarUnidadeGestoraPorIdUseCase;
import br.gov.caixa.siaas_api.application.unidadegestora.port.in.PesquisarUnidadeGestoraUseCase;
import br.gov.caixa.siaas_api.application.unidadegestora.query.UnidadeGestoraSearchQuery;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UnidadeGestoraControllerTest {

    private static final Long ID = 10L;
    private static final Long CODIGO = 1L;
    private static final Long TIPO = 1L;
    private static final String NOME_UG = "UG";
    private static final String NOME_UG_CENTRAL = "UG CENTRAL";
    private static final String EMAIL_CAIXA_POSTAL = "mail@xpto.gov.br";
    private static final String EMAIL_CAIXA_POSTAL_CENTRAL = "siaas@caixa.gov.br";
    private static final String FLAG_SIM = "S";
    private static final String SORT_NU_UNIDADE_GESTORA = "nuUnidadeGestora";
    private static final String DIRECAO_ASC = "ASC";
    private static final String DIRECAO_DESC = "DESC";

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
        PesquisarUnidadeGestoraUseCase pesquisarUseCase = mock(PesquisarUnidadeGestoraUseCase.class);
        UnidadeGestoraWebMapper mapper = mock(UnidadeGestoraWebMapper.class);
        UnidadeGestoraController controller = new UnidadeGestoraController(useCase, buscarPorCodigoETipoUseCase, pesquisarUseCase, mapper);

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
        verifyNoInteractions(pesquisarUseCase);
    }

    @Test
    void buscarPorId_devePropagarExcecao_quandoUseCaseLancar() {
        // arrange
        BuscarUnidadeGestoraPorIdUseCase useCase = mock(BuscarUnidadeGestoraPorIdUseCase.class);
        BuscarPorCodigoETipoUseCase buscarPorCodigoETipoUseCase = mock(BuscarPorCodigoETipoUseCase.class);
        PesquisarUnidadeGestoraUseCase pesquisarUseCase = mock(PesquisarUnidadeGestoraUseCase.class);
        UnidadeGestoraWebMapper mapper = mock(UnidadeGestoraWebMapper.class);
        UnidadeGestoraController controller = new UnidadeGestoraController(useCase, buscarPorCodigoETipoUseCase, pesquisarUseCase, mapper);

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
        verifyNoInteractions(pesquisarUseCase);
    }

    @Test
    void buscarPorCodigoETipo_deveChamarUseCase_eMapearResponse() {
        // arrange
        BuscarUnidadeGestoraPorIdUseCase buscarPorIdUseCase = mock(BuscarUnidadeGestoraPorIdUseCase.class);
        BuscarPorCodigoETipoUseCase buscarPorCodigoETipoUseCase = mock(BuscarPorCodigoETipoUseCase.class);
        PesquisarUnidadeGestoraUseCase pesquisarUseCase = mock(PesquisarUnidadeGestoraUseCase.class);
        UnidadeGestoraWebMapper mapper = mock(UnidadeGestoraWebMapper.class);
        UnidadeGestoraController controller = new UnidadeGestoraController(buscarPorIdUseCase, buscarPorCodigoETipoUseCase, pesquisarUseCase, mapper);

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
        verifyNoInteractions(pesquisarUseCase);
    }

    @Test
    void buscarPorCodigoETipo_devePropagarExcecao_quandoUseCaseLancar() {
        // arrange
        BuscarUnidadeGestoraPorIdUseCase buscarPorIdUseCase = mock(BuscarUnidadeGestoraPorIdUseCase.class);
        BuscarPorCodigoETipoUseCase buscarPorCodigoETipoUseCase = mock(BuscarPorCodigoETipoUseCase.class);
        PesquisarUnidadeGestoraUseCase pesquisarUseCase = mock(PesquisarUnidadeGestoraUseCase.class);
        UnidadeGestoraWebMapper mapper = mock(UnidadeGestoraWebMapper.class);
        UnidadeGestoraController controller = new UnidadeGestoraController(buscarPorIdUseCase, buscarPorCodigoETipoUseCase, pesquisarUseCase, mapper);

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
        verifyNoInteractions(pesquisarUseCase);
    }

    @Test
    void pesquisar_deveRetornarPaginacaoMapeada() {
        BuscarUnidadeGestoraPorIdUseCase buscarPorIdUseCase = mock(BuscarUnidadeGestoraPorIdUseCase.class);
        BuscarPorCodigoETipoUseCase buscarPorCodigoETipoUseCase = mock(BuscarPorCodigoETipoUseCase.class);
        PesquisarUnidadeGestoraUseCase pesquisarUseCase = mock(PesquisarUnidadeGestoraUseCase.class);
        UnidadeGestoraWebMapper mapper = mock(UnidadeGestoraWebMapper.class);
        UnidadeGestoraController controller = new UnidadeGestoraController(buscarPorIdUseCase, buscarPorCodigoETipoUseCase, pesquisarUseCase, mapper);

        UnidadeGestora domain = new UnidadeGestora();
        domain.setNuUnidadeGestora(ID);

        when(pesquisarUseCase.pesquisar(any())).thenReturn(new PageResult<>(
                java.util.List.of(domain),
                1,
                1,
                0,
                20,
                true,
                true
        ));
        when(mapper.toResponse(domain)).thenReturn(RESPONSE);

        PaginacaoResponseDTO<UnidadeGestoraResponse> result = controller.pesquisar(
                new br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.dto.UnidadeGestoraFilter(1, NOME_UG_CENTRAL, EMAIL_CAIXA_POSTAL_CENTRAL, 2, FLAG_SIM),
                new PaginacaoRequestDTO(0, 20, SORT_NU_UNIDADE_GESTORA, DIRECAO_ASC)
        );

        org.junit.jupiter.api.Assertions.assertEquals(1, result.content().size());
        org.junit.jupiter.api.Assertions.assertSame(RESPONSE, result.content().get(0));
        verify(pesquisarUseCase, times(1)).pesquisar(any());
        verify(mapper, times(1)).toResponse(domain);
        verifyNoInteractions(buscarPorIdUseCase, buscarPorCodigoETipoUseCase);
    }

    @Test
    void pesquisar_deveMontarQueryComPaginacaoNulaEFlagNula() {
        BuscarUnidadeGestoraPorIdUseCase buscarPorIdUseCase = mock(BuscarUnidadeGestoraPorIdUseCase.class);
        BuscarPorCodigoETipoUseCase buscarPorCodigoETipoUseCase = mock(BuscarPorCodigoETipoUseCase.class);
        PesquisarUnidadeGestoraUseCase pesquisarUseCase = mock(PesquisarUnidadeGestoraUseCase.class);
        UnidadeGestoraWebMapper mapper = mock(UnidadeGestoraWebMapper.class);
        UnidadeGestoraController controller = new UnidadeGestoraController(buscarPorIdUseCase, buscarPorCodigoETipoUseCase, pesquisarUseCase, mapper);

        when(pesquisarUseCase.pesquisar(any())).thenReturn(new PageResult<>(
                java.util.List.of(),
                0,
                0,
                0,
                20,
                true,
                true
        ));

        controller.pesquisar(
                new br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.dto.UnidadeGestoraFilter(1, NOME_UG, EMAIL_CAIXA_POSTAL, 2, null),
                null
        );

        ArgumentCaptor<UnidadeGestoraSearchQuery> captor = ArgumentCaptor.forClass(UnidadeGestoraSearchQuery.class);
        verify(pesquisarUseCase, times(1)).pesquisar(captor.capture());

        UnidadeGestoraSearchQuery query = captor.getValue();
        assertEquals(1, query.nuUnidade());
        assertEquals(NOME_UG, query.noUnidadeGestora());
        assertEquals(EMAIL_CAIXA_POSTAL, query.noCaixaPostal());
        assertEquals(2, query.nuTipoUnidadeGestora());
        assertEquals(null, query.icAtivo());
        assertEquals(null, query.page());
        assertEquals(null, query.size());
        assertEquals(null, query.sortBy());
        assertEquals(null, query.direction());
        verifyNoInteractions(buscarPorIdUseCase, buscarPorCodigoETipoUseCase, mapper);
    }

    @Test
    void pesquisar_deveConverterFlagParaSimNao() {
        BuscarUnidadeGestoraPorIdUseCase buscarPorIdUseCase = mock(BuscarUnidadeGestoraPorIdUseCase.class);
        BuscarPorCodigoETipoUseCase buscarPorCodigoETipoUseCase = mock(BuscarPorCodigoETipoUseCase.class);
        PesquisarUnidadeGestoraUseCase pesquisarUseCase = mock(PesquisarUnidadeGestoraUseCase.class);
        UnidadeGestoraWebMapper mapper = mock(UnidadeGestoraWebMapper.class);
        UnidadeGestoraController controller = new UnidadeGestoraController(buscarPorIdUseCase, buscarPorCodigoETipoUseCase, pesquisarUseCase, mapper);

        when(pesquisarUseCase.pesquisar(any())).thenReturn(new PageResult<>(
                java.util.List.of(),
                0,
                0,
                0,
                20,
                true,
                true
        ));

        controller.pesquisar(
                new br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.dto.UnidadeGestoraFilter(1, NOME_UG, null, null, FLAG_SIM),
                new PaginacaoRequestDTO(1, 5, SORT_NU_UNIDADE_GESTORA, DIRECAO_DESC)
        );

        ArgumentCaptor<UnidadeGestoraSearchQuery> captor = ArgumentCaptor.forClass(UnidadeGestoraSearchQuery.class);
        verify(pesquisarUseCase, times(1)).pesquisar(captor.capture());
        assertEquals(SimNao.SIM, captor.getValue().icAtivo());
    }
}
