package br.gov.caixa.siaas_api.config;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.BaseAutenticacaoEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.BaseAutenticacaoRepository;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.UnidadeGestoraEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.TipoUnidadeGestoraEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.GrupoTrabalhoEntity;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.GrupoTrabalhoRepository;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.TipoUnidadeGestoraRepository;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.UnidadeGestoraRepository;

import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Profile("local-h2")
public class LocalH2DataSeeder {

    @Bean
    @Transactional
    ApplicationRunner seedBaseAutenticacao(BaseAutenticacaoRepository repo) {
        return args -> {
            if (repo.count() > 0) return;

            repo.save(nova("Base 001", SimNao.S, SimNao.N, SimNao.S));
            repo.save(nova("Base 002", SimNao.N, SimNao.N, SimNao.S));
            repo.save(nova("Base 003", SimNao.S, SimNao.S, SimNao.N));
            repo.save(nova("Base 004", SimNao.N, SimNao.S, SimNao.N));
            repo.save(nova("SIAAS SINAV", SimNao.S, SimNao.S, SimNao.S));
            repo.save(nova("SIAAS SINAT", SimNao.S, SimNao.N, SimNao.S));

            for (int i = 5; i <= 35; i++) {
                repo.save(nova("Base " + String.format("%03d", i), SimNao.S, SimNao.N, SimNao.S));
            }
        };
    }

    /**
     * Seeder de Unidade Gestora.
     *
     * Observações importantes:
     * 1) Como UnidadeGestora tem @ManyToOne para TipoUnidadeGestora e GrupoTrabalho,
     *    você precisa popular as tabelas de apoio ANTES.
     * 2) Ajuste nomes de campos/getters/setters conforme suas Entities reais.
     */
    @Bean
    @Transactional
    ApplicationRunner seedUnidadeGestora(
            UnidadeGestoraRepository ugRepo,
            TipoUnidadeGestoraRepository tipoRepo,
            GrupoTrabalhoRepository grupoRepo
    ) {
        return args -> {
            if (ugRepo.count() > 0) return;

            seedTiposSeVazio(tipoRepo);
            seedGruposSeVazio(grupoRepo);

            TipoUnidadeGestoraEntity tipoNegocio = tipoRepo.findById(2l).orElseThrow();
            TipoUnidadeGestoraEntity tipoProducao = tipoRepo.findById(5l).orElseThrow();

            GrupoTrabalhoEntity gtBsb = grupoRepo.findById(1l).orElseThrow();
            GrupoTrabalhoEntity gtRj  = grupoRepo.findById(2l).orElseThrow();

            ugRepo.save(novaUG(11001, 99001, "ug.negocio@caixa.gov.br", SimNao.S, tipoNegocio, null));
            ugRepo.save(novaUG(11002, 99002, "ug.producao.bsb@caixa.gov.br", SimNao.S, tipoProducao, gtBsb));
            ugRepo.save(novaUG(11003, 99003, "ug.producao.rj@caixa.gov.br", SimNao.S, tipoProducao, gtRj));

            // mais registros pra testar paginação
            for (int i = 11010; i <= 11035; i++) {
                long id = 2000L + (i - 11010);
                Integer natural = 90000 + (i - 11010);
                ugRepo.save(novaUG(
                        i,
                        natural,
                        "ug" + i + "@caixa.gov.br",
                        SimNao.S,
                        (i % 2 == 0) ? tipoNegocio : tipoProducao,
                        (i % 2 == 0) ? null : gtBsb
                ));
            }
        };
    }

    private BaseAutenticacaoEntity nova(String nome, SimNao icRecurso, SimNao icSinav, SimNao icAtivo) {
        BaseAutenticacaoEntity b = new BaseAutenticacaoEntity();
        b.setNoBaseAutenticacao(nome);
        b.setIcRecurso(icRecurso);
        b.setIcSinav(icSinav);
        b.setIcAtivo(icAtivo);
        return b;
    }
    private void seedTiposSeVazio(TipoUnidadeGestoraRepository tipoRepo) {
        if (tipoRepo.count() > 0) return;

        tipoRepo.save(novoTipo(1, "DESENVOLVIMENTO", SimNao.N));
        tipoRepo.save(novoTipo(2, "NEGOCIO", SimNao.N));
        tipoRepo.save(novoTipo(3, "TI", SimNao.N));
        tipoRepo.save(novoTipo(4, "SEGURANCA", SimNao.S));
        tipoRepo.save(novoTipo(5, "PRODUCAO", SimNao.S));
    }

    private void seedGruposSeVazio(GrupoTrabalhoRepository grupoRepo) {
        if (grupoRepo.count() > 0) return;

        grupoRepo.save(novoGrupo(1, "CEPTI SEGURANCA BSB"));
        grupoRepo.save(novoGrupo(2, "CEPTI SEGURANCA RJ"));
        grupoRepo.save(novoGrupo(3, "CEPTI SEGURANCA SP"));
        grupoRepo.save(novoGrupo(4, "CETEC PEDES"));
    }

    private TipoUnidadeGestoraEntity novoTipo(int id, String nome, SimNao icGrupoTrabalho) {
        TipoUnidadeGestoraEntity t = new TipoUnidadeGestoraEntity();
        t.setNuTipoUnidadeGestora(id);
        t.setNoTipoUnidadeGestora(nome);
        t.setIcGrupoTrabalho(icGrupoTrabalho);
        return t;
    }

    private GrupoTrabalhoEntity novoGrupo(int id, String nome) {
        GrupoTrabalhoEntity g = new GrupoTrabalhoEntity();
        g.setNuGrupoTrabalho(id);
        g.setNoGrupoTrabalho(nome);
        return g;
    }

    private UnidadeGestoraEntity novaUG(
            Integer nuUnidade,
            Integer nuNatural,
            String noCaixaPostal,
            SimNao icAtivo,
            TipoUnidadeGestoraEntity tipo,
            GrupoTrabalhoEntity grupo
    ) {
        UnidadeGestoraEntity ug = new UnidadeGestoraEntity();

        ug.setNuUnidade(nuUnidade);
        ug.setNuNatural(nuNatural);

        ug.setNoCaixaPostal(noCaixaPostal);

        ug.setIcAtivo(icAtivo);

        ug.setTipoUnidadeGestora(tipo);
        ug.setGrupoTrabalho(grupo);

        return ug;
    }
}