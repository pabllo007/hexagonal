package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.converter;

import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimNaoAttributeConverterTest {

    private final SimNaoAttributeConverter converter = new SimNaoAttributeConverter();

    @Test
    void convertToDatabaseColumn_deveRetornarNull_quandoAttributeNull() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    void convertToDatabaseColumn_deveRetornarSEN_quandoAttributeNaoNull() {
        assertEquals("S", converter.convertToDatabaseColumn(SimNao.S));
        assertEquals("N", converter.convertToDatabaseColumn(SimNao.N));
    }

    @Test
    void convertToEntityAttribute_deveRetornarNull_quandoDbDataNull() {
        assertNull(converter.convertToEntityAttribute(null));
    }

    @Test
    void convertToEntityAttribute_deveConverterSEN_quandoDbDataValido() {
        assertEquals(SimNao.S, converter.convertToEntityAttribute("S"));
        assertEquals(SimNao.S, converter.convertToEntityAttribute(" s "));
        assertEquals(SimNao.N, converter.convertToEntityAttribute("N"));
        assertEquals(SimNao.N, converter.convertToEntityAttribute(" n "));
    }

    @Test
    void convertToEntityAttribute_deveLancarException_quandoDbDataInvalido() {
        assertThrows(IllegalArgumentException.class, () -> converter.convertToEntityAttribute("X"));
    }
}