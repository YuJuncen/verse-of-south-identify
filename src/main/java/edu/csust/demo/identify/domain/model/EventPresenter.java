package edu.csust.demo.identify.domain.model;

import edu.csust.demo.identify.domain.model.events.UserDomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class EventPresenter implements ApplicationListener<UserDomainEvent> {
    private final EventRepository repository;

    @Autowired
    public EventPresenter(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(UserDomainEvent event) {
        var log = new EventLog(
                event.getClass().getSimpleName(),
                event.occursOn(),
                event.toString()
        );
        repository.save(log);
    }
}
