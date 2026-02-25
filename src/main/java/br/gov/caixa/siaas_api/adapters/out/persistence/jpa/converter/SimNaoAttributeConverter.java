package br.gov.caixa.siaas_api.adapters.out.persistence.jpa.converter;

import br.gov.caixa.siaas_api.domain.shared.enums.SimNao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class SimNaoAttributeConverter implements AttributeConverter<SimNao, String> {

    @Override
    public String convertToDatabaseColumn(SimNao attribute) {
        return attribute == null ? null : attribute.toDatabase();
    }

    @Override
    public SimNao convertToEntityAttribute(String dbData) {
        return SimNao.fromDatabase(dbData);
    }
}