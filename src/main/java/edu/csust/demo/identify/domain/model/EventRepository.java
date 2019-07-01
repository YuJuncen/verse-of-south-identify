package edu.csust.demo.identify.domain.model;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface EventRepository extends PagingAndSortingRepository<EventLog, UUID> { }
