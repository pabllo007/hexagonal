package br.gov.caixa.siaas_api.adapters.in.web.grupotrabalho.rest;

import br.gov.caixa.siaas_api.adapters.in.web.grupotrabalho.dto.GrupoTrabalhoResponse;
import br.gov.caixa.siaas_api.adapters.in.web.grupotrabalho.mapper.GrupoTrabalhoWebMapper;
import br.gov.caixa.siaas_api.application.grupotrabalho.port.in.PesquisarGrupoTrabalhoUseCase;
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
@RequestMapping("/api/v1/grupo-trabalhos")
public class GrupoTrabalhoController {

    private static final Logger log = LoggerFactory.getLogger(GrupoTrabalhoController.class);
    private final PesquisarGrupoTrabalhoUseCase pesquisarUseCase;
    private final GrupoTrabalhoWebMapper mapper;

    public GrupoTrabalhoController(PesquisarGrupoTrabalhoUseCase pesquisarUseCase,
                                   GrupoTrabalhoWebMapper mapper) {
        this.pesquisarUseCase = pesquisarUseCase;
        this.mapper = mapper;
    }


    @Operation(
            summary = "Lista o grupo de trabalho",
            description = "Realiza a consulta dos Grupos de trabalho."
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
    public List<GrupoTrabalhoResponse> pesquisar() {
        var result = pesquisarUseCase.pesquisar();
        return result.stream().map(mapper::toResponse).collect(Collectors.toList());
    }
}
