package br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseAutenticacaoFilterTest {

    private static final String BASE = "Base";
    private static final String BASE_TESTE = "Base Teste";

    @Test
    void deveCriarObjetoCorretamente() {
        BaseAutenticacaoFilter filter = new BaseAutenticacaoFilter(
                1L,
                BASE_TESTE,
                "S",
                "N",
                "S"
        );

        assertEquals(1L, filter.nuBaseAutenticacao());
        assertEquals(BASE_TESTE, filter.noBaseAutenticacao());
        assertEquals("S", filter.icRecurso());
        assertEquals("N", filter.icSinav());
        assertEquals("S", filter.icAtivo());
    }

    @Test
    void deveGerarEqualsCorretamente() {
        BaseAutenticacaoFilter f1 = new BaseAutenticacaoFilter(1L, BASE, "S", "N", "S");
        BaseAutenticacaoFilter f2 = new BaseAutenticacaoFilter(1L, BASE, "S", "N", "S");
        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());
    }
}