package edu.csust.demo.identify.domain.model.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@EqualsAndHashCode(callSuper = true)
@ToString
public class UserAuthorized extends UserDomainEvent {
    public UserAuthorized(Object source, String username) {
        super(source);
        this.username = username;
    }

    @Getter
    private final String username;
}
