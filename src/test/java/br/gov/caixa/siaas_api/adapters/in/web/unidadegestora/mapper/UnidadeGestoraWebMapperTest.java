package br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.mapper;

import br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.dto.UnidadeGestoraResponse;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import br.gov.caixa.siaas_api.domain.tipounidadegestora.model.TipoUnidadeGestora;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class UnidadeGestoraWebMapperTest {

    private static final String NOME_COM_ESPACOS = "  Base   Autenticacao   ";
    private static final String NOME_NORMALIZADO = "Base Autenticacao";

    private static final Long NU_UNIDADE_GESTORA = 10L;
    private static final String NO_UNIDADE_GESTORA = "Unidade 001";
    private static final String TIPO_UNIDADE_GESTORA = "Desenvolvimento";

    private static final String FLAG_S = "S";
    private static final String FLAG_N = "N";

//    @Test
//    void toDomain_deveRetornarNull_quandoRequestNull() {
//        UnidadeGestoraWebMapper mapper = new UnidadeGestoraWebMapper();
//
//        BaseAutenticacao result = mapper.toDomain(null);
//
//        assertNull(result);
//    }

//    @Test
//    void toDomain_deveMapearCampos_tratandoNome_eConvertendoFlags() {
//        UnidadeGestoraWebMapper mapper = new UnidadeGestoraWebMapper();
//
//        BaseAutenticacaoResponse req = new BaseAutenticacaoResponse(
//                null,
//                NOME_COM_ESPACOS,
//                FLAG_S,
//                FLAG_N,
//                null
//        );
//
//        BaseAutenticacao d = mapper.toDomain(req);
//
//        assertNotNull(d);
//        assertEquals(NOME_NORMALIZADO, d.getNoBaseAutenticacao());
//        assertEquals(SimNao.S, d.getIcRecurso());
//        assertEquals(SimNao.N, d.getIcSinav());
//    }

    @Test
    void toResponse_deveRetornarNull_quandoDomainNull() {
        UnidadeGestoraWebMapper mapper = new UnidadeGestoraWebMapper();

        UnidadeGestoraResponse result = mapper.toResponse(null);

        assertNull(result);
    }

    @Test
    void toResponse_deveMapear_quandoTipoExistir() {
        UnidadeGestoraWebMapper mapper = new UnidadeGestoraWebMapper();

        TipoUnidadeGestora tipo = new TipoUnidadeGestora();
        tipo.setNoTipoUnidadeGestora(TIPO_UNIDADE_GESTORA);

        UnidadeGestora ug = new UnidadeGestora();
        ug.setNuUnidadeGestora(NU_UNIDADE_GESTORA);
        ug.setNoUnidadeGestora(NO_UNIDADE_GESTORA);
        ug.setTipo(tipo);
        ug.setAtivo(true);

        UnidadeGestoraResponse r = mapper.toResponse(ug);

        assertNotNull(r);
        assertEquals(NU_UNIDADE_GESTORA, r.nuUnidadeGestora());
        assertEquals(NO_UNIDADE_GESTORA, r.noUnidadeGestora());
        assertEquals(TIPO_UNIDADE_GESTORA, r.tipoUnidadeGestora());
        assertTrue(r.icAtivo());
    }

    @Test
    void toResponse_deveMapear_quandoTipoForNull() {
        UnidadeGestoraWebMapper mapper = new UnidadeGestoraWebMapper();

        UnidadeGestora ug = new UnidadeGestora();
        ug.setNuUnidadeGestora(NU_UNIDADE_GESTORA);
        ug.setNoUnidadeGestora(NO_UNIDADE_GESTORA);
        ug.setTipo(null);
        ug.setAtivo(false);

        UnidadeGestoraResponse r = mapper.toResponse(ug);

        assertNotNull(r);
        assertEquals(NU_UNIDADE_GESTORA, r.nuUnidadeGestora());
        assertEquals(NO_UNIDADE_GESTORA, r.noUnidadeGestora());
        assertNull(r.tipoUnidadeGestora());
        assertFalse(r.icAtivo());
    }

    @Test
    void tratarNome_deveRetornarNull_quandoEntradaNull() throws Exception {
        UnidadeGestoraWebMapper mapper = new UnidadeGestoraWebMapper();

        Method m = UnidadeGestoraWebMapper.class.getDeclaredMethod("tratarNome", String.class);
        m.setAccessible(true);

        Object out = m.invoke(mapper, new Object[]{null});

        assertNull(out);
    }

    @Test
    void tratarNome_deveNormalizarEspacos() throws Exception {
        UnidadeGestoraWebMapper mapper = new UnidadeGestoraWebMapper();

        Method m = UnidadeGestoraWebMapper.class.getDeclaredMethod("tratarNome", String.class);
        m.setAccessible(true);

        Object out = m.invoke(mapper, NOME_COM_ESPACOS);

        assertEquals(NOME_NORMALIZADO, out);
    }

    @Test
    void toFlag_deveRetornarNull_quandoValorNull() throws Exception {
        UnidadeGestoraWebMapper mapper = new UnidadeGestoraWebMapper();

        Method m = UnidadeGestoraWebMapper.class.getDeclaredMethod("toFlag", SimNao.class);
        m.setAccessible(true);

        Object out = m.invoke(mapper, new Object[]{null});

        assertNull(out);
    }

    @Test
    void toFlag_deveRetornarS_ouN_quandoValorNaoForNull() throws Exception {
        UnidadeGestoraWebMapper mapper = new UnidadeGestoraWebMapper();

        Method m = UnidadeGestoraWebMapper.class.getDeclaredMethod("toFlag", SimNao.class);
        m.setAccessible(true);

        Object outS = m.invoke(mapper, SimNao.S);
        Object outN = m.invoke(mapper, SimNao.N);

        assertEquals(FLAG_S, outS);
        assertEquals(FLAG_N, outN);
    }
}