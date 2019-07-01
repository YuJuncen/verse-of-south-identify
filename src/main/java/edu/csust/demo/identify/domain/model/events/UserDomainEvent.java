package edu.csust.demo.identify.domain.model.events;

import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

public abstract class UserDomainEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public UserDomainEvent(Object source) {
        super(source);
    }

    final private LocalDateTime occursOn = LocalDateTime.now();
    public LocalDateTime occursOn() {
        return occursOn;
    }
}
