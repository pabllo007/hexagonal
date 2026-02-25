package br.gov.caixa.siaas_api.domain.shared.enums;

import java.util.Locale;
import java.util.Objects;

/**
 * Conceito de negócio: Sim/Não.
 * Puro (sem Spring/JPA/Web).
 */
public enum SimNao {
    S, N;

    public static SimNao of(Boolean value) {
        return Boolean.TRUE.equals(value) ? S : N;
    }

    public static SimNao fromDatabase(String value) {
        if (value == null) return null;
        String v = value.trim().toUpperCase(Locale.ROOT);
        if (Objects.equals(v, "S")) return S;
        if (Objects.equals(v, "N")) return N;
        throw new IllegalArgumentException("Valor inválido para SimNao: '" + value + "'. Esperado 'S' ou 'N'.");
    }

    public String toDatabase() {
        return this.name();
    }

    public boolean isSim() {
        return this == S;
    }

    public boolean isNao() {
        return this == N;
    }
}