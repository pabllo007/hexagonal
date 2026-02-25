package br.gov.caixa.siaas_api.domain.baseautenticacao.model;

import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseAutenticacaoTest {

    @Test
    void construtorVazio_deveCriarObjeto() {
        BaseAutenticacao b = new BaseAutenticacao();
        assertNotNull(b);

        assertNull(b.getNuBaseAutenticacao());
        assertNull(b.getNoBaseAutenticacao());
        assertNull(b.getIcRecurso());
        assertNull(b.getIcSinav());
        assertNull(b.getIcAtivo());
    }

    @Test
    void construtorCompleto_devePopularCamposCorretamente() {
        BaseAutenticacao b = new BaseAutenticacao(
                10L,
                "Base",
                SimNao.S,
                SimNao.N,
                SimNao.S
        );

        assertEquals(10L, b.getNuBaseAutenticacao());
        assertEquals("Base", b.getNoBaseAutenticacao());
        assertEquals(SimNao.S, b.getIcRecurso());
        assertEquals(SimNao.N, b.getIcSinav());
        assertEquals(SimNao.S, b.getIcAtivo());
        assertTrue(b.isAtiva());
    }

    @Test
    void settersEGetters_devemAtualizarCampos() {
        BaseAutenticacao b = new BaseAutenticacao();

        b.setNuBaseAutenticacao(1L);
        b.setNoBaseAutenticacao("Nome");
        b.setIcRecurso(SimNao.S);
        b.setIcSinav(SimNao.N);
        b.setIcAtivo(SimNao.N);

        assertEquals(1L, b.getNuBaseAutenticacao());
        assertEquals("Nome", b.getNoBaseAutenticacao());
        assertEquals(SimNao.S, b.getIcRecurso());
        assertEquals(SimNao.N, b.getIcSinav());
        assertEquals(SimNao.N, b.getIcAtivo());

        assertFalse(b.isAtiva());
    }

    @Test
    void isAtiva_deveRetornarTrue_quandoIcAtivoForS_mesmoMinusculo() {
        BaseAutenticacao b = new BaseAutenticacao();

        b.setIcAtivo(SimNao.S);
        assertTrue(b.isAtiva());

        b.setIcAtivo(SimNao.S);
        assertTrue(b.isAtiva());
    }


    @Test
    void inativar_deveSetarIcAtivoComoN() {
        BaseAutenticacao b = new BaseAutenticacao();
        b.setIcAtivo(SimNao.S);
        assertTrue(b.isAtiva());

        b.inativar();

        assertEquals(SimNao.N, b.getIcAtivo());
        assertFalse(b.isAtiva());
    }

    @Test
    void isAtiva_deveRetornarFalse_quandoIcAtivoNull() {
        BaseAutenticacao b = new BaseAutenticacao();
        b.setIcAtivo(null);
        assertFalse(b.isAtiva());
    }

    @Test
    void isAtiva_deveRetornarTrue_quandoIcAtivoForS() {
        BaseAutenticacao b = new BaseAutenticacao();
        b.setIcAtivo(SimNao.S);
        assertTrue(b.isAtiva());
    }
}