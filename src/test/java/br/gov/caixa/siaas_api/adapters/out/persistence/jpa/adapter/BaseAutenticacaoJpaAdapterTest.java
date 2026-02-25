package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.BaseAutenticacaoEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.BaseAutenticacaoPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.BaseAutenticacaoRepository;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseAutenticacaoJpaAdapterTest {

    @Mock BaseAutenticacaoRepository repository;
    @Mock BaseAutenticacaoPersistenceMapper mapper;

    @InjectMocks BaseAutenticacaoJpaAdapter adapter;

    @Test
    void findById_deveMapearParaDomain() {
        BaseAutenticacaoEntity entity = new BaseAutenticacaoEntity();
        BaseAutenticacao domain = new BaseAutenticacao();

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        Optional<BaseAutenticacao> result = adapter.findById(1L);

        assertTrue(result.isPresent());
        assertSame(domain, result.get());
        verify(repository).findById(1L);
        verify(mapper).toDomain(entity);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void create_deveMapearSalvarERetornarDomain() {
        BaseAutenticacao entrada = new BaseAutenticacao();
        BaseAutenticacaoEntity saved = new BaseAutenticacaoEntity();
        BaseAutenticacao saida = new BaseAutenticacao();

        when(repository.save(any(BaseAutenticacaoEntity.class))).thenReturn(saved);
        when(mapper.toDomain(saved)).thenReturn(saida);

        BaseAutenticacao result = adapter.create(entrada);

        assertSame(saida, result);

        ArgumentCaptor<BaseAutenticacaoEntity> captor = ArgumentCaptor.forClass(BaseAutenticacaoEntity.class);
        verify(mapper).toEntityForCreate(eq(entrada), captor.capture());
        verify(repository).save(any(BaseAutenticacaoEntity.class));
        verify(mapper).toDomain(saved);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void update_deveLancarIllegalArgument_quandoIdNull() {
        BaseAutenticacao entrada = new BaseAutenticacao();
        entrada.setNuBaseAutenticacao(null);

        assertThrows(IllegalArgumentException.class, () -> adapter.update(entrada));

        verifyNoInteractions(repository, mapper);
    }

    @Test
    void update_deveBuscarMapearSalvarERetornarDomain() {
        BaseAutenticacao entrada = new BaseAutenticacao();
        entrada.setNuBaseAutenticacao(1L);

        BaseAutenticacaoEntity entity = new BaseAutenticacaoEntity();
        BaseAutenticacaoEntity saved = new BaseAutenticacaoEntity();
        BaseAutenticacao saida = new BaseAutenticacao();

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toDomain(saved)).thenReturn(saida);

        BaseAutenticacao result = adapter.update(entrada);

        assertSame(saida, result);

        verify(repository).findById(1L);
        verify(mapper).toEntityForUpdate(entrada, entity);
        verify(repository).save(entity);
        verify(mapper).toDomain(saved);
        verifyNoMoreInteractions(repository, mapper);
    }
}