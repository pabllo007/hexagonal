package br.gov.caixa.siaas_api.adapters.in.web.tipounidadegestora.rest;

import br.gov.caixa.siaas_api.adapters.in.web.grupotrabalho.dto.GrupoTrabalhoResponse;
import br.gov.caixa.siaas_api.adapters.in.web.grupotrabalho.mapper.GrupoTrabalhoWebMapper;
import br.gov.caixa.siaas_api.adapters.in.web.tipounidadegestora.dto.TipoUnidadeGestoraResponse;
import br.gov.caixa.siaas_api.adapters.in.web.tipounidadegestora.mapper.TipoUnidadeGestoraWebMapper;
import br.gov.caixa.siaas_api.application.grupotrabalho.port.in.PesquisarGrupoTrabalhoUseCase;
import br.gov.caixa.siaas_api.application.tipounidadegestora.port.in.PesquisarTipoUnidadeGestoraUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tipo-unidades-gestoras")
public class TipoUnidadeGestoraController {

    private static final Logger log = LoggerFactory.getLogger(TipoUnidadeGestoraController.class);
    private final PesquisarTipoUnidadeGestoraUseCase pesquisarUseCase;
    private final TipoUnidadeGestoraWebMapper mapper;

    public TipoUnidadeGestoraController(PesquisarTipoUnidadeGestoraUseCase pesquisarUseCase,
                                        TipoUnidadeGestoraWebMapper mapper) {
        this.pesquisarUseCase = pesquisarUseCase;
        this.mapper = mapper;
    }


    @Operation(
            summary = "Lista o Tipo Unidade Gestora",
            description = "Realiza a consulta dos Tipos de Unidades Gestoras."
    )
    @ApiResponses({

            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta realizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "object",
                                    implementation = GrupoTrabalhoResponse.class
                            )
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
    public List<TipoUnidadeGestoraResponse> pesquisar() {
        var result = pesquisarUseCase.pesquisar();
        return result.stream().map(mapper::toResponse).collect(Collectors.toList());
    }
}
