package br.gov.caixa.siaas_api.config;

import br.gov.caixa.siaas_api.adapters.in.web.tipounidadegestora.mapper.TipoUnidadeGestoraWebMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter.TipoUnidadeGestoraJpaAdapter;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.TipoUnidadeGestoraPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.TipoUnidadeGestoraRepository;
import br.gov.caixa.siaas_api.application.tipounidadegestora.port.in.PesquisarTipoUnidadeGestoraUseCase;
import br.gov.caixa.siaas_api.application.tipounidadegestora.usecase.PesquisarTipoUnidadeGestoraService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TipoUnidadeGestoraConfigTest {

    @Test
    void tipoUnidadeGestoraPersistenceMapper_deveCriarBean() {
        TipoUnidadeGestoraConfig config = new TipoUnidadeGestoraConfig();

        TipoUnidadeGestoraPersistenceMapper bean = config.tipoUnidadeGestoraPersistenceMapper();

        assertNotNull(bean);
        assertEquals(TipoUnidadeGestoraPersistenceMapper.class, bean.getClass());
    }

    @Test
    void tipoUnidadeGestoraJpaAdapter_deveCriarBean() {
        TipoUnidadeGestoraConfig config = new TipoUnidadeGestoraConfig();

        TipoUnidadeGestoraRepository repo = mock(TipoUnidadeGestoraRepository.class);
        TipoUnidadeGestoraPersistenceMapper mapper = new TipoUnidadeGestoraPersistenceMapper();

        TipoUnidadeGestoraJpaAdapter bean = config.tipoUnidadeGestoraJpaAdapter(repo, mapper);

        assertNotNull(bean);
        assertEquals(TipoUnidadeGestoraJpaAdapter.class, bean.getClass());
    }

    @Test
    void tipoUnidadeGestoraWebMapper_deveCriarBean() {
        TipoUnidadeGestoraConfig config = new TipoUnidadeGestoraConfig();

        TipoUnidadeGestoraWebMapper bean = config.tipoUnidadeGestoraWebMapper();

        assertNotNull(bean);
        assertEquals(TipoUnidadeGestoraWebMapper.class, bean.getClass());
    }

    @Test
    void pesquisarTipoUnidadeGestoraUseCase_deveCriarBean() {
        TipoUnidadeGestoraConfig config = new TipoUnidadeGestoraConfig();

        TipoUnidadeGestoraJpaAdapter adapter = mock(TipoUnidadeGestoraJpaAdapter.class);

        PesquisarTipoUnidadeGestoraUseCase bean = config.pesquisarTipoUnidadeGestoraUseCase(adapter);

        assertNotNull(bean);
        assertTrue(bean instanceof PesquisarTipoUnidadeGestoraService);
    }
}