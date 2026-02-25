package br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.mapper;

import br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto.BaseAutenticacaoRequest;
import br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto.BaseAutenticacaoResponse;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseAutenticacaoWebMapperTest {

    private final BaseAutenticacaoWebMapper mapper = new BaseAutenticacaoWebMapper();

    @Test
    void toDomain_deveRetornarNulo_quandoRequestNulo() {
        assertNull(mapper.toDomain(null));
    }

    @Test
    void toDomain_deveMapearCampos_eTratarNome() {
        BaseAutenticacaoRequest req =
                new BaseAutenticacaoRequest("  Base   ", "S", "N");

        BaseAutenticacao domain = mapper.toDomain(req);

        assertNotNull(domain);
        assertEquals("Base", domain.getNoBaseAutenticacao());
        assertEquals(SimNao.S, domain.getIcRecurso());
        assertEquals(SimNao.N, domain.getIcSinav());
    }

    @Test
    void toResponse_deveRetornarNulo_quandoDomainNulo() {
        assertNull(mapper.toResponse(null));
    }

    @Test
    void toResponse_deveMapearTodosCampos() {
        BaseAutenticacao domain = new BaseAutenticacao();
        domain.setNuBaseAutenticacao(1L);
        domain.setNoBaseAutenticacao("Base");
        domain.setIcRecurso(SimNao.S);
        domain.setIcSinav(SimNao.N);
        domain.setIcAtivo(SimNao.S);

        BaseAutenticacaoResponse resp = mapper.toResponse(domain);

        assertNotNull(resp);
        assertEquals(1L, resp.nuBaseAutenticacao());
        assertEquals("Base", resp.noBaseAutenticacao());
        assertEquals("S", resp.icRecurso());
        assertEquals("N", resp.icSinav());
        assertEquals("S", resp.icAtivo());
    }

    @Test
    void tratarNome_deveRetornarNull_quandoNomeNull() {
        BaseAutenticacaoRequest req = new BaseAutenticacaoRequest(null, "S", "N");

        BaseAutenticacao d = mapper.toDomain(req);

        assertNotNull(d);
        assertNull(d.getNoBaseAutenticacao());
    }

    @Test
    void tratarNome_deveNormalizarEspacosInternos() {
        BaseAutenticacaoRequest req = new BaseAutenticacaoRequest("  Base   Aut  ", "S", "N");

        BaseAutenticacao d = mapper.toDomain(req);

        assertEquals("Base Aut", d.getNoBaseAutenticacao());
    }

    @Test
    void toDomain_deveMapearNullNosEnums_quandoVierNull() {
        BaseAutenticacaoRequest req = new BaseAutenticacaoRequest("Base", null, null);

        BaseAutenticacao d = mapper.toDomain(req);

        assertNull(d.getIcRecurso());
        assertNull(d.getIcSinav());
    }

    @Test
    void toResponse_deveManterNull_quandoCampoNullNoDomain() {
        BaseAutenticacao domain = new BaseAutenticacao();
        domain.setNuBaseAutenticacao(1L);
        domain.setNoBaseAutenticacao("Base");
        domain.setIcRecurso(null);
        domain.setIcSinav(SimNao.S);
        domain.setIcAtivo(null);

        BaseAutenticacaoResponse resp = mapper.toResponse(domain);

        assertNull(resp.icRecurso());
        assertEquals("S", resp.icSinav());
        assertNull(resp.icAtivo());
    }
}