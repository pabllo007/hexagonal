package br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.mapper;

import br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto.BaseAutenticacaoRequest;
import br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.dto.BaseAutenticacaoResponse;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;

public class BaseAutenticacaoWebMapper {

    public BaseAutenticacao toDomain(BaseAutenticacaoRequest req) {
        if (req == null) return null;

        BaseAutenticacao domain = new BaseAutenticacao();
        domain.setNoBaseAutenticacao(tratarNome(req.noBaseAutenticacao()));
        domain.setIcRecurso(SimNao.fromDatabase(req.icRecurso()));
        domain.setIcSinav(SimNao.fromDatabase(req.icSinav()));
        // icAtivo normalmente não vem no create/update; se vier, converta também
        return domain;
    }

    public BaseAutenticacaoResponse toResponse(BaseAutenticacao domain) {
        if (domain == null) return null;

        return new BaseAutenticacaoResponse(
                domain.getNuBaseAutenticacao(),
                domain.getNoBaseAutenticacao(),
                toFlag(domain.getIcRecurso()),
                toFlag(domain.getIcSinav()),
                toFlag(domain.getIcAtivo())
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