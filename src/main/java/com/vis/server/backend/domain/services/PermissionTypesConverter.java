package com.vis.server.backend.domain.services;

import com.vis.server.backend.domain.model.PermissionTypes;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PermissionTypesConverter implements AttributeConverter<PermissionTypes, String> {

    @Override
    public String convertToDatabaseColumn(PermissionTypes attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toString();
    }

    @Override
    public PermissionTypes convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(PermissionTypes.values())
            .filter(c -> c.toString().equals(dbData))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
