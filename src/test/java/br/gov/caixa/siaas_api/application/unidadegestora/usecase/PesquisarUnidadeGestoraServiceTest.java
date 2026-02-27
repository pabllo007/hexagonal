package br.gov.caixa.siaas_api.application.unidadegestora.usecase;

import br.gov.caixa.siaas_api.application.comum.paginacao.PageResult;
import br.gov.caixa.siaas_api.application.unidadegestora.port.out.UnidadeGestoraPort;
import br.gov.caixa.siaas_api.application.unidadegestora.query.UnidadeGestoraSearchQuery;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class PesquisarUnidadeGestoraServiceTest {

    private static final String NOME_UG = "UG";
    private static final String EMAIL_CAIXA_POSTAL = "mail@xpto.gov.br";
    private static final String SORT_NU_UNIDADE = "nuUnidade";
    private static final String DIRECAO_ASC = "ASC";

    @Test
    void pesquisar_deveDelegarParaPortESeguirRetorno() {
        UnidadeGestoraPort port = mock(UnidadeGestoraPort.class);
        PesquisarUnidadeGestoraService service = new PesquisarUnidadeGestoraService(port);

        UnidadeGestoraSearchQuery query = new UnidadeGestoraSearchQuery(1, NOME_UG, EMAIL_CAIXA_POSTAL, 2, null, 0, 20, SORT_NU_UNIDADE, DIRECAO_ASC);
        PageResult<UnidadeGestora> esperado = new PageResult<>(List.of(), 0, 0, 0, 20, true, true);

        when(port.search(query)).thenReturn(esperado);

        PageResult<UnidadeGestora> result = service.pesquisar(query);

        assertSame(esperado, result);
        verify(port, times(1)).search(query);
        verifyNoMoreInteractions(port);
    }
}
