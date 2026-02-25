package br.gov.caixa.siaas_api.config;

import br.gov.caixa.siaas_api.adapters.in.web.grupotrabalho.mapper.GrupoTrabalhoWebMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter.GrupoTrabalhoJpaAdapter;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.GrupoTrabalhoPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.GrupoTrabalhoRepository;
import br.gov.caixa.siaas_api.application.grupotrabalho.port.in.PesquisarGrupoTrabalhoUseCase;
import br.gov.caixa.siaas_api.application.grupotrabalho.usecase.PesquisarGrupoTrabalhoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GrupoTrabalhoConfigTest {

    @Test
    void grupoTrabalhoPersistenceMapper_deveCriarBean() {
        GrupoTrabalhoConfig config = new GrupoTrabalhoConfig();

        GrupoTrabalhoPersistenceMapper bean = config.grupoTrabalhoPersistenceMapper();

        assertNotNull(bean);
        assertEquals(GrupoTrabalhoPersistenceMapper.class, bean.getClass());
    }

    @Test
    void grupoTrabalhoJpaAdapter_deveCriarBean() {
        GrupoTrabalhoConfig config = new GrupoTrabalhoConfig();

        GrupoTrabalhoRepository repo = mock(GrupoTrabalhoRepository.class);
        GrupoTrabalhoPersistenceMapper mapper = new GrupoTrabalhoPersistenceMapper();

        GrupoTrabalhoJpaAdapter bean = config.grupoTrabalhoJpaAdapter(repo, mapper);

        assertNotNull(bean);
        assertEquals(GrupoTrabalhoJpaAdapter.class, bean.getClass());
    }

    @Test
    void grupoTrabalhoWebMapper_deveCriarBean() {
        GrupoTrabalhoConfig config = new GrupoTrabalhoConfig();

        GrupoTrabalhoWebMapper bean = config.grupoTrabalhoWebMapper();

        assertNotNull(bean);
        assertEquals(GrupoTrabalhoWebMapper.class, bean.getClass());
    }

    @Test
    void pesquisarGrupoTrabalhoUseCase_deveCriarBean() {
        GrupoTrabalhoConfig config = new GrupoTrabalhoConfig();

        GrupoTrabalhoJpaAdapter adapter = mock(GrupoTrabalhoJpaAdapter.class);

        PesquisarGrupoTrabalhoUseCase bean = config.pesquisarGrupoTrabalhoUseCase(adapter);

        assertNotNull(bean);
        assertTrue(bean instanceof PesquisarGrupoTrabalhoService);
    }
}