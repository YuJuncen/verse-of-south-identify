package edu.csust.demo.identify.domain.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.regex.Pattern;

public class EmailAddressDeserializer extends StdDeserializer<EmailAddress> {
    EmailAddressDeserializer() {
        this(null);
    }

    protected EmailAddressDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public EmailAddress deserialize(JsonParser p, DeserializationContext context) throws IOException, JsonProcessingException {
        var email = p.getText().toLowerCase();
        var emailRegex = Pattern.compile(
                "(?<user>[a-z0-9!#$%&'*+/=?^_`{|}~-]+" +
                        "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
                        "@(?<host>(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])"
        );
        var matcher = emailRegex.matcher(email);
        if (matcher.matches()) {
            return new EmailAddress(matcher.group("host"), matcher.group("user"));
        }
        throw new IllegalArgumentException("Can't deserialize incoming email address string.");
    }
}
