package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.adapter;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.entity.BaseAutenticacaoEntity;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.mapper.BaseAutenticacaoPersistenceMapper;
import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.repository.BaseAutenticacaoRepository;
import br.gov.caixa.siaas_api.application.baseautenticacao.query.BaseAutenticacaoSearchQuery;
import br.gov.caixa.siaas_api.application.comum.paginacao.PageResult;
import br.gov.caixa.siaas_api.domain.baseautenticacao.model.BaseAutenticacao;
import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseAutenticacaoSearchJpaAdapterTest {

    @Mock
    private BaseAutenticacaoRepository repositorio;

    @Mock
    private BaseAutenticacaoPersistenceMapper mapper;

    @InjectMocks
    private BaseAutenticacaoSearchJpaAdapter adapter;

    private final String STR_NOBASEAUTENTICACAO = "noBaseAutenticacao";

    @Test
    void search_deveRetornarPageResult_mapearConteudo_eUsarDefaultSortQuandoNulo() {
        BaseAutenticacaoSearchQuery q = mock(BaseAutenticacaoSearchQuery.class);
        when(q.page()).thenReturn(null);
        when(q.size()).thenReturn(null);
        when(q.sortBy()).thenReturn(null);
        when(q.direction()).thenReturn(null);

        when(q.nuBaseAutenticacao()).thenReturn(10L);
        when(q.noBaseAutenticacao()).thenReturn("Base");
        when(q.icRecurso()).thenReturn(SimNao.S);
        when(q.icSinav()).thenReturn((SimNao.N));
        when(q.icAtivo()).thenReturn(null);

        BaseAutenticacaoEntity e1 = new BaseAutenticacaoEntity();
        BaseAutenticacaoEntity e2 = new BaseAutenticacaoEntity();
        List<BaseAutenticacaoEntity> entities = List.of(e1, e2);

        BaseAutenticacao d1 = new BaseAutenticacao();
        BaseAutenticacao d2 = new BaseAutenticacao();

        when(mapper.toDomain(e1)).thenReturn(d1);
        when(mapper.toDomain(e2)).thenReturn(d2);

        Page<BaseAutenticacaoEntity> page = new PageImpl<>(
                entities,
                PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "nuBaseAutenticacao")),
                2
        );

        ArgumentCaptor<Specification<BaseAutenticacaoEntity>> specCaptor = ArgumentCaptor.forClass(Specification.class);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        when(repositorio.findAll(specCaptor.capture(), pageableCaptor.capture())).thenReturn(page);

        PageResult<BaseAutenticacao> result = adapter.search(q);

        assertNotNull(result);
        assertEquals(2, result.content().size());
        assertSame(d1, result.content().get(0));
        assertSame(d2, result.content().get(1));

        assertEquals(2L, result.totalElements());
        assertEquals(1, result.totalPages());
        assertEquals(0, result.page());
        assertEquals(20, result.size());
        assertTrue(result.first());
        assertTrue(result.last());

        Pageable used = pageableCaptor.getValue();
        assertEquals(0, used.getPageNumber());
        assertEquals(20, used.getPageSize());

        Sort.Order order = used.getSort().getOrderFor("nuBaseAutenticacao");
        assertNotNull(order);
        assertEquals(Sort.Direction.ASC, order.getDirection());

        assertNotNull(specCaptor.getValue());

        verify(repositorio).findAll(any(Specification.class), any(Pageable.class));
        verify(mapper).toDomain(e1);
        verify(mapper).toDomain(e2);
        verifyNoMoreInteractions(repositorio, mapper);
    }

    @Test
    void search_deveUsarSortByEDirectionQuandoInformados() {
        BaseAutenticacaoSearchQuery q = mock(BaseAutenticacaoSearchQuery.class);

        when(q.page()).thenReturn(2);
        when(q.size()).thenReturn(5);
        when(q.sortBy()).thenReturn(STR_NOBASEAUTENTICACAO);
        when(q.direction()).thenReturn("DESC");

        when(q.nuBaseAutenticacao()).thenReturn(null);
        when(q.noBaseAutenticacao()).thenReturn(null);
        when(q.icAtivo()).thenReturn(SimNao.S);

        Page<BaseAutenticacaoEntity> page = new PageImpl<>(
                List.of(),
                PageRequest.of(2, 5, Sort.by(Sort.Direction.DESC, STR_NOBASEAUTENTICACAO)),
                0
        );

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        when(repositorio.findAll(any(Specification.class), pageableCaptor.capture())).thenReturn(page);

        PageResult<BaseAutenticacao> result = adapter.search(q);

        assertNotNull(result);
        assertEquals(0, result.content().size());
        assertEquals(2, result.page());
        assertEquals(5, result.size());

        Pageable used = pageableCaptor.getValue();
        assertEquals(2, used.getPageNumber());
        assertEquals(5, used.getPageSize());

        Sort.Order order = used.getSort().getOrderFor(STR_NOBASEAUTENTICACAO);
        assertNotNull(order);
        assertEquals(Sort.Direction.DESC, order.getDirection());

        verify(repositorio).findAll(any(Specification.class), any(Pageable.class));
        verifyNoInteractions(mapper);
        verifyNoMoreInteractions(repositorio);
    }
}