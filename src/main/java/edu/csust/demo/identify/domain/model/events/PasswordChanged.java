package edu.csust.demo.identify.domain.model.events;
import lombok.*;
import lombok.experimental.Wither;

@EqualsAndHashCode(callSuper = true)
@ToString
public class PasswordChanged extends UserDomainEvent {
    @Getter String uid;

    public PasswordChanged(Object source, String uid) {
        super(source);
        this.uid = uid;
    }
}
