package br.gov.caixa.siaas_api.config;

import br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.mapper.UnidadeGestoraWebMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter.UnidadeGestoraJpaAdapter;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.UnidadeGestoraPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.UnidadeGestoraRepository;
import br.gov.caixa.siaas_api.application.unidadegestora.port.in.BuscarPorCodigoETipoUseCase;
import br.gov.caixa.siaas_api.application.unidadegestora.port.in.BuscarUnidadeGestoraPorIdUseCase;
import br.gov.caixa.siaas_api.application.unidadegestora.usecase.BuscarUnidadeGestoraPorCodigoEhTipoService;
import br.gov.caixa.siaas_api.application.unidadegestora.usecase.BuscarUnidadeGestoraPorIdService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnidadeGestoraConfig {

    @Bean
    public UnidadeGestoraPersistenceMapper unidadeGestoraPersistenceMapper() {
        return new UnidadeGestoraPersistenceMapper();
    }

    @Bean
    public UnidadeGestoraJpaAdapter unidadeGestoraJpaAdapter(UnidadeGestoraRepository repo,
                                                             UnidadeGestoraPersistenceMapper mapper) {
        return new UnidadeGestoraJpaAdapter(repo, mapper);
    }

    @Bean
    public UnidadeGestoraWebMapper unidadeGestoraWebMapper() {
        return new UnidadeGestoraWebMapper();
    }


    @Bean
    public BuscarUnidadeGestoraPorIdUseCase buscarUnidadeGestoraPorIdUseCase(UnidadeGestoraJpaAdapter baseAdapter) {
        return new BuscarUnidadeGestoraPorIdService(baseAdapter);
    }

    @Bean
    public BuscarPorCodigoETipoUseCase buscarPorCodigoETipoUseCase(UnidadeGestoraJpaAdapter baseAdapter) {
        return new BuscarUnidadeGestoraPorCodigoEhTipoService(baseAdapter);
    }
}