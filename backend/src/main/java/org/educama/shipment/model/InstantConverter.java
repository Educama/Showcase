package org.educama.shipment.model;

import javax.persistence.AttributeConverter;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Converter to convert Instant into Timestamp for persistence.
 */
public class InstantConverter implements AttributeConverter<Instant, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(Instant instant) {
        return Timestamp.from(instant);
    }

    @Override
    public Instant convertToEntityAttribute(Timestamp timestamp) {
        return timestamp.toInstant();
    }
}
