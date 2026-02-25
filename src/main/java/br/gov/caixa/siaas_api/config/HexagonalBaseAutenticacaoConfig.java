package br.gov.caixa.siaas_api.config;

import br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.mapper.BaseAutenticacaoWebMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter.*;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.BaseAutenticacaoPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.ModuloRepository;
import br.gov.caixa.siaas_api.application.baseautenticacao.port.in.*;
import br.gov.caixa.siaas_api.application.baseautenticacao.usecase.*;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.BaseAutenticacaoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HexagonalBaseAutenticacaoConfig {

    @Bean
    public BaseAutenticacaoPersistenceMapper baseAutenticacaoPersistenceMapper() {
        return new BaseAutenticacaoPersistenceMapper();
    }

    @Bean
    public BaseAutenticacaoJpaAdapter baseAutenticacaoJpaAdapter(BaseAutenticacaoRepository repo,
                                                                 BaseAutenticacaoPersistenceMapper mapper) {
        return new BaseAutenticacaoJpaAdapter(repo, mapper);
    }

    @Bean
    public ModuloJpaAdapter moduloJpaAdapter(ModuloRepository moduloRepository) {
        return new ModuloJpaAdapter(moduloRepository);
    }

    @Bean
    public InativarBaseAutenticacaoUseCase inativarBaseAutenticacaoUseCase(BaseAutenticacaoJpaAdapter baseAdapter,
                                                                           ModuloJpaAdapter moduloAdapter) {
        return new InativarBaseAutenticacaoService(baseAdapter, moduloAdapter);
    }

    @Bean
    public BaseAutenticacaoWebMapper baseAutenticacaoWebMapper() {
        return new BaseAutenticacaoWebMapper();
    }

    @Bean
    public AtivarBaseAutenticacaoUseCase ativarBaseAutenticacaoUseCase(BaseAutenticacaoJpaAdapter baseAdapter) {
        return new AtivarBaseAutenticacaoService(baseAdapter);
    }

    @Bean
    public BuscarBaseAutenticacaoPorIdUseCase buscarBaseAutenticacaoPorIdUseCase(BaseAutenticacaoJpaAdapter baseAdapter) {
        return new BuscarBaseAutenticacaoPorIdService(baseAdapter);
    }

    @Bean
    public BaseAutenticacaoSearchJpaAdapter baseAutenticacaoSearchJpaAdapter(
            BaseAutenticacaoRepository repo,
            BaseAutenticacaoPersistenceMapper mapper
    ) {
        return new BaseAutenticacaoSearchJpaAdapter(repo, mapper);
    }

    @Bean
    public PesquisarBaseAutenticacaoUseCase pesquisarBaseAutenticacaoUseCase(
            BaseAutenticacaoSearchJpaAdapter adapter
    ) {
        return new PesquisarBaseAutenticacaoService(adapter);
    }
    @Bean
    public BaseAutenticacaoValidationJpaAdapter baseAutenticacaoValidationJpaAdapter(BaseAutenticacaoRepository repo) {
        return new BaseAutenticacaoValidationJpaAdapter(repo);
    }

    @Bean
    public CriarBaseAutenticacaoUseCase criarBaseAutenticacaoUseCase(
            BaseAutenticacaoJpaAdapter baseAdapter,
            BaseAutenticacaoValidationJpaAdapter validationAdapter
    ) {
        return new CriarBaseAutenticacaoService(baseAdapter, validationAdapter);
    }

    @Bean
    public AtualizarBaseAutenticacaoUseCase atualizarBaseAutenticacaoUseCase(
            BaseAutenticacaoJpaAdapter baseAdapter,
            BaseAutenticacaoValidationJpaAdapter validationAdapter
    ) {
        return new AtualizarBaseAutenticacaoService(baseAdapter, validationAdapter);
    }

    @Bean
    public BaseAutenticacaoDeleteJpaAdapter baseAutenticacaoDeleteJpaAdapter(BaseAutenticacaoRepository repo) {
        return new BaseAutenticacaoDeleteJpaAdapter(repo);
    }

    @Bean
    public ExcluirBaseAutenticacaoUseCase excluirBaseAutenticacaoUseCase(
            BaseAutenticacaoDeleteJpaAdapter deleteAdapter,
            ModuloJpaAdapter moduloAdapter
    ) {
        return new ExcluirBaseAutenticacaoService(deleteAdapter, moduloAdapter);
    }
}