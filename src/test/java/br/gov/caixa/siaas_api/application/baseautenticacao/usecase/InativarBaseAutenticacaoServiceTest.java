package br.gov.caixa.siaas_api.application.baseautenticacao.usecase;

import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoPort;
import br.gov.caixa.siaas_api.application.modulo.port.out.ModuloConsultaPort;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import br.gov.caixa.siaas_api.exceptions.NaoEncontradoException;
import br.gov.caixa.siaas_api.exceptions.NegocioException;
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
class InativarBaseAutenticacaoServiceTest {

    @Mock
    private BaseAutenticacaoPort basePort;

    @Mock
    private ModuloConsultaPort moduloPort;

    @InjectMocks
    private InativarBaseAutenticacaoService service;

    @Test
    void inativar_deveLancarNaoEncontrado_quandoNaoExiste() {
        when(basePort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NaoEncontradoException.class, () -> service.inativar(1L));

        verify(basePort).findById(1L);
        verifyNoMoreInteractions(basePort);
        verifyNoInteractions(moduloPort);
    }

    @Test
    void inativar_deveSerIdempotente_quandoJaEstaInativa() {
        BaseAutenticacao base = new BaseAutenticacao();
        base.setNuBaseAutenticacao(1L);
        base.setIcAtivo(SimNao.N);

        when(basePort.findById(1L)).thenReturn(Optional.of(base));

        BaseAutenticacao result = service.inativar(1L);

        assertSame(base, result);
        verify(basePort).findById(1L);
        verifyNoMoreInteractions(basePort);
        verifyNoInteractions(moduloPort);
    }

    @Test
    void inativar_deveLancarNegocio_quandoEmUso() {
        BaseAutenticacao base = new BaseAutenticacao();
        base.setNuBaseAutenticacao(1L);
        base.setIcAtivo((SimNao.S));

        when(basePort.findById(1L)).thenReturn(Optional.of(base));
        when(moduloPort.existeModuloUsandoBaseAutenticacao(1L)).thenReturn(true);

        assertThrows(NegocioException.class, () -> service.inativar(1L));

        verify(basePort).findById(1L);
        verify(moduloPort).existeModuloUsandoBaseAutenticacao(1L);
        verify(basePort, never()).update(any());
        verifyNoMoreInteractions(basePort, moduloPort);
    }

    @Test
    void inativar_deveInativarEAtualizar_quandoOk() {
        BaseAutenticacao base = new BaseAutenticacao();
        base.setNuBaseAutenticacao(1L);
        base.setIcAtivo((SimNao.S));

        when(basePort.findById(1L)).thenReturn(Optional.of(base));
        when(moduloPort.existeModuloUsandoBaseAutenticacao(1L)).thenReturn(false);
        when(basePort.update(any(BaseAutenticacao.class))).thenAnswer(inv -> inv.getArgument(0));

        BaseAutenticacao result = service.inativar(1L);

        assertEquals(SimNao.N, result.getIcAtivo());

        ArgumentCaptor<BaseAutenticacao> captor = ArgumentCaptor.forClass(BaseAutenticacao.class);
        verify(basePort).findById(1L);
        verify(moduloPort).existeModuloUsandoBaseAutenticacao(1L);
        verify(basePort).update(captor.capture());
        assertEquals(SimNao.N, captor.getValue().getIcAtivo());

        verifyNoMoreInteractions(basePort, moduloPort);
    }
}