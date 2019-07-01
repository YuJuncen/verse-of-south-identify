package edu.csust.demo.identify.domain.model.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
public class UserCreated extends UserDomainEvent {
    public UserCreated(Object source, String username) {
        super(source);
        this.username = username;
    }

    @Getter
    private final String username;
}
