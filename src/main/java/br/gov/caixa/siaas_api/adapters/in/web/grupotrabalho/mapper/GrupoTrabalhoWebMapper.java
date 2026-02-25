package br.gov.caixa.siaas_api.adapters.in.web.grupotrabalho.mapper;

import br.gov.caixa.siaas_api.adapters.in.web.grupotrabalho.dto.GrupoTrabalhoResponse;
import br.gov.caixa.siaas_api.domain.grupotrabalho.model.GrupoTrabalho;

public class GrupoTrabalhoWebMapper {

    public GrupoTrabalhoResponse toResponse(GrupoTrabalho domain) {
        if (domain == null) return null;

        return new GrupoTrabalhoResponse(
                domain.getNuGrupoTrabalho(),
                domain.getNoGrupoTrabalho()
        );
    }
}