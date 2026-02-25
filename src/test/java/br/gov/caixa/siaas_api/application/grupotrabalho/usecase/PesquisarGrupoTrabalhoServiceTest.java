package br.gov.caixa.siaas_api.application.grupotrabalho.usecase;

import br.gov.caixa.siaas_api.application.grupotrabalho.port.out.GrupoTrabalhoPort;
import br.gov.caixa.siaas_api.domain.grupotrabalho.model.GrupoTrabalho;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PesquisarGrupoTrabalhoServiceTest {

    private static final Integer NU_GRUPO_1 = 1;
    private static final String NO_GRUPO_1 = "Grupo 001";

    @Test
    void pesquisar_deveRetornarListaDoPort() {
        GrupoTrabalhoPort port = mock(GrupoTrabalhoPort.class);
        PesquisarGrupoTrabalhoService service = new PesquisarGrupoTrabalhoService(port);

        GrupoTrabalho g = new GrupoTrabalho();
        g.setNuGrupoTrabalho(NU_GRUPO_1);
        g.setNoGrupoTrabalho(NO_GRUPO_1);

        List<GrupoTrabalho> lista = List.of(g);
        when(port.findAll()).thenReturn(lista);

        List<GrupoTrabalho> result = service.pesquisar();

        assertSame(lista, result);
        assertEquals(1, result.size());
        assertSame(g, result.get(0));

        verify(port, times(1)).findAll();
        verifyNoMoreInteractions(port);
    }
}