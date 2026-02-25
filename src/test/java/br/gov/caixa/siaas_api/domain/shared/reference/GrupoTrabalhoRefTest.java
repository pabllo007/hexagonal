package br.gov.caixa.siaas_api.domain.shared.reference;

import br.gov.caixa.siaas_api.domain.grupotrabalho.model.GrupoTrabalho;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrupoTrabalhoRefTest {

    private static final Integer ID_1 = 1;
    private static final Integer ID_2 = 2;
    private static final String NOME_1 = "CEPTI SEGURANCA BSB";
    private static final String NOME_2 = "CEPTI SEGURANCA RJ";

    @Test
    void constantesDevemTerValoresEsperados() {
        assertEquals(1, GrupoTrabalho.CEPTI_SEGURANCA_BSB);
        assertEquals(2, GrupoTrabalho.CEPTI_SEGURANCA_RJ);
        assertEquals(3, GrupoTrabalho.CEPTI_SEGURANCA_SP);
        assertEquals(4, GrupoTrabalho.CETEC_PEDES);
    }

    @Test
    void construtorDefault_deveIniciarNulo() {
        GrupoTrabalho ref = new GrupoTrabalho();

        assertNull(ref.getNuGrupoTrabalho());
        assertNull(ref.getNoGrupoTrabalho());
    }

    @Test
    void construtorComArgs_devePopularCampos() {
        GrupoTrabalho ref = new GrupoTrabalho(ID_1, NOME_1);

        assertEquals(ID_1, ref.getNuGrupoTrabalho());
        assertEquals(NOME_1, ref.getNoGrupoTrabalho());
    }

    @Test
    void gettersSetters_devemPersistirValores() {
        GrupoTrabalho ref = new GrupoTrabalho();

        ref.setNuGrupoTrabalho(ID_1);
        ref.setNoGrupoTrabalho(NOME_1);

        assertEquals(ID_1, ref.getNuGrupoTrabalho());
        assertEquals(NOME_1, ref.getNoGrupoTrabalho());
    }

    @Test
    void equals_deveSerTrue_quandoMesmaInstancia() {
        GrupoTrabalho ref = new GrupoTrabalho(ID_1, NOME_1);

        assertEquals(ref, ref);
    }

    @Test
    void equals_deveSerFalse_quandoNulo() {
        GrupoTrabalho ref = new GrupoTrabalho(ID_1, NOME_1);

        assertNotEquals(ref, null);
    }

    @Test
    void equals_deveSerFalse_quandoTipoDiferente() {
        GrupoTrabalho ref = new GrupoTrabalho(ID_1, NOME_1);

        assertNotEquals(ref, new Object());
    }

    @Test
    void equals_deveCompararApenasId() {
        GrupoTrabalho a = new GrupoTrabalho(ID_1, NOME_1);
        GrupoTrabalho b = new GrupoTrabalho(ID_1, NOME_2);

        assertEquals(a, b);
    }

    @Test
    void equals_deveSerFalse_quandoIdDiferente() {
        GrupoTrabalho a = new GrupoTrabalho(ID_1, NOME_1);
        GrupoTrabalho b = new GrupoTrabalho(ID_2, NOME_1);

        assertNotEquals(a, b);
    }

    @Test
    void equals_deveSerTrue_quandoAmbosIdsNulos() {
        GrupoTrabalho a = new GrupoTrabalho(null, NOME_1);
        GrupoTrabalho b = new GrupoTrabalho(null, NOME_2);

        assertEquals(a, b);
    }

    @Test
    void hashCode_deveSerIgual_quandoIdsIguais() {
        GrupoTrabalho a = new GrupoTrabalho(ID_1, NOME_1);
        GrupoTrabalho b = new GrupoTrabalho(ID_1, NOME_2);

        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void hashCode_deveSerIgual_quandoIdsNulos() {
        GrupoTrabalho a = new GrupoTrabalho(null, NOME_1);
        GrupoTrabalho b = new GrupoTrabalho(null, NOME_2);

        assertEquals(a.hashCode(), b.hashCode());
    }
}