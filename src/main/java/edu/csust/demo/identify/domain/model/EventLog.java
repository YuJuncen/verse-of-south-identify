package edu.csust.demo.identify.domain.model;


import lombok.Value;
import lombok.experimental.Wither;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Wither
@Entity
class EventLog {
    @Id @GeneratedValue
    private UUID id = UUID.randomUUID();

    private final String eventName;
    private final LocalDateTime occursOn;
    private final String information;

    EventLog() {
        eventName = null;
        occursOn = null;
        information = null;
    }

    EventLog(String eventName, LocalDateTime occursOn, String information) {
        this.eventName = eventName;
        this.occursOn = occursOn;
        this.information = information;
    }
}
