package com.sse.publisher.configs;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.sse.publisher.constants.HttpConstants;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {

    @Override
    public void serialize(
            final OffsetDateTime source, final JsonGenerator generator, final SerializerProvider provider)
            throws IOException {

        if (source != null) {
            final var formatter = DateTimeFormatter.ofPattern(HttpConstants.DATE_PATTERN);
            generator.writeString(formatter.format(source));
        } else {
            generator.writeNull();
        }
    }
}
