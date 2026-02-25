package br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.rest;

import br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.dto.UnidadeGestoraResponse;
import br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.mapper.UnidadeGestoraWebMapper;
import br.gov.caixa.siaas_api.application.unidadegestora.port.in.BuscarUnidadeGestoraPorIdUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/unidade-gestora")
public class UnidadeGestoraController {

    private static final Logger log = LoggerFactory.getLogger(UnidadeGestoraController.class);

    private final BuscarUnidadeGestoraPorIdUseCase buscarPorIdUseCase;
    private final UnidadeGestoraWebMapper mapper;

    public UnidadeGestoraController(BuscarUnidadeGestoraPorIdUseCase buscarPorIdUseCase,
                                   UnidadeGestoraWebMapper mapper) {
        this.buscarPorIdUseCase = buscarPorIdUseCase;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Busca uma Unidade Gestora pelo ID",
            description = """
        Retorna os detalhes de uma Unidade Gestora existente.
        Este endpoint é restrito a usuários autenticados e com permissão
        apropriada.
        - Requer token Bearer válido.
        - Retorna 404 se o recurso não existir.
    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta realizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UnidadeGestoraResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetros inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Unidade Gestora não encontrada",
                    content = @Content(
                            mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário sem permissão para acessar o recurso",
                    content = @Content(
                            mediaType = "application/problem+json"
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token inválido ou ausente",
                    content = @Content(
                            mediaType = "application/problem+json"
                    )
            )
    })
    @GetMapping("/{id}")
    public UnidadeGestoraResponse buscarPorId(@PathVariable Long id) {
        var domain = buscarPorIdUseCase.buscar(id);
        return mapper.toResponse(domain);
    }
}
