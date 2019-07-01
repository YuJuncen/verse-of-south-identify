package edu.csust.demo.identify.domain.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class EmailAddressSerializer extends StdSerializer<EmailAddress> {
    EmailAddressSerializer() {
        this(null);
    }

    protected EmailAddressSerializer(Class<EmailAddress> t) {
        super(t);
    }

    @Override
    public void serialize(EmailAddress value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(String.format("%s@%s", value.getEmailUsername(), value.getEmailHostname()));
    }
}
