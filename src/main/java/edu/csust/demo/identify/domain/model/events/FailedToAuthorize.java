package edu.csust.demo.identify.domain.model.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
public class FailedToAuthorize extends UserDomainEvent {
    @Getter
    private final String username;

    public FailedToAuthorize(Object source, String username) {
        super(source);
        this.username = username;
    }
}
