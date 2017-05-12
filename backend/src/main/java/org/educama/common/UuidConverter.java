package org.educama.common;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.UUID;

/**
 * Converts between typesafe {@link UUID} (application) and {@link String} (database).
 */
@Converter
public class UuidConverter implements AttributeConverter<UUID, String> {

    @Override
    public String convertToDatabaseColumn(UUID uuid) {
        return uuid.toString();
    }

    @Override
    public UUID convertToEntityAttribute(String value) {
        return UUID.fromString(value);
    }

}
