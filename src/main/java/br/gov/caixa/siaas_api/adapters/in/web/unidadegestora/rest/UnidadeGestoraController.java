package br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.rest;

import br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.dto.PaginacaoRequestDTO;
import br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.dto.PaginacaoResponseDTO;
import br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.mapper.PaginacaoMapper;
import br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.dto.UnidadeGestoraFilter;
import br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.dto.UnidadeGestoraResponse;
import br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.mapper.UnidadeGestoraWebMapper;
import br.gov.caixa.siaas_api.application.unidadegestora.port.in.BuscarPorCodigoETipoUseCase;
import br.gov.caixa.siaas_api.application.unidadegestora.port.in.BuscarUnidadeGestoraPorIdUseCase;
import br.gov.caixa.siaas_api.application.unidadegestora.port.in.PesquisarUnidadeGestoraUseCase;
import br.gov.caixa.siaas_api.application.unidadegestora.query.UnidadeGestoraSearchQuery;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/unidade-gestora")
public class UnidadeGestoraController {

    private final BuscarUnidadeGestoraPorIdUseCase buscarPorIdUseCase;
    private final BuscarPorCodigoETipoUseCase buscarPorCodigoETipoUseCase;
    private final PesquisarUnidadeGestoraUseCase pesquisarUseCase;
    private final UnidadeGestoraWebMapper mapper;

    public UnidadeGestoraController(BuscarUnidadeGestoraPorIdUseCase buscarPorIdUseCase,
                                    BuscarPorCodigoETipoUseCase buscarPorCodigoETipoUseCase,
                                    PesquisarUnidadeGestoraUseCase pesquisarUseCase,
                                   UnidadeGestoraWebMapper mapper) {
        this.buscarPorIdUseCase = buscarPorIdUseCase;
        this.buscarPorCodigoETipoUseCase = buscarPorCodigoETipoUseCase;
        this.pesquisarUseCase = pesquisarUseCase;
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


    @Operation(
            summary = "Busca uma Unidade Gestora por código e tipo",
            description = "Retorna uma Unidade Gestora a partir dos parâmetros obrigatórios codigo e tipo."
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
            )
    })
    @GetMapping("/codigo-tipo")
    public UnidadeGestoraResponse buscarPorCodigoETipo(
            @RequestParam Long codigo,
            @RequestParam Long tipo
    ) {
        var domain = buscarPorCodigoETipoUseCase.buscarPorCodigoEhTipo(codigo, tipo);
        return mapper.toResponse(domain);
    }

    @Operation(
            summary = "Pesquisa paginada de Unidade Gestora",
            description = """
        Realiza a consulta paginada e filtrada de Unidades Gestoras.
        Filtros disponíveis: nuUnidade (código), noCaixaPostal (email),
        nuTipoUnidadeGestora (tipo) e icAtivo (status).
        O filtro por noUnidadeGestora (nome) está reservado para integração
        futura com a API SIICO.
        Caso parâmetros de paginação não sejam enviados, os valores padrão
        serão utilizados (page=0, size=20, sortBy='id', direction='ASC').
    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta realizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "object",
                                    implementation = PaginacaoResponseDTO.class
                            )
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
                    responseCode = "401",
                    description = "Não autenticado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Sem permissão",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @GetMapping
    public PaginacaoResponseDTO<UnidadeGestoraResponse> pesquisar(
            UnidadeGestoraFilter filtro,
            PaginacaoRequestDTO paginacao
    ) {
        var query = new UnidadeGestoraSearchQuery(
                filtro.nuUnidade(),
                filtro.noUnidadeGestora(),
                filtro.noCaixaPostal(),
                filtro.nuTipoUnidadeGestora(),
                toSimNao(filtro.icAtivo()),
                paginacao != null ? paginacao.page() : null,
                paginacao != null ? paginacao.size() : null,
                paginacao != null ? paginacao.sortBy() : null,
                paginacao != null ? paginacao.direction() : null
        );

        var result = pesquisarUseCase.pesquisar(query);

        return PaginacaoMapper.paraRespostaPaginada(result, mapper::toResponse);
    }

    private SimNao toSimNao(String flag) {
        return flag == null ? null : SimNao.fromDatabase(flag);
    }

}
