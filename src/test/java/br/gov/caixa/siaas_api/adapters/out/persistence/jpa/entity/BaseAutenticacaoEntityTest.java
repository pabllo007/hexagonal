package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity;

import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseAutenticacaoEntityTest {

    private static final Long NU_BASE_1 = 1L;
    private static final Long NU_BASE_2 = 2L;
    private static final Long NU_BASE_3 = 3L;

    private static final String NO_BASE_1 = "Base";
    private static final String NO_BASE_2 = "Base2";
    private static final String NO_BASE_3 = "Base3";

    private static final String BUILDER_CLASS_NAME = "BaseAutenticacaoEntityBuilder";

    @Test
    void deveCobrirConstrutoresGettersSettersBuilderEConstantes() {
        BaseAutenticacaoEntity e1 = new BaseAutenticacaoEntity();
        assertNotNull(e1);

        e1.setNuBaseAutenticacao(NU_BASE_1);
        e1.setNoBaseAutenticacao(NO_BASE_1);
        e1.setIcRecurso(SimNao.S);
        e1.setIcSinav(SimNao.N);
        e1.setIcAtivo(SimNao.S);

        assertEquals(NU_BASE_1, e1.getNuBaseAutenticacao());
        assertEquals(NO_BASE_1, e1.getNoBaseAutenticacao());
        assertEquals(SimNao.S, e1.getIcRecurso());
        assertEquals(SimNao.N, e1.getIcSinav());
        assertEquals(SimNao.S, e1.getIcAtivo());

        BaseAutenticacaoEntity e2 = new BaseAutenticacaoEntity(
                NU_BASE_2, NO_BASE_2, SimNao.N, SimNao.S, SimNao.N
        );

        assertEquals(NU_BASE_2, e2.getNuBaseAutenticacao());
        assertEquals(NO_BASE_2, e2.getNoBaseAutenticacao());
        assertEquals(SimNao.N, e2.getIcRecurso());
        assertEquals(SimNao.S, e2.getIcSinav());
        assertEquals(SimNao.N, e2.getIcAtivo());

        BaseAutenticacaoEntity e3 = BaseAutenticacaoEntity.builder()
                .nuBaseAutenticacao(NU_BASE_3)
                .noBaseAutenticacao(NO_BASE_3)
                .icRecurso(SimNao.S)
                .icSinav(SimNao.S)
                .icAtivo(SimNao.S)
                .build();

        assertEquals(NU_BASE_3, e3.getNuBaseAutenticacao());
        assertEquals(NO_BASE_3, e3.getNoBaseAutenticacao());
        assertEquals(SimNao.S, e3.getIcRecurso());
        assertEquals(SimNao.S, e3.getIcSinav());
        assertEquals(SimNao.S, e3.getIcAtivo());

        assertNotNull(BaseAutenticacaoEntity.SISGR);
        assertNotNull(BaseAutenticacaoEntity.SINAV);
    }

    @Test
    void builder_toString_deveSerChamavel_eBuildDeveFuncionar() {
        BaseAutenticacaoEntity.BaseAutenticacaoEntityBuilder builder =
                BaseAutenticacaoEntity.builder()
                        .nuBaseAutenticacao(NU_BASE_1)
                        .noBaseAutenticacao(NO_BASE_1)
                        .icRecurso(SimNao.S)
                        .icSinav(SimNao.N)
                        .icAtivo(SimNao.S);

        String s = builder.toString();
        assertNotNull(s);
        assertTrue(s.contains(BUILDER_CLASS_NAME));

        BaseAutenticacaoEntity e = builder.build();
        assertNotNull(e);
        assertEquals(NU_BASE_1, e.getNuBaseAutenticacao());
        assertEquals(NO_BASE_1, e.getNoBaseAutenticacao());
        assertEquals(SimNao.S, e.getIcRecurso());
        assertEquals(SimNao.N, e.getIcSinav());
        assertEquals(SimNao.S, e.getIcAtivo());

        String entityString = e.toString();
        assertNotNull(entityString);
        assertFalse(entityString.isBlank());
    }
}