package edu.csust.demo.identify.domain.model.events;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
public class RoleAdded extends UserDomainEvent {
    @Getter private String uid;
    @Getter private String roleName;

    public RoleAdded(Object source, String uid, String roleName) {
        super(source);
        this.uid = uid;
        this.roleName = roleName;
    }
}
