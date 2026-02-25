package br.gov.caixa.siaas_api.domain.modulo.model;

import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ModuloTest {

    @Test
    void builderDevePopularCamposCorretamente() {
        BaseAutenticacao base = new BaseAutenticacao();
        base.setNuBaseAutenticacao(1L);
        base.setNoBaseAutenticacao("BASE_AUT");

        Date agora = new Date();

        Modulo modulo = Modulo.builder()
                .nuModulo(10L)
                .sgModulo("SIAAS")
                .noModulo("Sistema SIAAS")
                .icPerfilEventual(SimNao.S)
                .icCentralizado(SimNao.N)
                .icMultiplosPerfis(SimNao.S)
                .icSigal(SimNao.N)
                .tsCadastro(agora)
                .coMatriculaCadastro("A123456")
                .baseAutenticacao(base)
                .nuProducao(999L)
                .build();

        assertEquals(10L, modulo.getNuModulo());
        assertEquals("SIAAS", modulo.getSgModulo());
        assertEquals("Sistema SIAAS", modulo.getNoModulo());
        assertEquals(SimNao.S, modulo.getIcPerfilEventual());
        assertEquals(SimNao.N, modulo.getIcCentralizado());
        assertEquals(SimNao.S, modulo.getIcMultiplosPerfis());
        assertEquals(SimNao.N, modulo.getIcSigal());
        assertEquals("A123456", modulo.getCoMatriculaCadastro());
        assertEquals(999L, modulo.getNuProducao());
        assertSame(base, modulo.getBaseAutenticacao());
        assertNotNull(modulo.getTsCadastro());
    }
}