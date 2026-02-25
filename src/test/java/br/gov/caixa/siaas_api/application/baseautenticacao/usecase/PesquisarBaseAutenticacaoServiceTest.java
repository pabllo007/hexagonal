package br.gov.caixa.siaas_api.application.baseautenticacao.usecase;

import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoSearchPort;
import br.gov.caixa.siaas_api.application.baseautenticacao.query.BaseAutenticacaoSearchQuery;
import br.gov.caixa.siaas_api.application.comum.paginacao.PageResult;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PesquisarBaseAutenticacaoServiceTest {

    @Mock
    private BaseAutenticacaoSearchPort searchPort;

    @InjectMocks
    private PesquisarBaseAutenticacaoService service;

    @Test
    void pesquisar_deveDelegarParaSearchPort() {
        BaseAutenticacaoSearchQuery query = mock(BaseAutenticacaoSearchQuery.class);

        @SuppressWarnings("unchecked")
        PageResult<BaseAutenticacao> esperado = mock(PageResult.class);

        when(searchPort.search(query)).thenReturn(esperado);

        PageResult<BaseAutenticacao> result = service.pesquisar(query);

        assertSame(esperado, result);
        verify(searchPort).search(query);
        verifyNoMoreInteractions(searchPort);
    }
}