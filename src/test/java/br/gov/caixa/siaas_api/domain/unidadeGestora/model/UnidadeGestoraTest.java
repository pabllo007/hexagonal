package br.gov.caixa.siaas_api.domain.unidadeGestora.model;

import br.gov.caixa.siaas_api.domain.grupotrabalho.model.GrupoTrabalho;
import br.gov.caixa.siaas_api.domain.tipounidadegestora.model.TipoUnidadeGestora;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnidadeGestoraTest {

    private static final Long NU_UNIDADE_GESTORA = 10L;
    private static final String NO_UNIDADE_GESTORA = "UG TESTE";
    private static final Integer NU_UNIDADE = 123;
    private static final Integer NU_NATURAL = 456;
    private static final String NO_CAIXA_POSTAL = "CP-001";

    @Test
    void construtorDefault_deveInicializarSemNuloObrigatorio_eAtivoFalse() {
        UnidadeGestora ug = new UnidadeGestora();

        // getters de campos objeto devem começar nulos (default do Java)
        assertNull(ug.getNuUnidadeGestora());
        assertNull(ug.getNoUnidadeGestora());
        assertNull(ug.getNuUnidade());
        assertNull(ug.getNuNatural());
        assertNull(ug.getNoCaixaPostal());
        assertNull(ug.getTipo());
        assertNull(ug.getGrupoTrabalho());
        assertFalse(ug.isAtivo());
    }

    @Test
    void gettersSetters_devemPersistirValoresCorretamente() {
        UnidadeGestora ug = new UnidadeGestora();

        TipoUnidadeGestora tipo = new TipoUnidadeGestora();
        GrupoTrabalho grupo = new GrupoTrabalho();

        ug.setNuUnidadeGestora(NU_UNIDADE_GESTORA);
        ug.setNoUnidadeGestora(NO_UNIDADE_GESTORA);
        ug.setNuUnidade(NU_UNIDADE);
        ug.setNuNatural(NU_NATURAL);
        ug.setNoCaixaPostal(NO_CAIXA_POSTAL);
        ug.setTipo(tipo);
        ug.setGrupoTrabalho(grupo);
        ug.setAtivo(true);

        assertEquals(NU_UNIDADE_GESTORA, ug.getNuUnidadeGestora());
        assertEquals(NO_UNIDADE_GESTORA, ug.getNoUnidadeGestora());
        assertEquals(NU_UNIDADE, ug.getNuUnidade());
        assertEquals(NU_NATURAL, ug.getNuNatural());
        assertEquals(NO_CAIXA_POSTAL, ug.getNoCaixaPostal());
        assertSame(tipo, ug.getTipo());
        assertSame(grupo, ug.getGrupoTrabalho());
        assertTrue(ug.isAtivo());
    }

    @Test
    void setters_devemPermitirSobrescreverValores() {
        UnidadeGestora ug = new UnidadeGestora();

        TipoUnidadeGestora tipo1 = new TipoUnidadeGestora();
        TipoUnidadeGestora tipo2 = new TipoUnidadeGestora();
        GrupoTrabalho grupo1 = new GrupoTrabalho();
        GrupoTrabalho grupo2 = new GrupoTrabalho();

        ug.setNuUnidadeGestora(NU_UNIDADE_GESTORA);
        ug.setNoUnidadeGestora(NO_UNIDADE_GESTORA);
        ug.setNuUnidade(NU_UNIDADE);
        ug.setNuNatural(NU_NATURAL);
        ug.setNoCaixaPostal(NO_CAIXA_POSTAL);
        ug.setTipo(tipo1);
        ug.setGrupoTrabalho(grupo1);
        ug.setAtivo(true);

        ug.setNuUnidadeGestora(null);
        ug.setNoUnidadeGestora(null);
        ug.setNuUnidade(null);
        ug.setNuNatural(null);
        ug.setNoCaixaPostal(null);
        ug.setTipo(tipo2);
        ug.setGrupoTrabalho(grupo2);
        ug.setAtivo(false);

        assertNull(ug.getNuUnidadeGestora());
        assertNull(ug.getNoUnidadeGestora());
        assertNull(ug.getNuUnidade());
        assertNull(ug.getNuNatural());
        assertNull(ug.getNoCaixaPostal());
        assertSame(tipo2, ug.getTipo());
        assertSame(grupo2, ug.getGrupoTrabalho());
        assertFalse(ug.isAtivo());
    }
}