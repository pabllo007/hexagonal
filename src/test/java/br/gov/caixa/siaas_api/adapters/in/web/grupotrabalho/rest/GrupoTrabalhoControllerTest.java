package br.gov.caixa.siaas_api.adapters.in.web.grupotrabalho.rest;

import br.gov.caixa.siaas_api.adapters.in.web.grupotrabalho.dto.GrupoTrabalhoResponse;
import br.gov.caixa.siaas_api.adapters.in.web.grupotrabalho.mapper.GrupoTrabalhoWebMapper;
import br.gov.caixa.siaas_api.application.grupotrabalho.port.in.PesquisarGrupoTrabalhoUseCase;
import br.gov.caixa.siaas_api.domain.grupotrabalho.model.GrupoTrabalho;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GrupoTrabalhoControllerTest {

    private static final Integer NU_GRUPO_1 = 1;
    private static final String NO_GRUPO_1 = "Grupo 001";

    private static final Integer NU_GRUPO_2 = 2;
    private static final String NO_GRUPO_2 = "Grupo 002";

    private static final GrupoTrabalhoResponse R1 = new GrupoTrabalhoResponse(NU_GRUPO_1, NO_GRUPO_1);
    private static final GrupoTrabalhoResponse R2 = new GrupoTrabalhoResponse(NU_GRUPO_2, NO_GRUPO_2);

    @Test
    void pesquisar_deveChamarUseCase_eMapearLista() {
        PesquisarGrupoTrabalhoUseCase useCase = mock(PesquisarGrupoTrabalhoUseCase.class);
        GrupoTrabalhoWebMapper mapper = mock(GrupoTrabalhoWebMapper.class);
        GrupoTrabalhoController controller = new GrupoTrabalhoController(useCase, mapper);

        GrupoTrabalho d1 = new GrupoTrabalho();
        d1.setNuGrupoTrabalho(NU_GRUPO_1);
        d1.setNoGrupoTrabalho(NO_GRUPO_1);

        GrupoTrabalho d2 = new GrupoTrabalho();
        d2.setNuGrupoTrabalho(NU_GRUPO_2);
        d2.setNoGrupoTrabalho(NO_GRUPO_2);

        when(useCase.pesquisar()).thenReturn(List.of(d1, d2));
        when(mapper.toResponse(d1)).thenReturn(R1);
        when(mapper.toResponse(d2)).thenReturn(R2);

        List<GrupoTrabalhoResponse> result = controller.pesquisar();

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
        PesquisarGrupoTrabalhoUseCase useCase = mock(PesquisarGrupoTrabalhoUseCase.class);
        GrupoTrabalhoWebMapper mapper = mock(GrupoTrabalhoWebMapper.class);
        GrupoTrabalhoController controller = new GrupoTrabalhoController(useCase, mapper);

        RuntimeException ex = new RuntimeException("erro");
        when(useCase.pesquisar()).thenThrow(ex);

        RuntimeException thrown = assertThrows(RuntimeException.class, controller::pesquisar);

        assertSame(ex, thrown);

        verify(useCase, times(1)).pesquisar();
        verifyNoInteractions(mapper);
        verifyNoMoreInteractions(useCase);
    }
}