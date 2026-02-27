package br.gov.caixa.siaas_api.exceptions;

import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import br.gov.caixa.siaas_api.mensagens.MessageService;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final MessageService messageService =
            mock(MessageService.class);
    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler(messageService);

    private static final String ERROR_BASEAUTENTICACAO_NOT_FOUNT = "error.baseautenticacao.not-found";
    private static final String ERROR_NEGOCIO = "error.negocio";
    private static final String ERRO_DE_NEGOCIO = "Erro de negócio";
    private static final String NAO_ENCONTRADO = "Não encontrada";
    private static final String ERROR_GENERIC_INTERNAL = "error.generic.internal";
    private static final String ERRO_INTERNO = "Erro interno";
    private static final String STRING_X = "x";
    private static final String MESSAGE_NOME_BASE_OBRIGATORIO = " O nome da base de autenticação é obrigatório.";
    private static final String STR_CAMPO1 = "campo1";
    private static final String STR_CAMPO2 = "campo2";


    @Test
    void tratarNaoEncontradoDeveRetornar404() {
        NaoEncontradoException ex =
                new NaoEncontradoException(ERROR_BASEAUTENTICACAO_NOT_FOUNT, 1L);

        when(messageService.obterMensagem(ERROR_BASEAUTENTICACAO_NOT_FOUNT, 1L))
                .thenReturn(NAO_ENCONTRADO);

        ResponseEntity<ProblemDetail> resposta =
                handler.tratarNaoEncontrado(ex);

        ProblemDetail corpo = resposta.getBody();

        assertEquals(404, resposta.getStatusCode().value());
        assertNotNull(corpo);
        assertEquals("Recurso não encontrado", corpo.getTitle());
        assertEquals(NAO_ENCONTRADO, corpo.getDetail());
        assertEquals("urn:simpa:base-autenticacao:not-found", corpo.getType().toString());
        assertEquals(ERROR_BASEAUTENTICACAO_NOT_FOUNT, corpo.getProperties().get("code"));
    }

    @Test
    void tratarNegocioDeveRetornar400() {
        NegocioException ex =
                new NegocioException(ERROR_NEGOCIO, STRING_X);

        when(messageService.obterMensagem(ERROR_NEGOCIO, STRING_X))
                .thenReturn(ERRO_DE_NEGOCIO);

        ResponseEntity<ProblemDetail> resposta =
                handler.tratarNegocio(ex);

        ProblemDetail corpo = resposta.getBody();

        assertEquals(400, resposta.getStatusCode().value());
        assertNotNull(corpo);
        assertEquals(ERRO_DE_NEGOCIO, corpo.getTitle());
        assertEquals(ERRO_DE_NEGOCIO, corpo.getDetail());
        assertEquals("urn:simpa:negocio:erro", corpo.getType().toString());
        assertEquals(ERROR_NEGOCIO, corpo.getProperties().get("code"));
    }

    @Test
    void tratarGenericoDeveRetornar500() {

        when(messageService.obterMensagem(ERROR_GENERIC_INTERNAL))
                .thenReturn(ERRO_INTERNO);

        ResponseEntity<ProblemDetail> resposta =
                handler.tratarGenerico(new RuntimeException("boom"));

        ProblemDetail corpo = resposta.getBody();

        assertEquals(500, resposta.getStatusCode().value());
        assertNotNull(corpo);
        assertEquals(ERRO_INTERNO, corpo.getTitle());
        assertEquals(ERRO_INTERNO, corpo.getDetail());
        assertEquals("urn:simpa:erro:interno", corpo.getType().toString());
        assertEquals(ERROR_GENERIC_INTERNAL, corpo.getProperties().get("code"));
    }

    @Test
    void handleValidationErrorsDeveRetornar400ComCampos() throws Exception {
        Object target = new Object();
        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(target, "baseAutenticacaoRequest");

        bindingResult.addError(new FieldError(
                "baseAutenticacaoRequest",
                "noBaseAutenticacao",
                "",
                false,
                null,
                null,
                MESSAGE_NOME_BASE_OBRIGATORIO
        ));

        MethodParameter methodParameter = mock(MethodParameter.class);

        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(methodParameter, bindingResult);

        ResponseEntity<ProblemDetail> resposta =
                handler.handleValidationErrors(ex);

        assertEquals(HttpStatus.BAD_REQUEST.value(), resposta.getStatusCode().value());
        ProblemDetail body = resposta.getBody();
        assertNotNull(body);
        assertEquals("Dados inválidos", body.getTitle());
        assertEquals(MESSAGE_NOME_BASE_OBRIGATORIO, body.getDetail());
        assertEquals(URI.create("urn:simpa:validation:error"), body.getType());

        Object fieldsObj = body.getProperties().get("fields");
        assertNotNull(fieldsObj);
        @SuppressWarnings("unchecked")
        Map<String, String> fields = (Map<String, String>) fieldsObj;

        assertEquals(1, fields.size());
        assertEquals(MESSAGE_NOME_BASE_OBRIGATORIO,
                fields.get("noBaseAutenticacao"));
    }
    @Test
    void handleEnumErrorDeveRetornar400ComMensagemEspecificaParaCampoEnum() {
        InvalidFormatException invalidFormatException =
                new InvalidFormatException(
                        null,
                        "Valor inválido para enum",
                        "",
                        SimNao.class
                );

        invalidFormatException.prependPath(Object.class, "icRecurso");

        HttpMessageNotReadableException ex =
                new HttpMessageNotReadableException("JSON parse error", invalidFormatException);

        ResponseEntity<ProblemDetail> resposta =
                handler.handleEnumError(ex);

        assertEquals(HttpStatus.BAD_REQUEST.value(), resposta.getStatusCode().value());
        ProblemDetail body = resposta.getBody();
        assertNotNull(body);
        assertEquals("Dados inválidos", body.getTitle());
        assertEquals("O indicador de recurso é obrigatório e deve ser 'S' ou 'N'.",
                body.getDetail());
        assertEquals(URI.create("urn:simpa:json:invalid"), body.getType());
        assertEquals("error.json.invalid", body.getProperties().get("code"));
    }

    @Test
    void handleValidationErrors_deveCobrirMergeFunction_quandoMesmoFieldRepetido() {
        MessageService ms = mock(MessageService.class);
        GlobalExceptionHandler handler = new GlobalExceptionHandler(ms);

        BeanPropertyBindingResult br = new BeanPropertyBindingResult(new Object(), "obj");

        br.addError(new FieldError("obj", STR_CAMPO1, "msg1"));
        br.addError(new FieldError("obj", STR_CAMPO1, "msg2"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, br);

        ResponseEntity<ProblemDetail> resp = handler.handleValidationErrors(ex);

        assertNotNull(resp);
        assertEquals(400, resp.getStatusCode().value());
        assertNotNull(resp.getBody());

        Object fields = resp.getBody().getProperties().get("fields");
        assertNotNull(fields);
    }
    @Test
    void handleValidationErrors_deveExecutarLambdaInterno() {
        MessageService ms = mock(MessageService.class);
        when(ms.obterMensagem(anyString(), any())).thenReturn("msg");

        GlobalExceptionHandler handler = new GlobalExceptionHandler(ms);

        BeanPropertyBindingResult br = new BeanPropertyBindingResult(new Object(), "obj");
        br.addError(new FieldError("obj", STR_CAMPO1, "erro1"));
        br.addError(new FieldError("obj", STR_CAMPO2, "erro2"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, br);

        ResponseEntity<ProblemDetail> resp = handler.handleValidationErrors(ex);

        assertEquals(400, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
    }

    @Test
    void handleValidationErrors_deveExecutarLambdaParaCadaFieldError() {
        MessageService ms = mock(MessageService.class);
        when(ms.obterMensagem(anyString(), any())).thenReturn("msg");

        GlobalExceptionHandler handler = new GlobalExceptionHandler(ms);

        Object alvo = new Object();
        BeanPropertyBindingResult br = new BeanPropertyBindingResult(alvo, "obj");
        br.addError(new FieldError("obj", STR_CAMPO1, "erro1"));
        br.addError(new FieldError("obj", STR_CAMPO2, "erro2"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, br);

        ResponseEntity<ProblemDetail> resp = handler.handleValidationErrors(ex);

        assertNotNull(resp);
        assertEquals(400, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
    }

    @Test
    void handleEnumError_deveTratarQuandoCausaEhIllegalArgument() {
        MessageService ms = mock(MessageService.class);
        when(ms.obterMensagem(anyString(), any())).thenReturn("msg");

        GlobalExceptionHandler handler = new GlobalExceptionHandler(ms);

        HttpMessageNotReadableException ex =
                new HttpMessageNotReadableException("x", new IllegalArgumentException("bad"));

        ResponseEntity<ProblemDetail> resp = handler.handleEnumError(ex);

        assertNotNull(resp);
        assertEquals(400, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
    }

    @Test
    void handleEnumError_deveTratarQuandoCausaNaoEhIllegalArgument() {
        MessageService ms = mock(MessageService.class);
        when(ms.obterMensagem(anyString(), any())).thenReturn("msg");

        GlobalExceptionHandler handler = new GlobalExceptionHandler(ms);

        HttpMessageNotReadableException ex =
                new HttpMessageNotReadableException("x", new RuntimeException("boom"));

        ResponseEntity<ProblemDetail> resp = handler.handleEnumError(ex);

        assertNotNull(resp);
        assertEquals(400, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
    }

    @Test
    void handleEnumError_deveCobrirCase_icRecurso() {
        ResponseEntity<ProblemDetail> resp = executarEnumErrorComCampo("icRecurso");
        assertEquals(400, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertTrue(resp.getBody().getDetail().contains("recurso"));
    }

    @Test
    void handleEnumError_deveCobrirCase_icSinav() {
        ResponseEntity<ProblemDetail> resp = executarEnumErrorComCampo("icSinav");
        assertEquals(400, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertTrue(resp.getBody().getDetail().contains("SINAV"));
    }

    @Test
    void handleEnumError_deveCobrirCase_icAtivo() {
        ResponseEntity<ProblemDetail> resp = executarEnumErrorComCampo("icAtivo");
        assertEquals(400, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertTrue(resp.getBody().getDetail().contains("ativo"));
    }

    @Test
    void handleEnumError_deveCobrirDefault() {
        ResponseEntity<ProblemDetail> resp = executarEnumErrorComCampo("outroCampo");
        assertEquals(400, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertTrue(resp.getBody().getDetail().contains("outroCampo"));
    }

    private ResponseEntity<ProblemDetail> executarEnumErrorComCampo(String fieldName) {
        MessageService ms = mock(MessageService.class);
        GlobalExceptionHandler handler = new GlobalExceptionHandler(ms);

        InvalidFormatException.Reference ref = mock(InvalidFormatException.Reference.class);
        when(ref.getFieldName()).thenReturn(fieldName);

        InvalidFormatException ife = mock(InvalidFormatException.class);
        when(ife.getPath()).thenReturn(List.of(ref));

        HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
        when(ex.getMostSpecificCause()).thenReturn(ife);

        return handler.handleEnumError(ex);
    }
    @Test
    void handleEnumError_quandoNaoInvalidFormatException_deveRetornarJsonInvalido() {
        MessageService ms = mock(MessageService.class);
        GlobalExceptionHandler handler = new GlobalExceptionHandler(ms);

        HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
        when(ex.getMostSpecificCause()).thenReturn(new RuntimeException("x"));

        ResponseEntity<ProblemDetail> resp = handler.handleEnumError(ex);

        assertEquals(400, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertEquals("JSON inválido", resp.getBody().getDetail());
    }

    @Test
    void handleEnumError_quandoCausaEhIllegalArgument_deveRetornar400() {
        MessageService ms = mock(MessageService.class);
        when(ms.obterMensagem(anyString(), any())).thenReturn("msg");

        GlobalExceptionHandler handler = new GlobalExceptionHandler(ms);

        HttpMessageNotReadableException ex =
                new HttpMessageNotReadableException("x", new IllegalArgumentException("bad"));

        ResponseEntity<ProblemDetail> resp = handler.handleEnumError(ex);

        assertEquals(400, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
    }

    @Test
    void handleEnumError_quandoCausaEhOutraException_deveRetornar400() {
        MessageService ms = mock(MessageService.class);
        when(ms.obterMensagem(anyString(), any())).thenReturn("msg");

        GlobalExceptionHandler handler = new GlobalExceptionHandler(ms);

        HttpMessageNotReadableException ex =
                new HttpMessageNotReadableException("x", new RuntimeException("boom"));

        ResponseEntity<ProblemDetail> resp = handler.handleEnumError(ex);

        assertEquals(400, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
    }

    @Test
    void handleEnumError_quandoCausaNull_deveRetornar400() {
        MessageService ms = mock(MessageService.class);
        when(ms.obterMensagem(anyString(), any())).thenReturn("msg");

        GlobalExceptionHandler handler = new GlobalExceptionHandler(ms);

        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("x");

        ResponseEntity<ProblemDetail> resp = handler.handleEnumError(ex);

        assertEquals(400, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
    }

    @Test
    void handlePropertyReferenceException_deveRetornar400ComMensagemAmigavel() {
        PropertyReferenceException ex = mock(PropertyReferenceException.class);
        when(ex.getPropertyName()).thenReturn("asdad");

        ResponseEntity<ProblemDetail> resp = handler.handlePropertyReferenceException(ex);

        assertEquals(400, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertEquals("Parâmetro de ordenação inválido", resp.getBody().getTitle());
        assertEquals("Campo de ordenação inválido: 'asdad'.", resp.getBody().getDetail());
        assertEquals("urn:simpa:query:invalid-sort", resp.getBody().getType().toString());
        assertEquals("error.query.invalid-sort", resp.getBody().getProperties().get("code"));
    }
}
