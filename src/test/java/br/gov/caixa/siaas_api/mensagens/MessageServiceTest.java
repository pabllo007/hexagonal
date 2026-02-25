package br.gov.caixa.siaas_api.mensagens;

import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MessageServiceTest {
    private static final String CHAVE = "chave";
    private static final String MENSAGEM_TRADUZIDA = "Mensagem traduzida";

    @Test
    void obterMensagemDeveDelegarParaMessageSource() {
        MessageSource fonte = mock(MessageSource.class);
        MessageService service = new MessageService(fonte);

        when(fonte.getMessage(eq(CHAVE),
                any(), any(Locale.class)))
                .thenReturn(MENSAGEM_TRADUZIDA);

        String mensagem = service.obterMensagem(CHAVE, 1L);

        assertEquals(MENSAGEM_TRADUZIDA, mensagem);
        verify(fonte).getMessage(eq(CHAVE),
                any(), any(Locale.class));
    }
}
