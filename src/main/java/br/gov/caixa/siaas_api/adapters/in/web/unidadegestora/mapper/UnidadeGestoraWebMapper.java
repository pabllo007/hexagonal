package br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.mapper;

import br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto.BaseAutenticacaoRequest;
import br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto.BaseAutenticacaoResponse;
import br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.dto.UnidadeGestoraResponse;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import br.gov.caixa.siaas_api.domain.unidadeGestora.model.UnidadeGestora;

public class UnidadeGestoraWebMapper {

//    public BaseAutenticacao toDomain(BaseAutenticacaoRequest req) {
//        if (req == null) return null;
//
//        BaseAutenticacao domain = new BaseAutenticacao();
//        domain.setNoBaseAutenticacao(tratarNome(req.noBaseAutenticacao()));
//        domain.setIcRecurso(SimNao.fromDatabase(req.icRecurso()));
//        domain.setIcSinav(SimNao.fromDatabase(req.icSinav()));
//        return domain;
//    }

    public UnidadeGestoraResponse toResponse(UnidadeGestora domain) {
        if (domain == null) return null;

        return new UnidadeGestoraResponse(
                domain.getNuUnidadeGestora(),
                domain.getNoUnidadeGestora(),
                domain.isAtivo(),
                domain.getNuUnidade(),
                domain.getTipo() != null ? domain.getTipo().getNuTipoUnidadeGestora() : null,
                domain.getTipo() != null ? domain.getTipo().getNoTipoUnidadeGestora() : null,
                domain.getGrupoTrabalho() != null ? domain.getGrupoTrabalho().getNuGrupoTrabalho() : null,
                domain.getGrupoTrabalho() != null ? domain.getGrupoTrabalho().getNoGrupoTrabalho() : null,
                domain.getNoCaixaPostal()
        );
    }

    private String tratarNome(String nome) {
        if (nome == null) return null;
        return nome.trim().replaceAll("\\s+", " ");
    }

    private String toFlag(SimNao value) {
        return value == null ? null : value.toDatabase(); // "S"/"N"
    }
}