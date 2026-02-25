package br.gov.caixa.siaas_api.application.baseautenticacao.usecase;

import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoPort;
import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoValidationPort;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import br.gov.caixa.siaas_api.exceptions.NegocioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarBaseAutenticacaoServiceTest {

    @Mock
    private BaseAutenticacaoPort basePort;

    @Mock
    private BaseAutenticacaoValidationPort validationPort;

    @InjectMocks
    private CriarBaseAutenticacaoService service;

    @Test
    void criar_deveLancarNegocio_quandoNomeDuplicado() {
        BaseAutenticacao entrada = new BaseAutenticacao();
        entrada.setNoBaseAutenticacao("Base");

        when(validationPort.existsByNomeIgnoreCase("Base")).thenReturn(true);

        assertThrows(NegocioException.class, () -> service.criar(entrada));

        verify(validationPort).existsByNomeIgnoreCase("Base");
        verifyNoMoreInteractions(validationPort);
        verifyNoInteractions(basePort);
    }

    @Test
    void criar_deveSetarIcAtivoS_eChamarCreate_quandoOk() {
        BaseAutenticacao entrada = new BaseAutenticacao();
        entrada.setNoBaseAutenticacao("Base");
        entrada.setIcAtivo((SimNao.N));

        when(validationPort.existsByNomeIgnoreCase("Base")).thenReturn(false);
        when(basePort.create(any(BaseAutenticacao.class))).thenAnswer(inv -> inv.getArgument(0));

        BaseAutenticacao result = service.criar(entrada);

        assertEquals(SimNao.S, result.getIcAtivo());

        ArgumentCaptor<BaseAutenticacao> captor = ArgumentCaptor.forClass(BaseAutenticacao.class);
        verify(validationPort).existsByNomeIgnoreCase("Base");
        verify(basePort).create(captor.capture());
        assertEquals(SimNao.S, captor.getValue().getIcAtivo());
        verifyNoMoreInteractions(validationPort, basePort);
    }
}