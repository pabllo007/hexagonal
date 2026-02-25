package br.gov.caixa.siaas_api.domain.shared.reference;

import br.gov.caixa.siaas_api.domain.tipounidadegestora.model.TipoUnidadeGestora;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoUnidadeGestoraRefTest {

    private static final Integer ID_1 = 1;
    private static final Integer ID_2 = 2;
    private static final Integer ID_PRODUCAO = TipoUnidadeGestora.PRODUCAO;

    private static final String NOME_1 = "DESENVOLVIMENTO";
    private static final String NOME_2 = "NEGOCIO";

    @Test
    void constantesDevemTerValoresEsperados() {
        assertEquals(1, TipoUnidadeGestora.DESENVOLVIMENTO);
        assertEquals(2, TipoUnidadeGestora.NEGOCIO);
        assertEquals(3, TipoUnidadeGestora.TI);
        assertEquals(4, TipoUnidadeGestora.SEGURANCA);
        assertEquals(5, TipoUnidadeGestora.PRODUCAO);
    }

    @Test
    void construtorDefault_deveIniciarNulo_ePermiteGrupoTrabalhoFalse() {
        TipoUnidadeGestora ref = new TipoUnidadeGestora();

        assertNull(ref.getNuTipoUnidadeGestora());
        assertNull(ref.getNoTipoUnidadeGestora());
        assertFalse(ref.isPermiteGrupoTrabalho());
    }

    @Test
    void construtorComArgs_devePopularCampos() {
        TipoUnidadeGestora ref = new TipoUnidadeGestora(ID_1, NOME_1, true);

        assertEquals(ID_1, ref.getNuTipoUnidadeGestora());
        assertEquals(NOME_1, ref.getNoTipoUnidadeGestora());
        assertTrue(ref.isPermiteGrupoTrabalho());
    }

    @Test
    void gettersSetters_devemPersistirValores() {
        TipoUnidadeGestora ref = new TipoUnidadeGestora();

        ref.setNuTipoUnidadeGestora(ID_2);
        ref.setNoTipoUnidadeGestora(NOME_2);
        ref.setPermiteGrupoTrabalho(true);

        assertEquals(ID_2, ref.getNuTipoUnidadeGestora());
        assertEquals(NOME_2, ref.getNoTipoUnidadeGestora());
        assertTrue(ref.isPermiteGrupoTrabalho());
    }

    @Test
    void isProducao_deveRetornarFalse_quandoIdNulo() {
        TipoUnidadeGestora ref = new TipoUnidadeGestora(null, NOME_1, false);

        assertFalse(ref.isProducao());
    }

    @Test
    void isProducao_deveRetornarFalse_quandoIdDiferenteDeProducao() {
        TipoUnidadeGestora ref = new TipoUnidadeGestora(ID_1, NOME_1, false);

        assertFalse(ref.isProducao());
    }

    @Test
    void isProducao_deveRetornarTrue_quandoIdForProducao() {
        TipoUnidadeGestora ref = new TipoUnidadeGestora(ID_PRODUCAO, "PRODUCAO", false);

        assertTrue(ref.isProducao());
    }

    @Test
    void equals_deveSerTrue_quandoMesmaInstancia() {
        TipoUnidadeGestora ref = new TipoUnidadeGestora(ID_1, NOME_1, false);

        assertEquals(ref, ref);
    }

    @Test
    void equals_deveSerFalse_quandoNulo() {
        TipoUnidadeGestora ref = new TipoUnidadeGestora(ID_1, NOME_1, false);

        assertNotEquals(ref, null);
    }

    @Test
    void equals_deveSerFalse_quandoTipoDiferente() {
        TipoUnidadeGestora ref = new TipoUnidadeGestora(ID_1, NOME_1, false);

        assertNotEquals(ref, new Object());
    }

    @Test
    void equals_deveCompararApenasId() {
        TipoUnidadeGestora a = new TipoUnidadeGestora(ID_1, NOME_1, false);
        TipoUnidadeGestora b = new TipoUnidadeGestora(ID_1, NOME_2, true);

        assertEquals(a, b);
    }

    @Test
    void equals_deveSerFalse_quandoIdDiferente() {
        TipoUnidadeGestora a = new TipoUnidadeGestora(ID_1, NOME_1, false);
        TipoUnidadeGestora b = new TipoUnidadeGestora(ID_2, NOME_1, false);

        assertNotEquals(a, b);
    }

    @Test
    void equals_deveSerTrue_quandoAmbosIdsNulos() {
        TipoUnidadeGestora a = new TipoUnidadeGestora(null, NOME_1, false);
        TipoUnidadeGestora b = new TipoUnidadeGestora(null, NOME_2, true);

        assertEquals(a, b);
    }

    @Test
    void hashCode_deveSerIgual_quandoIdsIguais() {
        TipoUnidadeGestora a = new TipoUnidadeGestora(ID_1, NOME_1, false);
        TipoUnidadeGestora b = new TipoUnidadeGestora(ID_1, NOME_2, true);

        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void hashCode_deveSerIgual_quandoIdsNulos() {
        TipoUnidadeGestora a = new TipoUnidadeGestora(null, NOME_1, false);
        TipoUnidadeGestora b = new TipoUnidadeGestora(null, NOME_2, true);

        assertEquals(a.hashCode(), b.hashCode());
    }
}