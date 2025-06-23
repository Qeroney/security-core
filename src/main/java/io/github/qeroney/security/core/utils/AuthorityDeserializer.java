package io.github.qeroney.security.core.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;

public class AuthorityDeserializer extends StdDeserializer<GrantedAuthority> {

    public AuthorityDeserializer() {
        super(GrantedAuthority.class);
    }

    @Override
    public GrantedAuthority deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String auth = node.path("authority").asText(null);
        return new SimpleGrantedAuthority(auth);
    }
}
