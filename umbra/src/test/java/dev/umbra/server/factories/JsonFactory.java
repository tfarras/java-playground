package dev.umbra.server.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.umbra.server.user.dto.RegisterUserRequest;
import org.flywaydb.core.internal.util.ObjectMapperFactory;

public abstract class JsonFactory<ObjectType> {
    private final ObjectMapper mapper = new ObjectMapper();

    public abstract ObjectType buildObject();

    public String buildJson() {
        var data = buildObject();
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    };
}
