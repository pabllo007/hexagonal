package br.gov.caixa.siaas_api.application.grupotrabalho.port.out;


import br.gov.caixa.siaas_api.domain.grupotrabalho.model.GrupoTrabalho;

import java.util.List;

public interface GrupoTrabalhoPort {
    List<GrupoTrabalho> findAll();
}