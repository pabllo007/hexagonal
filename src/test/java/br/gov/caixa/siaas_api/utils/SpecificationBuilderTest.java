package br.gov.caixa.siaas_api.utils;

import br.gov.caixa.siaas_api.adapters.out.persistence.jpa.utils.SpecificationBuilder;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpecificationBuilderTest {

    static class Dummy {}

    @Test
    void build_deveExecutarLambdaWithEqual_eWithLike() {
        @SuppressWarnings("unchecked")
        Root<Dummy> root = (Root<Dummy>) mock(Root.class);
        @SuppressWarnings("unchecked")
        CriteriaQuery<Object> cq = (CriteriaQuery<Object>) mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        @SuppressWarnings("unchecked")
        Path<Object> p1 = (Path<Object>) mock(Path.class);
        @SuppressWarnings("unchecked")
        Path<Object> p2 = (Path<Object>) mock(Path.class);

        when(root.get("campo1")).thenReturn(p1);
        when(root.get("campo2")).thenReturn(p2);

        Predicate predEq = mock(Predicate.class);
        Predicate predLike = mock(Predicate.class);
        Predicate predAnd = mock(Predicate.class);

        when(cb.equal(p1, 10L)).thenReturn(predEq);

        @SuppressWarnings("unchecked")
        Expression<String> lowerExpr = (Expression<String>) mock(Expression.class);

        when(cb.lower((Expression<String>) (Expression<?>) p2)).thenReturn(lowerExpr);
        when(cb.like(lowerExpr, "%abc%")).thenReturn(predLike);

        when(cb.and(any(Predicate.class), any(Predicate.class))).thenReturn(predAnd);

        Specification<Dummy> spec = new SpecificationBuilder<Dummy>()
                .withEqual("campo1", 10L)
                .withLike("campo2", "abc")
                .build();

        Predicate result = spec.toPredicate(root, cq, cb);

        assertNotNull(result);

        verify(cb).equal(p1, 10L);
        verify(cb).lower(any(Expression.class));
        verify(cb).like(eq(lowerExpr), eq("%abc%"));
        verify(cb, atLeastOnce()).and(any(Predicate.class), any(Predicate.class));
    }

    @Test
    void withLike_deveIgnorarQuandoValorNullOuVazio() {
        Specification<Dummy> spec = new SpecificationBuilder<Dummy>()
                .withLike("campo", null)
                .withLike("campo", "")
                .build();

        Root<Dummy> root = mock(Root.class);
        CriteriaQuery<?> cq = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        assertNull(spec.toPredicate(root, cq, cb));
    }

    @Test
    void withEqual_deveIgnorarQuandoValorNull() {
        Specification<Dummy> spec = new SpecificationBuilder<Dummy>()
                .withEqual("campo", null)
                .build();

        Root<Dummy> root = mock(Root.class);
        CriteriaQuery<?> cq = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        assertNull(spec.toPredicate(root, cq, cb));
    }
}