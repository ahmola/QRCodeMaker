package dev.practice.QRCodeGenerator.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import dev.practice.QRCodeGenerator.model.CustomUser;

import java.io.IOException;

public class CustomUserEntitySerializer extends JsonSerializer<CustomUser> {
    @Override
    public void serialize(CustomUser user, JsonGenerator generator, SerializerProvider serializers)
            throws IOException {
        generator.writeStartObject();

        generator.writeNumberField("id", user.getId());

        generator.writeStringField("password", user.getPassword());

        generator.writeStringField("username", user.getUsername());

        generator.writeStringField("role", user.getRole().toString());

        generator.writeFieldName("authorities");
        generator.writeObject(user.getAuthorities());

        generator.writeEndObject();
    }
}
