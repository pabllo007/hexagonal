package br.gov.caixa.siaas_api.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import static org.junit.jupiter.api.Assertions.*;

class MessageSourceConfigTest {

    private final MessageSourceConfig config =
            new MessageSourceConfig();

    @Test
    void messageSourceDeveRetornarImplementacaoCorreta() {
        MessageSource fonte = config.messageSource();

        assertNotNull(fonte);
        assertTrue(fonte instanceof ReloadableResourceBundleMessageSource);
    }

    @Test
    void messageSource_deveConfigurarBasenameEEncoding() {
        MessageSourceConfig cfg = new MessageSourceConfig();
        MessageSource ms = cfg.messageSource();

        assertNotNull(ms);
        assertTrue(ms instanceof ReloadableResourceBundleMessageSource);

        ReloadableResourceBundleMessageSource r = (ReloadableResourceBundleMessageSource) ms;
        assertNotNull(r);
    }
}
