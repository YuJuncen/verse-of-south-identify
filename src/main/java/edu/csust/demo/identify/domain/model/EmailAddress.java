package edu.csust.demo.identify.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import javax.persistence.Embeddable;

@EqualsAndHashCode
@AllArgsConstructor
@Embeddable
@Getter
@Wither
@NoArgsConstructor
public class EmailAddress {
    private String emailHostname;
    private String emailUsername;

    @JsonValue
    public String toJson() {
        return String.format("%s@%s", emailUsername, emailHostname);
    }

    @Override
    public String toString() {
        return "EmailAddress{" +
                "host='" + emailHostname + '\'' +
                ", user='" + emailUsername + '\'' +
                '}';
    }
}
