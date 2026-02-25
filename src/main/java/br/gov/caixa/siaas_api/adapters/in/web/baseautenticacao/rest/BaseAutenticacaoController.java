package br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.rest;

import br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.mapper.BaseAutenticacaoWebMapper;
import br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto.BaseAutenticacaoFilter;
import br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.dto.PaginacaoRequestDTO;
import br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.dto.PaginacaoResponseDTO;
import br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto.BaseAutenticacaoRequest;
import br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto.BaseAutenticacaoResponse;
import br.gov.caixa.siaas_api.adapters.in.web.comum.paginacao.mapper.PaginacaoMapper;
import br.gov.caixa.siaas_api.application.baseautenticacao.port.in.*;
import br.gov.caixa.siaas_api.application.baseautenticacao.query.BaseAutenticacaoSearchQuery;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/base-autenticacoes")
public class BaseAutenticacaoController {

    private static final Logger log = LoggerFactory.getLogger(BaseAutenticacaoController.class);
    private final InativarBaseAutenticacaoUseCase inativarUseCase;
    private final BaseAutenticacaoWebMapper mapper;
    private final AtivarBaseAutenticacaoUseCase ativarUseCase;
    private final BuscarBaseAutenticacaoPorIdUseCase buscarPorIdUseCase;
    private final PesquisarBaseAutenticacaoUseCase pesquisarUseCase;
    private final CriarBaseAutenticacaoUseCase criarUseCase;
    private final AtualizarBaseAutenticacaoUseCase atualizarUseCase;
    private final ExcluirBaseAutenticacaoUseCase excluirUseCase;

    public BaseAutenticacaoController(InativarBaseAutenticacaoUseCase inativarUseCase,
                                      AtivarBaseAutenticacaoUseCase ativarUseCase,
                                      BuscarBaseAutenticacaoPorIdUseCase buscarPorIdUseCase,
                                      PesquisarBaseAutenticacaoUseCase pesquisarUseCase,
                                      CriarBaseAutenticacaoUseCase criarUseCase,
                                      AtualizarBaseAutenticacaoUseCase atualizarUseCase,
                                      ExcluirBaseAutenticacaoUseCase excluirUseCase,
                                      BaseAutenticacaoWebMapper mapper) {
        this.inativarUseCase = inativarUseCase;
        this.ativarUseCase = ativarUseCase;
        this.buscarPorIdUseCase = buscarPorIdUseCase;
        this.pesquisarUseCase = pesquisarUseCase;
        this.criarUseCase = criarUseCase;
        this.atualizarUseCase = atualizarUseCase;
        this.excluirUseCase = excluirUseCase;
        this.mapper = mapper;
    }


    @Operation(
            summary = "Cria uma nova Base de Autenticação",
            description = """
            Cria uma nova Base de Autenticação com os dados fornecidos no corpo da requisição.
            Após a criação, o endpoint retorna:
            - Status 201 (Created)
            - Header Location apontando para a URL do recurso criado
            - O corpo da resposta com os dados da Base de Autenticação criada
            Requer token Bearer válido.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = """
                    Base de autenticação criada com sucesso.
                    O header 'Location' retorna a URL completa do recurso criado.
                """,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BaseAutenticacaoResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos na requisição.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token inválido ou ausente.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário sem permissão para criar este recurso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<BaseAutenticacaoResponse> criar(@Valid @RequestBody BaseAutenticacaoRequest request) {

        var domainIn = mapper.toDomain(request);
        var domainOut = criarUseCase.criar(domainIn);
        var resposta = mapper.toResponse(domainOut);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resposta.nuBaseAutenticacao())
                .toUri();

        return ResponseEntity.created(location).body(resposta);
    }

    @Operation(
            summary = "Atualiza uma base de autenticação",
            description = "Atualiza os dados de uma base de autenticação existente pelo seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Base de autenticação atualizada com sucesso."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos na requisição.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Base de autenticação não encontrada.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token inválido ou ausente"),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário sem permissão para atualizar este recurso.")
    })
    @PutMapping("/{id}")
    public BaseAutenticacaoResponse atualizar(@PathVariable Long id,
                                              @Valid @RequestBody BaseAutenticacaoRequest request) {
        var domainIn = mapper.toDomain(request);
        var domainOut = atualizarUseCase.atualizar(id, domainIn);
        return mapper.toResponse(domainOut);
    }


    @Operation(
            summary = "Busca uma Base de Autenticação pelo ID",
            description = """
        Retorna os detalhes de uma Base de Autenticação existente.  
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
                            schema = @Schema(implementation = BaseAutenticacaoResponse.class)
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
                    description = "Base de Autenticação não encontrada",
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
    public BaseAutenticacaoResponse buscarPorId(@PathVariable Long id) {
        var domain = buscarPorIdUseCase.buscar(id);
        return mapper.toResponse(domain);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        excluirUseCase.excluir(id);
    }

    @Operation(
            summary = "Pesquisa paginada de Bases de Autenticação",
            description = """
        Realiza a consulta paginada e filtrada das Bases de Autenticação.
        Todos os parâmetros de filtro são opcionais.
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
    public PaginacaoResponseDTO<BaseAutenticacaoResponse> pesquisar(
            BaseAutenticacaoFilter filtro, PaginacaoRequestDTO paginacao) {

        var query = new BaseAutenticacaoSearchQuery(
                filtro.nuBaseAutenticacao(),
                filtro.noBaseAutenticacao(),
                toSimNao(filtro.icRecurso()),
                toSimNao(filtro.icSinav()),
                toSimNao(filtro.icAtivo()),
                paginacao != null ? paginacao.page() : null,
                paginacao != null ? paginacao.size() : null,
                paginacao != null ? paginacao.sortBy() : null,
                paginacao != null ? paginacao.direction() : null
        );

        var result = pesquisarUseCase.pesquisar(query);

        return PaginacaoMapper
                .paraRespostaPaginada(result, mapper::toResponse);
    }
    @Operation(
            summary = "Ativa uma base de autenticação",
            description = """
        Ativa uma base de autenticação existente, alterando o indicador de ativo para 'S'.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Base de autenticação ativada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BaseAutenticacaoResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Base de autenticação não encontrada.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token inválido ou ausente.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário sem permissão para ativar este recurso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PatchMapping("/{id}/ativar")
    public BaseAutenticacaoResponse ativar(@PathVariable Long id) {
        var domain = ativarUseCase.ativar(id);
        return mapper.toResponse(domain);
    }
    @Operation(
            summary = "Inativa uma base de autenticação",
            description = """
        Inativa uma base de autenticação existente, alterando o indicador de ativo para 'N'.
        """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Base de autenticação inativada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BaseAutenticacaoResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Base de autenticação não encontrada.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token inválido ou ausente.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Usuário sem permissão para inativar este recurso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    @PatchMapping("/{id}/inativar")
    public BaseAutenticacaoResponse inativar(@PathVariable Long id) {
        var domain = inativarUseCase.inativar(id);
        return mapper.toResponse(domain);
    }
    private SimNao toSimNao(String flag) {
        return flag == null ? null : SimNao.fromDatabase(flag);
    }
}
