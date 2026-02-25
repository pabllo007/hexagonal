package br.gov.caixa.siaas_api.config;

import br.gov.caixa.siaas_api.adapters.in.web.tipounidadegestora.mapper.TipoUnidadeGestoraWebMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter.TipoUnidadeGestoraJpaAdapter;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.TipoUnidadeGestoraPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.TipoUnidadeGestoraRepository;
import br.gov.caixa.siaas_api.application.tipounidadegestora.port.in.PesquisarTipoUnidadeGestoraUseCase;
import br.gov.caixa.siaas_api.application.tipounidadegestora.usecase.PesquisarTipoUnidadeGestoraService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TipoUnidadeGestoraConfig {

    @Bean
    public TipoUnidadeGestoraPersistenceMapper tipoUnidadeGestoraPersistenceMapper() {
        return new TipoUnidadeGestoraPersistenceMapper();
    }

    @Bean
    public TipoUnidadeGestoraJpaAdapter tipoUnidadeGestoraJpaAdapter(TipoUnidadeGestoraRepository repo,
                                                                     TipoUnidadeGestoraPersistenceMapper mapper) {
        return new TipoUnidadeGestoraJpaAdapter(repo, mapper);
    }

    @Bean
    public TipoUnidadeGestoraWebMapper tipoUnidadeGestoraWebMapper() {
        return new TipoUnidadeGestoraWebMapper();
    }


    @Bean
    public PesquisarTipoUnidadeGestoraUseCase pesquisarTipoUnidadeGestoraUseCase(TipoUnidadeGestoraJpaAdapter baseAdapter) {
        return new PesquisarTipoUnidadeGestoraService(baseAdapter);
    }
}