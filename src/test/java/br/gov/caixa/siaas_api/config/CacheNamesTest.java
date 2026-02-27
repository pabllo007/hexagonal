package br.gov.caixa.siaas_api.config;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class CacheNamesTest {

    @Test
    void constantesDevemEstarCorretas() {
        assertEquals("grupoTrabalho", CacheNames.GRUPO_TRABALHO);
        assertEquals("tipoUnidadeGestora", CacheNames.TIPO_UNIDADE_GESTORA);
    }

    @Test
    void construtorPrivadoDeveLancarExcecao() throws Exception {
        Constructor<CacheNames> constructor = CacheNames.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));

        constructor.setAccessible(true);

        InvocationTargetException ex = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertInstanceOf(IllegalStateException.class, ex.getCause());
    }
}
