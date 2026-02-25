package br.gov.caixa.siaas_api.application.grupotrabalho.usecase;

import br.gov.caixa.siaas_api.application.grupotrabalho.port.in.PesquisarGrupoTrabalhoUseCase;
import br.gov.caixa.siaas_api.application.grupotrabalho.port.out.GrupoTrabalhoPort;
import br.gov.caixa.siaas_api.domain.grupotrabalho.model.GrupoTrabalho;

import java.util.List;

public class PesquisarGrupoTrabalhoService implements PesquisarGrupoTrabalhoUseCase {

    private final GrupoTrabalhoPort trabalhoPort;

    public PesquisarGrupoTrabalhoService(GrupoTrabalhoPort trabalhoPort) {
        this.trabalhoPort = trabalhoPort;
    }

    @Override
    public List<GrupoTrabalho> pesquisar() {
        return trabalhoPort.findAll();
    }
}