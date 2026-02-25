package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity;

import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ModuloEntityTest {

    private static final Long NU_BASE_AUT = 10L;

    private static final Long NU_MODULO_1 = 1L;
    private static final Long NU_MODULO_2 = 2L;
    private static final Long NU_MODULO_3 = 3L;

    private static final String SG_MODULO_1 = "SIAAS";
    private static final String SG_MODULO_2 = "SG";
    private static final String SG_MODULO_3 = "SG3";

    private static final String NO_MODULO_1 = "Modulo";
    private static final String NO_MODULO_2 = "Nome";
    private static final String NO_MODULO_3 = "Nome3";

    private static final String MATRICULA_1 = "1234567";
    private static final String MATRICULA_2 = "7654321";
    private static final String MATRICULA_3 = "1111111";
    private static final String MATRICULA_4 = "0000000";

    private static final String MATRICULA_CADASTRO_2 = "123";
    private static final String MATRICULA_EXCLUSAO = "999";

    private static final Long NU_PRODUCAO_1 = 99L;
    private static final Long NU_PRODUCAO_2 = 2L;

    private static final Long NU_PRODUCAO_ARGS = 1L;

    private static final String BUILDER_CLASS_NAME = "ModuloEntityBuilder";

    @Test
    void deveCobrirConstrutoresGettersSettersBuilder() {
        Date agora = new Date();

        BaseAutenticacaoEntity base = new BaseAutenticacaoEntity();
        base.setNuBaseAutenticacao(NU_BASE_AUT);

        ModuloEntity m1 = new ModuloEntity();
        assertNotNull(m1);

        m1.setNuModulo(NU_MODULO_1);
        m1.setSgModulo(SG_MODULO_1);
        m1.setNoModulo(NO_MODULO_1);
        m1.setIcPerfilEventual(SimNao.S);
        m1.setIcCentralizado(SimNao.N);
        m1.setIcMultiplosPerfis(SimNao.S);
        m1.setIcSigal(SimNao.N);
        m1.setTsCadastro(agora);
        m1.setCoMatriculaCadastro(MATRICULA_1);
        m1.setTsExclusao(null);
        m1.setCoMatriculaExclusao(null);
        m1.setBaseAutenticacao(base);
        m1.setNuProducao(NU_PRODUCAO_1);

        assertEquals(NU_MODULO_1, m1.getNuModulo());
        assertEquals(SG_MODULO_1, m1.getSgModulo());
        assertEquals(NO_MODULO_1, m1.getNoModulo());
        assertEquals(SimNao.S, m1.getIcPerfilEventual());
        assertEquals(SimNao.N, m1.getIcCentralizado());
        assertEquals(SimNao.S, m1.getIcMultiplosPerfis());
        assertEquals(SimNao.N, m1.getIcSigal());
        assertEquals(agora, m1.getTsCadastro());
        assertEquals(MATRICULA_1, m1.getCoMatriculaCadastro());
        assertSame(base, m1.getBaseAutenticacao());
        assertEquals(NU_PRODUCAO_1, m1.getNuProducao());

        ModuloEntity m2 = new ModuloEntity(
                NU_MODULO_2, SG_MODULO_2, NO_MODULO_2,
                SimNao.N, SimNao.S, SimNao.N, SimNao.S,
                agora, MATRICULA_2,
                agora, MATRICULA_3,
                base,
                NU_PRODUCAO_ARGS
        );
        assertEquals(NU_MODULO_2, m2.getNuModulo());

        ModuloEntity m3 = ModuloEntity.builder()
                .nuModulo(NU_MODULO_3)
                .sgModulo(SG_MODULO_3)
                .noModulo(NO_MODULO_3)
                .icPerfilEventual(SimNao.S)
                .icCentralizado(SimNao.S)
                .icMultiplosPerfis(SimNao.S)
                .icSigal(SimNao.S)
                .tsCadastro(agora)
                .coMatriculaCadastro(MATRICULA_4)
                .baseAutenticacao(base)
                .nuProducao(NU_PRODUCAO_2)
                .build();

        assertEquals(NU_MODULO_3, m3.getNuModulo());
        assertEquals(SG_MODULO_3, m3.getSgModulo());
        assertEquals(NO_MODULO_3, m3.getNoModulo());
        assertEquals(SimNao.S, m3.getIcSigal());
    }

    @Test
    void builder_deveCobrirToStringESetterFaltantes_eEntityGettersFaltantes() {
        Date cadastro = new Date();
        Date exclusao = new Date(cadastro.getTime() + 1000);

        BaseAutenticacaoEntity base = new BaseAutenticacaoEntity();
        base.setNuBaseAutenticacao(NU_BASE_AUT);

        ModuloEntity.ModuloEntityBuilder builder = ModuloEntity.builder()
                .nuModulo(NU_MODULO_1)
                .sgModulo(SG_MODULO_1)
                .noModulo(NO_MODULO_1)
                .icPerfilEventual(SimNao.S)
                .icCentralizado(SimNao.N)
                .icMultiplosPerfis(SimNao.S)
                .icSigal(SimNao.N)
                .tsCadastro(cadastro)
                .coMatriculaCadastro(MATRICULA_CADASTRO_2)
                .baseAutenticacao(base)
                .nuProducao(NU_PRODUCAO_1)
                .tsExclusao(exclusao)
                .coMatriculaExclusao(MATRICULA_EXCLUSAO);

        String s = builder.toString();
        assertNotNull(s);
        assertTrue(s.contains(BUILDER_CLASS_NAME));

        ModuloEntity m = builder.build();
        assertNotNull(m);

        assertEquals(exclusao, m.getTsExclusao());
        assertEquals(MATRICULA_EXCLUSAO, m.getCoMatriculaExclusao());

        assertEquals(NU_MODULO_1, m.getNuModulo());
        assertEquals(SG_MODULO_1, m.getSgModulo());
        assertEquals(NO_MODULO_1, m.getNoModulo());
        assertSame(base, m.getBaseAutenticacao());
    }
}