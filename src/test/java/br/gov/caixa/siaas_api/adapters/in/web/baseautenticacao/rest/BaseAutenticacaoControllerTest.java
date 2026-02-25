package br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.rest;

import br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto.BaseAutenticacaoFilter;
import br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto.BaseAutenticacaoRequest;
import br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto.BaseAutenticacaoResponse;
import br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.mapper.BaseAutenticacaoWebMapper;
import br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.dto.PaginacaoRequestDTO;
import br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.dto.PaginacaoResponseDTO;
import br.gov.caixa.siaas_api.application.baseautenticacao.port.in.*;
import br.gov.caixa.siaas_api.application.baseautenticacao.query.BaseAutenticacaoSearchQuery;
import br.gov.caixa.siaas_api.application.comum.paginacao.PageResult;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import br.gov.caixa.siaas_api.exceptions.NaoEncontradoException;
import br.gov.caixa.siaas_api.exceptions.NegocioException;
import br.gov.caixa.siaas_api.mensagens.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@WebMvcTest(BaseAutenticacaoController.class)
class BaseAutenticacaoControllerTest {

    private static final String BASE = "Base";
    private static final String URL_API_BASE_AUTENTICACAO = "/api/v1/base-autenticacoes";

    private static final String TYPE_VALIDATION_ERROR = "urn:simpa:validation:error";
    private static final String TYPE_JSON_INVALID = "urn:simpa:json:invalid";
    private static final String TYPE_NEGOCIO_ERROR = "urn:simpa:negocio:erro";
    private static final String TYPE_NOT_FOUND = "urn:simpa:base-autenticacao:not-found";
    private static final String TYPE_INTERNAL_ERROR = "urn:simpa:erro:interno";

    private static final String TITLE_DADOS_INVALIDOS = "Dados inválidos";
    private static final String TITLE_ERRO_NEGOCIO = "Erro de negócio";
    private static final String TITLE_RECURSO_NAO_ENCONTRADO = "Recurso não encontrado";
    private static final String TITLE_ERRO_INTERNO = "Erro interno";

    private static final String CODE_NOT_FOUND = "error.baseautenticacao.not-found";
    private static final String CODE_NEGOCIO = "error.negocio";
    private static final String CODE_JSON_INVALID = "error.json.invalid";
    private static final String CODE_GENERIC_INTERNAL = "error.generic.internal";

    private static final String MSG_BASE_NAO_ENCONTRADA = "Base de autenticação não encontrada";
    private static final String MSG_NOME_OBRIGATORIO =
            "O nome da base de autenticação é obrigatório.";
    private static final String MSG_ENUM_RECURSO_OBRIGATORIO =
            "O indicador de recurso é obrigatório e deve ser 'S' ou 'N'.";
    private static final String MSG_ERRO_NEGOCIO = "Erro de negócio";
    private static final String MSG_ERRO_INTERNO_PADRAO =
            "Ocorreu um erro interno inesperado. Tente novamente mais tarde.";

    private static final String JP_NU_BASE = "$.nuBaseAutenticacao";
    private static final String JP_NO_BASE = "$.noBaseAutenticacao";
    private static final String JP_IC_ATIVO = "$.icAtivo";
    private static final String JP_TYPE = "$.type";
    private static final String JP_TITLE = "$.title";
    private static final String JP_DETAIL = "$.detail";
    private static final String JP_CODE = "$.code";
    private static final String PARAM_ID = "/{id}";

    @MockBean
    private InativarBaseAutenticacaoUseCase inativarBaseAutenticacaoUseCase;

    @MockBean
    private AtivarBaseAutenticacaoUseCase ativarBaseAutenticacaoUseCase;

    @MockBean
    BuscarBaseAutenticacaoPorIdUseCase buscarPorIdUseCase;

    @MockBean
    private PesquisarBaseAutenticacaoUseCase pesquisarUseCase;

    @MockBean
    private CriarBaseAutenticacaoUseCase criarBaseAutenticacaoUseCase;

    @MockBean
    private AtualizarBaseAutenticacaoUseCase atualizarBaseAutenticacaoUseCase;

    @MockBean
    private ExcluirBaseAutenticacaoUseCase excluirBaseAutenticacaoUseCase;

    @MockBean
    private BaseAutenticacaoWebMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MessageService messageService;

    @Test
    void postCriarDeveRetornar201ComBodyELocation() throws Exception {
        var req = new BaseAutenticacaoRequest(
                BASE,
                "S", "N"
        );

        var domainIn = new BaseAutenticacao();
        domainIn.setNoBaseAutenticacao(BASE);
        domainIn.setIcRecurso(SimNao.S);
        domainIn.setIcSinav(SimNao.N);

        var domainOut = new BaseAutenticacao();
        domainOut.setNuBaseAutenticacao(1L);
        domainOut.setNoBaseAutenticacao(BASE);
        domainOut.setIcRecurso(SimNao.S);
        domainOut.setIcSinav(SimNao.N);
        domainOut.setIcAtivo(SimNao.S);

        var resp = new BaseAutenticacaoResponse(
                1L,
                BASE,
                "S", "N", "S"
        );

        when(mapper.toDomain(any(BaseAutenticacaoRequest.class))).thenReturn(domainIn);
        when(criarBaseAutenticacaoUseCase.criar(any())).thenReturn(domainOut);
        when(mapper.toResponse(domainOut)).thenReturn(resp);

        String json = objectMapper.writeValueAsString(req);

        mockMvc.perform(post(URL_API_BASE_AUTENTICACAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",
                        containsString(URL_API_BASE_AUTENTICACAO + "/1")))
                .andExpect(jsonPath(JP_NU_BASE).value(1L))
                .andExpect(jsonPath(JP_NO_BASE).value(BASE))
                .andExpect(jsonPath(JP_IC_ATIVO).value("S"));

        verify(mapper).toDomain(any(BaseAutenticacaoRequest.class));
        verify(criarBaseAutenticacaoUseCase).criar(any());
        verify(mapper).toResponse(domainOut);
    }
    @Test
    void postCriarDeveRetornar400QuandoNomeDuplicado() throws Exception {
        BaseAutenticacaoRequest req = new BaseAutenticacaoRequest(
                BASE,
                "S", "N"
        );

        var domainIn = new BaseAutenticacao();
        domainIn.setNoBaseAutenticacao(BASE);
        domainIn.setIcRecurso(SimNao.S);
        domainIn.setIcSinav(SimNao.N);

        String CODE_DUPLICATE = "error.baseautenticacao.duplicate-name";
        String MSG_DUPLICATE = "Já existe uma Base de Autenticação com o nome '" + BASE + "'.";

        when(mapper.toDomain(any(BaseAutenticacaoRequest.class))).thenReturn(domainIn);
        when(criarBaseAutenticacaoUseCase.criar(any()))
                .thenThrow(new NegocioException(CODE_DUPLICATE, BASE));

        when(messageService.obterMensagem(CODE_DUPLICATE, BASE)).thenReturn(MSG_DUPLICATE);

        String json = objectMapper.writeValueAsString(req);

        mockMvc.perform(post(URL_API_BASE_AUTENTICACAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(JP_TYPE).value(TYPE_NEGOCIO_ERROR))
                .andExpect(jsonPath(JP_TITLE).value(TITLE_ERRO_NEGOCIO))
                .andExpect(jsonPath(JP_DETAIL).value(MSG_DUPLICATE))
                .andExpect(jsonPath(JP_CODE).value(CODE_DUPLICATE));

        verify(mapper).toDomain(any(BaseAutenticacaoRequest.class));
        verify(criarBaseAutenticacaoUseCase).criar(any());
    }

    @Test
    void putAtualizarDeveRetornar200ComBody() throws Exception {
        BaseAutenticacaoRequest req = new BaseAutenticacaoRequest(
                BASE,
                "S", "N"
        );

        var domainIn = new BaseAutenticacao();
        domainIn.setNoBaseAutenticacao(BASE);
        domainIn.setIcRecurso(SimNao.S);
        domainIn.setIcSinav(SimNao.N);

        var domainOut = new BaseAutenticacao();
        domainOut.setNuBaseAutenticacao(1L);
        domainOut.setNoBaseAutenticacao(BASE);
        domainOut.setIcRecurso(SimNao.S);
        domainOut.setIcSinav(SimNao.N);
        domainOut.setIcAtivo(SimNao.S);

        BaseAutenticacaoResponse resp = new BaseAutenticacaoResponse(
                1L,
                BASE,
                "S", "N", "S"
        );

        when(mapper.toDomain(any(BaseAutenticacaoRequest.class))).thenReturn(domainIn);
        when(atualizarBaseAutenticacaoUseCase.atualizar(eq(1L), any())).thenReturn(domainOut);
        when(mapper.toResponse(domainOut)).thenReturn(resp);

        String json = objectMapper.writeValueAsString(req);

        mockMvc.perform(put(URL_API_BASE_AUTENTICACAO + PARAM_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JP_NU_BASE).value(1L))
                .andExpect(jsonPath(JP_NO_BASE).value(BASE));

        verify(mapper).toDomain(any(BaseAutenticacaoRequest.class));
        verify(atualizarBaseAutenticacaoUseCase).atualizar(eq(1L), any());
        verify(mapper ).toResponse(domainOut);
    }

    @Test
    void putAtualizarDeveRetornar404QuandoNaoEncontrado() throws Exception {
        BaseAutenticacaoRequest req = new BaseAutenticacaoRequest(
                BASE,
                "S", "N"
        );

        var domainIn = new BaseAutenticacao();
        domainIn.setNoBaseAutenticacao(BASE);
        domainIn.setIcRecurso(SimNao.S);
        domainIn.setIcSinav(SimNao.N);

        long idInexistente = 99L;

        when(mapper.toDomain(any(BaseAutenticacaoRequest.class))).thenReturn(domainIn);

        when(atualizarBaseAutenticacaoUseCase.atualizar(eq(idInexistente), any()))
                .thenThrow(new br.gov.caixa.siaas_api.exceptions.NaoEncontradoException(CODE_NOT_FOUND, idInexistente));

        when(messageService.obterMensagem(CODE_NOT_FOUND, idInexistente))
                .thenReturn(MSG_BASE_NAO_ENCONTRADA);

        String json = objectMapper.writeValueAsString(req);

        mockMvc.perform(put(URL_API_BASE_AUTENTICACAO + PARAM_ID, idInexistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(JP_TYPE).value(TYPE_NOT_FOUND))
                .andExpect(jsonPath(JP_TITLE).value(TITLE_RECURSO_NAO_ENCONTRADO))
                .andExpect(jsonPath(JP_DETAIL).value(MSG_BASE_NAO_ENCONTRADA))
                .andExpect(jsonPath(JP_CODE).value(CODE_NOT_FOUND));

        verify(atualizarBaseAutenticacaoUseCase).atualizar(eq(idInexistente), any());
    }

    @Test
    void putAtualizarDeveRetornar400QuandoNomeDuplicado() throws Exception {
        BaseAutenticacaoRequest req = new BaseAutenticacaoRequest(
                BASE,
                "S", "N"
        );

        var domainIn = new BaseAutenticacao();
        domainIn.setNoBaseAutenticacao(BASE);
        domainIn.setIcRecurso(SimNao.S);
        domainIn.setIcSinav(SimNao.N);

        String CODE_DUPLICATE = "error.baseautenticacao.duplicate-name";
        String MSG_DUPLICATE = "Já existe uma Base de Autenticação com o nome '" + BASE + "'.";

        when(mapper.toDomain(any(BaseAutenticacaoRequest.class))).thenReturn(domainIn);

        when(atualizarBaseAutenticacaoUseCase.atualizar(eq(1L), any()))
                .thenThrow(new br.gov.caixa.siaas_api.exceptions.NegocioException(CODE_DUPLICATE, BASE));

        when(messageService.obterMensagem(CODE_DUPLICATE, BASE))
                .thenReturn(MSG_DUPLICATE);

        String json = objectMapper.writeValueAsString(req);

        mockMvc.perform(put(URL_API_BASE_AUTENTICACAO + PARAM_ID, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(JP_TYPE).value(TYPE_NEGOCIO_ERROR))
                .andExpect(jsonPath(JP_TITLE).value(TITLE_ERRO_NEGOCIO))
                .andExpect(jsonPath(JP_DETAIL).value(MSG_DUPLICATE))
                .andExpect(jsonPath(JP_CODE).value(CODE_DUPLICATE));

        verify(atualizarBaseAutenticacaoUseCase).atualizar(eq(1L), any());
    }

    @Test
    void getBuscarPorIdDeveRetornar200ComBody() throws Exception {
        var entidade = new BaseAutenticacao();
        var resp = new BaseAutenticacaoResponse(
                1L,
                BASE,
                "S", "N","S"
        );

        when(buscarPorIdUseCase.buscar(1L)).thenReturn(entidade);
        when(mapper.toResponse(entidade)).thenReturn(resp);

        mockMvc.perform(get(URL_API_BASE_AUTENTICACAO + PARAM_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JP_NU_BASE).value(1L))
                .andExpect(jsonPath(JP_NO_BASE).value(BASE));

        verify(buscarPorIdUseCase).buscar(1L);
        verify(mapper).toResponse(entidade);
    }

    @Test
    void getPesquisarDeveRetornar200ComListaPaginada() throws Exception {
        var domain = new BaseAutenticacao();
        domain.setNuBaseAutenticacao(1L);
        domain.setNoBaseAutenticacao(BASE);
        domain.setIcRecurso(SimNao.S);
        domain.setIcSinav(SimNao.N);
        domain.setIcAtivo(SimNao.S);

        BaseAutenticacaoResponse resp = new BaseAutenticacaoResponse(
                1L,
                BASE,
                "S", "N", "S"
        );

        var pageResult = new PageResult<>(
                java.util.List.of(domain),
                1L,
                1,
                0,
                10,
                true,
                true
        );

        when(pesquisarUseCase.pesquisar(any())).thenReturn(pageResult);
        when(mapper.toResponse(domain)).thenReturn(resp);

        mockMvc.perform(get(URL_API_BASE_AUTENTICACAO)
                        .param("noBaseAutenticacao", BASE)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nuBaseAutenticacao").value(1L))
                .andExpect(jsonPath("$.content[0].noBaseAutenticacao").value(BASE));

        verify(pesquisarUseCase).pesquisar(any());
        verify(mapper).toResponse(domain);
    }

    @Test
    void patchAtivarDeveRetornar200ComBodyAtivo() throws Exception {
        var domain = new BaseAutenticacao();
        domain.setNuBaseAutenticacao(1L);
        domain.setNoBaseAutenticacao(BASE);
        domain.setIcRecurso(SimNao.S);
        domain.setIcSinav(SimNao.N);
        domain.setIcAtivo(SimNao.S);

        var resp = new BaseAutenticacaoResponse(
                1L,
                BASE,
                "S", "N", "S"
        );

        when(ativarBaseAutenticacaoUseCase.ativar(1L)).thenReturn(domain);
        when(mapper.toResponse(domain)).thenReturn(resp);

        mockMvc.perform(patch(URL_API_BASE_AUTENTICACAO + "/{id}/ativar", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JP_NU_BASE).value(1L))
                .andExpect(jsonPath(JP_NO_BASE).value(BASE))
                .andExpect(jsonPath(JP_IC_ATIVO).value("S"));

        verify(ativarBaseAutenticacaoUseCase).ativar(1L);
        verify(mapper).toResponse(domain);
    }

    @Test
    void getBuscarPorIdDeveRetornar404QuandoNaoEncontrado() throws Exception {
        long idInexistente = 99L;

        when(buscarPorIdUseCase.buscar(idInexistente))
                .thenThrow(new NaoEncontradoException(CODE_NOT_FOUND, idInexistente));

        when(messageService.obterMensagem(CODE_NOT_FOUND, idInexistente))
                .thenReturn(MSG_BASE_NAO_ENCONTRADA);

        mockMvc.perform(get(URL_API_BASE_AUTENTICACAO + PARAM_ID, idInexistente))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(JP_TYPE).value(TYPE_NOT_FOUND))
                .andExpect(jsonPath(JP_TITLE).value(TITLE_RECURSO_NAO_ENCONTRADO))
                .andExpect(jsonPath(JP_DETAIL).value(MSG_BASE_NAO_ENCONTRADA))
                .andExpect(jsonPath(JP_CODE).value(CODE_NOT_FOUND));

        verify(buscarPorIdUseCase).buscar(idInexistente);
    }

    @Test
    void postCriarDeveRetornar400QuandoValidacaoFalhar() throws Exception {
        String json = """
            {
              "noBaseAutenticacao": "",
              "icRecurso": "S",
              "icSinav": "N",
              "icAtivo": "S"
            }
            """;

        mockMvc.perform(post(URL_API_BASE_AUTENTICACAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(JP_TYPE).value(TYPE_VALIDATION_ERROR))
                .andExpect(jsonPath(JP_TITLE).value(TITLE_DADOS_INVALIDOS))
                .andExpect(jsonPath(JP_DETAIL).value(MSG_NOME_OBRIGATORIO))
                .andExpect(jsonPath("$.fields.noBaseAutenticacao")
                        .value(MSG_NOME_OBRIGATORIO));
    }

    @Test
    void postCriarDeveRetornar400QuandoErroDeNegocio() throws Exception {
        BaseAutenticacaoRequest req = new BaseAutenticacaoRequest(
                BASE,
                "S", "N"
        );

        var domainIn = new BaseAutenticacao();
        domainIn.setNoBaseAutenticacao(BASE);
        domainIn.setIcRecurso(SimNao.S);
        domainIn.setIcSinav(SimNao.N);

        when(mapper.toDomain(any(BaseAutenticacaoRequest.class))).thenReturn(domainIn);

        when(criarBaseAutenticacaoUseCase.criar(any()))
                .thenThrow(new NegocioException(CODE_NEGOCIO, "x"));

        when(messageService.obterMensagem(CODE_NEGOCIO, "x"))
                .thenReturn(MSG_ERRO_NEGOCIO);

        String json = objectMapper.writeValueAsString(req);

        mockMvc.perform(post(URL_API_BASE_AUTENTICACAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(JP_TYPE).value(TYPE_NEGOCIO_ERROR))
                .andExpect(jsonPath(JP_TITLE).value(TITLE_ERRO_NEGOCIO))
                .andExpect(jsonPath(JP_DETAIL).value(MSG_ERRO_NEGOCIO))
                .andExpect(jsonPath(JP_CODE).value(CODE_NEGOCIO));

        verify(mapper).toDomain(any(BaseAutenticacaoRequest.class));
        verify(criarBaseAutenticacaoUseCase).criar(any());
    }

    @Test
    void postCriarDeveRetornar500QuandoErroInterno() throws Exception {
        BaseAutenticacaoRequest req = new BaseAutenticacaoRequest(
                BASE,
                "S", "N"
        );

        var domainIn = new BaseAutenticacao();
        domainIn.setNoBaseAutenticacao(BASE);
        domainIn.setIcRecurso(SimNao.S);
        domainIn.setIcSinav(SimNao.N);

        when(mapper.toDomain(any(BaseAutenticacaoRequest.class))).thenReturn(domainIn);

        when(criarBaseAutenticacaoUseCase.criar(any()))
                .thenThrow(new RuntimeException("boom"));

        when(messageService.obterMensagem(CODE_GENERIC_INTERNAL))
                .thenReturn(MSG_ERRO_INTERNO_PADRAO);

        String json = objectMapper.writeValueAsString(req);

        mockMvc.perform(post(URL_API_BASE_AUTENTICACAO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath(JP_TYPE).value(TYPE_INTERNAL_ERROR))
                .andExpect(jsonPath(JP_TITLE).value(TITLE_ERRO_INTERNO))
                .andExpect(jsonPath(JP_DETAIL).value(MSG_ERRO_INTERNO_PADRAO))
                .andExpect(jsonPath(JP_CODE).value(CODE_GENERIC_INTERNAL));

        verify(mapper).toDomain(any(BaseAutenticacaoRequest.class));
        verify(criarBaseAutenticacaoUseCase).criar(any());
    }

    @Test
    void patchInativarDeveRetornar200ComBodyInativo() throws Exception {

        var domain = new BaseAutenticacao();
        domain.setNuBaseAutenticacao(1L);
        domain.setNoBaseAutenticacao(BASE);
        domain.setIcRecurso(SimNao.S);
        domain.setIcSinav(SimNao.N);
        domain.setIcAtivo(SimNao.N);

        BaseAutenticacaoResponse resp = new BaseAutenticacaoResponse(
                1L,
                BASE,
                "S", "N", "N"
        );

        when(inativarBaseAutenticacaoUseCase.inativar(1L)).thenReturn(domain);
        when(mapper.toResponse(domain)).thenReturn(resp);

        mockMvc.perform(patch(URL_API_BASE_AUTENTICACAO + "/{id}/inativar", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JP_NU_BASE).value(1L))
                .andExpect(jsonPath(JP_NO_BASE).value(BASE))
                .andExpect(jsonPath(JP_IC_ATIVO).value("N"));

        verify(inativarBaseAutenticacaoUseCase).inativar(1L);
        verify(mapper).toResponse(domain);
    }
    @Test
    void deleteDeveRetornar204QuandoExcluirComSucesso() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .delete(URL_API_BASE_AUTENTICACAO + PARAM_ID, 1L))
                .andExpect(status().isNoContent());

        verify(excluirBaseAutenticacaoUseCase).excluir(1L);
    }

    @Test
    void deleteDeveRetornar404QuandoNaoEncontrado() throws Exception {
        long idInexistente = 99L;

        doThrow(new br.gov.caixa.siaas_api.exceptions.NaoEncontradoException(CODE_NOT_FOUND, idInexistente))
                .when(excluirBaseAutenticacaoUseCase).excluir(idInexistente);

        when(messageService.obterMensagem(CODE_NOT_FOUND, idInexistente))
                .thenReturn(MSG_BASE_NAO_ENCONTRADA);

        mockMvc.perform(delete(URL_API_BASE_AUTENTICACAO + PARAM_ID, idInexistente))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(JP_TYPE).value(TYPE_NOT_FOUND))
                .andExpect(jsonPath(JP_TITLE).value(TITLE_RECURSO_NAO_ENCONTRADO))
                .andExpect(jsonPath(JP_DETAIL).value(MSG_BASE_NAO_ENCONTRADA))
                .andExpect(jsonPath(JP_CODE).value(CODE_NOT_FOUND));
    }

    @Test
    void deleteDeveRetornar400QuandoEmUso() throws Exception {
        String CODE_EM_USO = "error.baseautenticacao.em-uso";
        String MSG_EM_USO = "Base de Segurança não pode ser inativada pois encontra-se em uso";

        doThrow(new br.gov.caixa.siaas_api.exceptions.NegocioException(CODE_EM_USO))
                .when(excluirBaseAutenticacaoUseCase).excluir(1L);

        when(messageService.obterMensagem(CODE_EM_USO)).thenReturn(MSG_EM_USO);

        mockMvc.perform(delete(URL_API_BASE_AUTENTICACAO + PARAM_ID, 1L))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(JP_TYPE).value(TYPE_NEGOCIO_ERROR))
                .andExpect(jsonPath(JP_TITLE).value(TITLE_ERRO_NEGOCIO))
                .andExpect(jsonPath(JP_DETAIL).value(MSG_EM_USO))
                .andExpect(jsonPath(JP_CODE).value(CODE_EM_USO));
    }

    @Test
    void pesquisar_deveCobrirBranches_quandoPaginacaoNull() {
        PesquisarBaseAutenticacaoUseCase pesquisar = mock(PesquisarBaseAutenticacaoUseCase.class);
        BaseAutenticacaoWebMapper mapper = mock(BaseAutenticacaoWebMapper.class);

        BaseAutenticacaoController controller = new BaseAutenticacaoController(
                mock(InativarBaseAutenticacaoUseCase.class),
                mock(AtivarBaseAutenticacaoUseCase.class),
                mock(BuscarBaseAutenticacaoPorIdUseCase.class),
                pesquisar,
                mock(CriarBaseAutenticacaoUseCase.class),
                mock(AtualizarBaseAutenticacaoUseCase.class),
                mock(ExcluirBaseAutenticacaoUseCase.class),
                mapper
        );

        PageResult<BaseAutenticacao> page = new PageResult<>(List.of(), 0, 0, 0, 20, true, true);
        when(pesquisar.pesquisar(any(BaseAutenticacaoSearchQuery.class))).thenReturn(page);

        BaseAutenticacaoFilter filtro = new BaseAutenticacaoFilter(null, null,"S", "S", "S");

        PaginacaoResponseDTO<BaseAutenticacaoResponse> resp = controller.pesquisar(filtro, null);

        assertNotNull(resp);
        assertEquals(0L, resp.totalElements());
        assertTrue(resp.content().isEmpty());

        verify(pesquisar).pesquisar(any(BaseAutenticacaoSearchQuery.class));
        verifyNoInteractions(mapper);
    }

    @Test
    void pesquisar_deveRetornarVazio_quandoFiltroSemCampos() {
        PesquisarBaseAutenticacaoUseCase pesquisar = mock(PesquisarBaseAutenticacaoUseCase.class);
        BaseAutenticacaoWebMapper mapper = mock(BaseAutenticacaoWebMapper.class);

        BaseAutenticacaoController controller = new BaseAutenticacaoController(
                mock(InativarBaseAutenticacaoUseCase.class),
                mock(AtivarBaseAutenticacaoUseCase.class),
                mock(BuscarBaseAutenticacaoPorIdUseCase.class),
                pesquisar,
                mock(CriarBaseAutenticacaoUseCase.class),
                mock(AtualizarBaseAutenticacaoUseCase.class),
                mock(ExcluirBaseAutenticacaoUseCase.class),
                mapper
        );

        PageResult<BaseAutenticacao> page = new PageResult<>(
                List.of(), 0, 0, 0, 20, true, true
        );
        when(pesquisar.pesquisar(any(BaseAutenticacaoSearchQuery.class))).thenReturn(page);

        BaseAutenticacaoFilter filtroVazio = new BaseAutenticacaoFilter(null, null, null, null, null);

        PaginacaoResponseDTO<BaseAutenticacaoResponse> resp = controller.pesquisar(filtroVazio, null);

        assertNotNull(resp);
        assertEquals(0L, resp.totalElements());
        assertEquals(0, resp.content().size());
        assertEquals(0, resp.totalPages());
        assertEquals(0, resp.page());
        assertEquals(20, resp.size());
        assertTrue(resp.first());
        assertTrue(resp.last());

        verify(pesquisar).pesquisar(any(BaseAutenticacaoSearchQuery.class));
        verifyNoInteractions(mapper);
    }

    @Test
    void pesquisar_deveMapearQuandoHaConteudo() {
        PesquisarBaseAutenticacaoUseCase pesquisar = mock(PesquisarBaseAutenticacaoUseCase.class);
        BaseAutenticacaoWebMapper mapper = mock(BaseAutenticacaoWebMapper.class);

        BaseAutenticacaoController controller = new BaseAutenticacaoController(
                mock(InativarBaseAutenticacaoUseCase.class),
                mock(AtivarBaseAutenticacaoUseCase.class),
                mock(BuscarBaseAutenticacaoPorIdUseCase.class),
                pesquisar,
                mock(CriarBaseAutenticacaoUseCase.class),
                mock(AtualizarBaseAutenticacaoUseCase.class),
                mock(ExcluirBaseAutenticacaoUseCase.class),
                mapper
        );

        BaseAutenticacao d1 = new BaseAutenticacao();
        d1.setNuBaseAutenticacao(1L);
        BaseAutenticacao d2 = new BaseAutenticacao();
        d2.setNuBaseAutenticacao(2L);

        BaseAutenticacaoResponse r1 = new BaseAutenticacaoResponse(1L, "Base1", null, null, null);
        BaseAutenticacaoResponse r2 = new BaseAutenticacaoResponse(2L, "Base2", null, null, null);

        when(mapper.toResponse(d1)).thenReturn(r1);
        when(mapper.toResponse(d2)).thenReturn(r2);

        PageResult<BaseAutenticacao> page = new PageResult<>(
                List.of(d1, d2), 2, 1, 0, 20, true, true
        );
        when(pesquisar.pesquisar(any(BaseAutenticacaoSearchQuery.class))).thenReturn(page);

        BaseAutenticacaoFilter filter = new BaseAutenticacaoFilter(1L, "Base", null, null, null);
        PaginacaoRequestDTO pag = new PaginacaoRequestDTO(0, 20, "nuBaseAutenticacao", "ASC");

        PaginacaoResponseDTO<BaseAutenticacaoResponse> resp = controller.pesquisar(filter, pag);

        assertNotNull(resp);
        assertEquals(2, resp.totalElements());
        assertEquals(2, resp.content().size());
        assertEquals(1, resp.totalPages());
        assertEquals(0, resp.page());
        assertEquals(20, resp.size());
        assertTrue(resp.first());
        assertTrue(resp.last());
        verify(pesquisar).pesquisar(any(BaseAutenticacaoSearchQuery.class));
        verify(mapper).toResponse(d1);
        verify(mapper).toResponse(d2);
    }

    @Test
    void pesquisar_deveCobrirBranchComPaginacaoPreenchidaMesmoComSortNulo() {
        PesquisarBaseAutenticacaoUseCase pesquisar = mock(PesquisarBaseAutenticacaoUseCase.class);
        BaseAutenticacaoWebMapper mapper = mock(BaseAutenticacaoWebMapper.class);

        BaseAutenticacaoController controller = new BaseAutenticacaoController(
                mock(InativarBaseAutenticacaoUseCase.class),
                mock(AtivarBaseAutenticacaoUseCase.class),
                mock(BuscarBaseAutenticacaoPorIdUseCase.class),
                pesquisar,
                mock(CriarBaseAutenticacaoUseCase.class),
                mock(AtualizarBaseAutenticacaoUseCase.class),
                mock(ExcluirBaseAutenticacaoUseCase.class),
                mapper
        );

        BaseAutenticacao d = new BaseAutenticacao();
        d.setNuBaseAutenticacao(1L);

        BaseAutenticacaoResponse r = new BaseAutenticacaoResponse(1L, "Base", null, null, null);
        when(mapper.toResponse(d)).thenReturn(r);

        PageResult<BaseAutenticacao> page = new PageResult<>(
                List.of(d), 1, 1, 0, 20, true, true
        );
        when(pesquisar.pesquisar(any(BaseAutenticacaoSearchQuery.class))).thenReturn(page);

        BaseAutenticacaoFilter filtro = new BaseAutenticacaoFilter(null, "Base", null, null, null);

        PaginacaoRequestDTO pag = new PaginacaoRequestDTO(0, 20, null, null);

        PaginacaoResponseDTO<BaseAutenticacaoResponse> resp = controller.pesquisar(filtro, pag);

        assertNotNull(resp);
        assertEquals(1L, resp.totalElements());
        assertEquals(1, resp.content().size());

        verify(pesquisar).pesquisar(any(BaseAutenticacaoSearchQuery.class));
        verify(mapper).toResponse(d);
    }
}