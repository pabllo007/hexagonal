package br.gov.caixa.siaas_api.mensagens;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageSource messageSource;

    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String obterMensagem(String chave, Object... argumentos) {
        return messageSource.getMessage(
                chave, argumentos, LocaleContextHolder.getLocale());
    }
}
