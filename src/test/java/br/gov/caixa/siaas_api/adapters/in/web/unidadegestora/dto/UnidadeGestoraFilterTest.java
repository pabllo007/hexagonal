package br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnidadeGestoraFilterTest {

    @Test
    void deveCriarObjetoCorretamente() {
        UnidadeGestoraFilter filter = new UnidadeGestoraFilter(
                1234,
                "UNIDADE CENTRAL",
                "siaas@xpto.gov.br",
                1,
                "S"
        );

        assertEquals(1234, filter.nuUnidade());
        assertEquals("UNIDADE CENTRAL", filter.noUnidadeGestora());
        assertEquals("siaas@xpto.gov.br", filter.noCaixaPostal());
        assertEquals(1, filter.nuTipoUnidadeGestora());
        assertEquals("S", filter.icAtivo());
    }

    @Test
    void deveGerarEqualsCorretamente() {
        UnidadeGestoraFilter f1 = new UnidadeGestoraFilter(1234, "UNIDADE", "email@xpto.gov.br", 1, "S");
        UnidadeGestoraFilter f2 = new UnidadeGestoraFilter(1234, "UNIDADE", "email@xpto.gov.br", 1, "S");

        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());
    }
}
