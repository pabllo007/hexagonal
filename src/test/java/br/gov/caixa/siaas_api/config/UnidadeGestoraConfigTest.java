package br.gov.caixa.siaas_api.config;

import br.gov.caixa.siaas_api.adapters.in.web.unidadegestora.mapper.UnidadeGestoraWebMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter.UnidadeGestoraJpaAdapter;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.UnidadeGestoraPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.UnidadeGestoraRepository;
import br.gov.caixa.siaas_api.application.unidadegestora.port.in.BuscarPorCodigoETipoUseCase;
import br.gov.caixa.siaas_api.application.unidadegestora.port.in.BuscarUnidadeGestoraPorIdUseCase;
import br.gov.caixa.siaas_api.application.unidadegestora.usecase.BuscarUnidadeGestoraPorCodigoEhTipoService;
import br.gov.caixa.siaas_api.application.unidadegestora.usecase.BuscarUnidadeGestoraPorIdService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnidadeGestoraConfigTest {

    @Test
    void unidadeGestoraPersistenceMapper_deveCriarBean() {
        UnidadeGestoraConfig config = new UnidadeGestoraConfig();

        UnidadeGestoraPersistenceMapper bean = config.unidadeGestoraPersistenceMapper();

        assertNotNull(bean);
        assertEquals(UnidadeGestoraPersistenceMapper.class, bean.getClass());
    }

    @Test
    void unidadeGestoraJpaAdapter_deveCriarBean() {
        UnidadeGestoraConfig config = new UnidadeGestoraConfig();

        UnidadeGestoraRepository repo = mock(UnidadeGestoraRepository.class);
        UnidadeGestoraPersistenceMapper mapper = new UnidadeGestoraPersistenceMapper();

        UnidadeGestoraJpaAdapter bean = config.unidadeGestoraJpaAdapter(repo, mapper);

        assertNotNull(bean);
        assertEquals(UnidadeGestoraJpaAdapter.class, bean.getClass());
    }

    @Test
    void unidadeGestoraWebMapper_deveCriarBean() {
        UnidadeGestoraConfig config = new UnidadeGestoraConfig();

        UnidadeGestoraWebMapper bean = config.unidadeGestoraWebMapper();

        assertNotNull(bean);
        assertEquals(UnidadeGestoraWebMapper.class, bean.getClass());
    }

    @Test
    void buscarUnidadeGestoraPorIdUseCase_deveCriarBean() {
        UnidadeGestoraConfig config = new UnidadeGestoraConfig();

        UnidadeGestoraJpaAdapter adapter = mock(UnidadeGestoraJpaAdapter.class);

        BuscarUnidadeGestoraPorIdUseCase bean = config.buscarUnidadeGestoraPorIdUseCase(adapter);

        assertNotNull(bean);
        assertTrue(bean instanceof BuscarUnidadeGestoraPorIdService);
    }

    @Test
    void buscarUnidadeGestoraPorCodigoTipo_deveCriarBean() {
        UnidadeGestoraConfig config = new UnidadeGestoraConfig();

        UnidadeGestoraJpaAdapter adapter = mock(UnidadeGestoraJpaAdapter.class);

        BuscarPorCodigoETipoUseCase bean = config.buscarPorCodigoETipoUseCase(adapter);

        assertNotNull(bean);
        assertTrue(bean instanceof BuscarUnidadeGestoraPorCodigoEhTipoService);
    }
}