package br.gov.caixa.siaas_api.application.baseautenticacao.usecase;

import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoPort;
import br.gov.caixa.siaas_api.application.baseautenticacao.port.out.BaseAutenticacaoValidationPort;
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
class AtualizarBaseAutenticacaoServiceTest {

    @Mock
    private BaseAutenticacaoPort basePort;

    @Mock
    private BaseAutenticacaoValidationPort validationPort;

    @InjectMocks
    private AtualizarBaseAutenticacaoService service;

    @Test
    void atualizar_deveLancarNaoEncontrado_quandoNaoExiste() {
        when(basePort.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NaoEncontradoException.class,
                () -> service.atualizar(1L, new BaseAutenticacao()));

        verify(basePort).findById(1L);
        verifyNoMoreInteractions(basePort);
        verifyNoInteractions(validationPort);
    }

    @Test
    void atualizar_deveLancarNegocio_quandoNomeDuplicado() {
        BaseAutenticacao atual = new BaseAutenticacao();
        atual.setNuBaseAutenticacao(1L);
        atual.setIcAtivo(SimNao.S);

        BaseAutenticacao entrada = new BaseAutenticacao();
        entrada.setNoBaseAutenticacao("Base");

        when(basePort.findById(1L)).thenReturn(Optional.of(atual));
        when(validationPort.existsByNomeIgnoreCaseAndIdNot("Base", 1L)).thenReturn(true);

        assertThrows(NegocioException.class, () -> service.atualizar(1L, entrada));

        verify(basePort).findById(1L);
        verify(validationPort).existsByNomeIgnoreCaseAndIdNot("Base", 1L);
        verify(basePort, never()).update(any());
        verifyNoMoreInteractions(basePort, validationPort);
    }

    @Test
    void atualizar_devePreservarIcAtivoEForcarId_antesDeAtualizar() {
        BaseAutenticacao atual = new BaseAutenticacao();
        atual.setNuBaseAutenticacao(1L);
        atual.setIcAtivo((SimNao.N));

        BaseAutenticacao entrada = new BaseAutenticacao();
        entrada.setNuBaseAutenticacao(999L);
        entrada.setNoBaseAutenticacao("Nova");
        entrada.setIcAtivo(SimNao.S);

        when(basePort.findById(1L)).thenReturn(Optional.of(atual));
        when(validationPort.existsByNomeIgnoreCaseAndIdNot("Nova", 1L)).thenReturn(false);
        when(basePort.update(any(BaseAutenticacao.class))).thenAnswer(inv -> inv.getArgument(0));

        BaseAutenticacao result = service.atualizar(1L, entrada);

        assertEquals(1L, result.getNuBaseAutenticacao());
        assertEquals(SimNao.N, result.getIcAtivo());
        assertEquals("Nova", result.getNoBaseAutenticacao());

        ArgumentCaptor<BaseAutenticacao> captor = ArgumentCaptor.forClass(BaseAutenticacao.class);
        verify(basePort).findById(1L);
        verify(validationPort).existsByNomeIgnoreCaseAndIdNot("Nova", 1L);
        verify(basePort).update(captor.capture());

        BaseAutenticacao enviado = captor.getValue();
        assertEquals(1L, enviado.getNuBaseAutenticacao());
        assertEquals(SimNao.N, enviado.getIcAtivo());
        assertEquals("Nova", enviado.getNoBaseAutenticacao());

        verifyNoMoreInteractions(basePort, validationPort);
    }
}