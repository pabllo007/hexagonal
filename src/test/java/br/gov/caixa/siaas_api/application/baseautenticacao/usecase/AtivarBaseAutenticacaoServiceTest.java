package br.gov.caixa.siaas_api.application.baseautenticacao.usecase;

import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoPort;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import br.gov.caixa.siaas_api.exceptions.NaoEncontradoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtivarBaseAutenticacaoServiceTest {

    @Mock
    private BaseAutenticacaoPort basePort;

    @InjectMocks
    private AtivarBaseAutenticacaoService service;

    @Test
    void ativar_deveLancarNaoEncontrado_quandoNaoExiste() {
        when(basePort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NaoEncontradoException.class, () -> service.ativar(1L));

        verify(basePort).findById(1L);
        verifyNoMoreInteractions(basePort);
    }

    @Test
    void ativar_deveSerIdempotente_quandoJaEstaAtiva() {
        BaseAutenticacao base = new BaseAutenticacao();
        base.setNuBaseAutenticacao(1L);
        base.setIcAtivo(SimNao.S);

        when(basePort.findById(1L)).thenReturn(Optional.of(base));

        BaseAutenticacao result = service.ativar(1L);

        assertSame(base, result);
        verify(basePort).findById(1L);
        verify(basePort, never()).update(any());
        verifyNoMoreInteractions(basePort);
    }

    @Test
    void ativar_deveAtivarEAtualizar_quandoEstavaInativa() {
        BaseAutenticacao base = new BaseAutenticacao();
        base.setNuBaseAutenticacao(1L);
        base.setIcAtivo(SimNao.N);

        when(basePort.findById(1L)).thenReturn(Optional.of(base));
        when(basePort.update(any(BaseAutenticacao.class))).thenAnswer(inv -> inv.getArgument(0));

        BaseAutenticacao result = service.ativar(1L);

        assertEquals(SimNao.S, result.getIcAtivo());

        ArgumentCaptor<BaseAutenticacao> captor = ArgumentCaptor.forClass(BaseAutenticacao.class);
        verify(basePort).findById(1L);
        verify(basePort).update(captor.capture());
        assertEquals(SimNao.S, captor.getValue().getIcAtivo());
        verifyNoMoreInteractions(basePort);
    }
}