package br.gov.caixa.siaas_api.application.grupotrabalho.port.in;

import br.gov.caixa.siaas_api.domain.grupotrabalho.model.GrupoTrabalho;

import java.util.List;

public interface PesquisarGrupoTrabalhoUseCase {
    List<GrupoTrabalho> pesquisar();
}
