package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.utils;


import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {
    private final List<Specification<T>> specs = new ArrayList<>();

    public SpecificationBuilder<T> withLike(String field, String value) {
        if (value != null && !value.isBlank()) {
            specs.add((root, query, cb) -> cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%"));
        }
        return this;
    }

    public <V> SpecificationBuilder<T> withEqual(String field, V value) {
        if (value != null) {
            specs.add((root, query, cb) -> cb.equal(root.get(field), value));
        }
        return this;
    }

    public Specification<T> build() {
        return specs.stream().reduce(Specification.where(null), Specification::and);
    }
}

