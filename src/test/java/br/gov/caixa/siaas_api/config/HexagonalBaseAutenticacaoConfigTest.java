package br.gov.caixa.siaas_api.config;

import br.gov.caixa.siaas_api.adapters.in.web.baseautenticacao.mapper.BaseAutenticacaoWebMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter.*;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.BaseAutenticacaoPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.BaseAutenticacaoRepository;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.ModuloRepository;
import br.gov.caixa.siaas_api.application.baseautenticacao.port.in.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HexagonalBaseAutenticacaoConfigTest {

    @Test
    void deveCriarTodosBeans_semSpringContext() {
        HexagonalBaseAutenticacaoConfig cfg = new HexagonalBaseAutenticacaoConfig();

        BaseAutenticacaoRepository baseRepo = Mockito.mock(BaseAutenticacaoRepository.class);
        ModuloRepository moduloRepo = Mockito.mock(ModuloRepository.class);

        BaseAutenticacaoPersistenceMapper mapper = cfg.baseAutenticacaoPersistenceMapper();
        assertNotNull(mapper);

        BaseAutenticacaoJpaAdapter baseAdapter = cfg.baseAutenticacaoJpaAdapter(baseRepo, mapper);
        assertNotNull(baseAdapter);

        ModuloJpaAdapter moduloAdapter = cfg.moduloJpaAdapter(moduloRepo);
        assertNotNull(moduloAdapter);

        InativarBaseAutenticacaoUseCase inativar = cfg.inativarBaseAutenticacaoUseCase(baseAdapter, moduloAdapter);
        assertNotNull(inativar);

        AtivarBaseAutenticacaoUseCase ativar = cfg.ativarBaseAutenticacaoUseCase(baseAdapter);
        assertNotNull(ativar);

        BuscarBaseAutenticacaoPorIdUseCase buscar = cfg.buscarBaseAutenticacaoPorIdUseCase(baseAdapter);
        assertNotNull(buscar);

        BaseAutenticacaoWebMapper webMapper = cfg.baseAutenticacaoWebMapper();
        assertNotNull(webMapper);

        BaseAutenticacaoSearchJpaAdapter searchAdapter = cfg.baseAutenticacaoSearchJpaAdapter(baseRepo, mapper);
        assertNotNull(searchAdapter);

        PesquisarBaseAutenticacaoUseCase pesquisar = cfg.pesquisarBaseAutenticacaoUseCase(searchAdapter);
        assertNotNull(pesquisar);

        BaseAutenticacaoValidationJpaAdapter validationAdapter = cfg.baseAutenticacaoValidationJpaAdapter(baseRepo);
        assertNotNull(validationAdapter);

        CriarBaseAutenticacaoUseCase criar = cfg.criarBaseAutenticacaoUseCase(baseAdapter, validationAdapter);
        assertNotNull(criar);

        AtualizarBaseAutenticacaoUseCase atualizar = cfg.atualizarBaseAutenticacaoUseCase(baseAdapter, validationAdapter);
        assertNotNull(atualizar);

        BaseAutenticacaoDeleteJpaAdapter deleteAdapter = cfg.baseAutenticacaoDeleteJpaAdapter(baseRepo);
        assertNotNull(deleteAdapter);

        ExcluirBaseAutenticacaoUseCase excluir = cfg.excluirBaseAutenticacaoUseCase(deleteAdapter, moduloAdapter);
        assertNotNull(excluir);
    }
}