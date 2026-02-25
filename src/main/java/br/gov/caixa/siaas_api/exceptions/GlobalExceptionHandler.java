package br.gov.caixa.siaas_api.exceptions;

import br.gov.caixa.siaas_api.mensagens.MessageService;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_GENERIC_INTERNAL = "error.generic.internal";

    private final MessageService messageService;

    public GlobalExceptionHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<ProblemDetail> tratarNaoEncontrado(NaoEncontradoException ex) {
        String detail = messageService.obterMensagem(ex.getCode(), ex.getArgs());

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setType(URI.create("urn:simpa:base-autenticacao:not-found"));
        pd.setTitle("Recurso não encontrado");
        pd.setDetail(detail);

        pd.setProperty("code", ex.getCode());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ProblemDetail> tratarNegocio(NegocioException ex) {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("Erro de negócio");

        String mensagem = messageService.obterMensagem(ex.getCode(), ex.getArgs());
        pb.setDetail(mensagem);

        pb.setType(URI.create("urn:simpa:negocio:erro"));
        pb.setProperty("code", ex.getCode());

        return ResponseEntity.badRequest().body(pb);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage,
                        (m1, m2) -> m1
                ));

        String primeiraMensagem = errors.values().stream().findFirst()
                .orElse("Um ou mais campos estão inválidos.");

        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("Dados inválidos");
        pb.setDetail(primeiraMensagem);
        pb.setProperty("fields", errors);
        pb.setType(URI.create("urn:simpa:validation:error"));

        return ResponseEntity.badRequest().body(pb);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleEnumError(HttpMessageNotReadableException ex) {

        Throwable root = ex.getMostSpecificCause();
        String detail = "JSON inválido";

        if (root instanceof InvalidFormatException ife) {

            String field = ife.getPath().get(0).getFieldName();

            detail = switch (field) {
                case "icRecurso" -> "O indicador de recurso é obrigatório e deve ser 'S' ou 'N'.";
                case "icSinav" -> "O indicador de SINAV é obrigatório e deve ser 'S' ou 'N'.";
                case "icAtivo" -> "O indicador de ativo é obrigatório e deve ser 'S' ou 'N'.";
                default -> "Valor inválido no campo '" + field + "'.";
            };
        }

        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("Dados inválidos");
        pb.setDetail(detail);
        pb.setType(URI.create("urn:simpa:json:invalid"));
        pb.setProperty("code", "error.json.invalid");

        return ResponseEntity.badRequest().body(pb);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> tratarGenerico(Exception ex) {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pb.setTitle("Erro interno");

        String mensagem = messageService.obterMensagem(ERROR_GENERIC_INTERNAL);
        pb.setDetail(mensagem);

        pb.setType(URI.create("urn:simpa:erro:interno"));
        pb.setProperty("code", ERROR_GENERIC_INTERNAL);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pb);
    }
}