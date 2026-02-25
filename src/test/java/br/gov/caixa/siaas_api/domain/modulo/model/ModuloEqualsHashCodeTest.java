package br.gov.caixa.siaas_api.domain.modulo.model;

import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ModuloEqualsHashCodeTest {

    @Test
    void equals_deveSerTrue_quandoMesmoObjeto() {
        Modulo m = Modulo.builder().nuModulo(1L).build();
        assertEquals(m, m);
    }

    @Test
    void equals_deveSerFalse_quandoNullOuClasseDiferente() {
        Modulo m = Modulo.builder().nuModulo(1L).build();
        assertNotEquals(m, null);
        assertNotEquals(m, "x");
    }

    @Test
    void equals_hashCode_deveSerTrue_quandoMesmoConteudo() {
        BaseAutenticacao base = new BaseAutenticacao();
        base.setNuBaseAutenticacao(10L);

        Date agora = new Date();

        Modulo m1 = Modulo.builder()
                .nuModulo(1L)
                .sgModulo("SIAAS")
                .noModulo("Modulo")
                .icPerfilEventual(SimNao.S)
                .icCentralizado(SimNao.N)
                .icMultiplosPerfis(SimNao.S)
                .icSigal(SimNao.N)
                .tsCadastro(agora)
                .coMatriculaCadastro("123")
                .baseAutenticacao(base)
                .nuProducao(99L)
                .build();

        Modulo m2 = Modulo.builder()
                .nuModulo(1L)
                .sgModulo("SIAAS")
                .noModulo("Modulo")
                .icPerfilEventual(SimNao.S)
                .icCentralizado(SimNao.N)
                .icMultiplosPerfis(SimNao.S)
                .icSigal(SimNao.N)
                .tsCadastro(agora)
                .coMatriculaCadastro("123")
                .baseAutenticacao(base)
                .nuProducao(99L)
                .build();

        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    void equals_deveSerFalse_quandoDiferente() {
        Modulo m1 = Modulo.builder().nuModulo(1L).sgModulo("A").build();
        Modulo m2 = Modulo.builder().nuModulo(2L).sgModulo("B").build();
        assertNotEquals(m1, m2);
    }
}