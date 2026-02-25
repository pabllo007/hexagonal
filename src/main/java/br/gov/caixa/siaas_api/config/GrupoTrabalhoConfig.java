package br.gov.caixa.siaas_api.config;

import br.gov.caixa.siaas_api.adapters.in.web.grupotrabalho.mapper.GrupoTrabalhoWebMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter.GrupoTrabalhoJpaAdapter;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.GrupoTrabalhoPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.GrupoTrabalhoRepository;
import br.gov.caixa.siaas_api.application.grupotrabalho.port.in.PesquisarGrupoTrabalhoUseCase;
import br.gov.caixa.siaas_api.application.grupotrabalho.usecase.PesquisarGrupoTrabalhoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrupoTrabalhoConfig {

    @Bean
    public GrupoTrabalhoPersistenceMapper grupoTrabalhoPersistenceMapper() {
        return new GrupoTrabalhoPersistenceMapper();
    }

    @Bean
    public GrupoTrabalhoJpaAdapter grupoTrabalhoJpaAdapter(GrupoTrabalhoRepository repo,
                                                           GrupoTrabalhoPersistenceMapper mapper) {
        return new GrupoTrabalhoJpaAdapter(repo, mapper);
    }

    @Bean
    public GrupoTrabalhoWebMapper grupoTrabalhoWebMapper() {
        return new GrupoTrabalhoWebMapper();
    }


    @Bean
    public PesquisarGrupoTrabalhoUseCase pesquisarGrupoTrabalhoUseCase(GrupoTrabalhoJpaAdapter baseAdapter) {
        return new PesquisarGrupoTrabalhoService(baseAdapter);
    }
}