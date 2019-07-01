package edu.csust.demo.identify.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Value;
import lombok.experimental.Wither;

import javax.persistence.Embeddable;

@Embeddable
@Value
@Wither
public class Personal {
    private String nickName;
    private byte[] avatar;

    @JsonDeserialize(using = EmailAddressDeserializer.class)
    private EmailAddress emailAddress;

    @JsonCreator
    public Personal(
            @JsonProperty("nickName") String nickName,
            @JsonProperty("avatar") byte[] avatar,
            @JsonProperty("emailAddress") EmailAddress address) {
        this.nickName = nickName;
        this.avatar = avatar;
        this.emailAddress = address;
    }

    protected Personal() {
        nickName = null;
        avatar = null;
        emailAddress = null;
    }
}
