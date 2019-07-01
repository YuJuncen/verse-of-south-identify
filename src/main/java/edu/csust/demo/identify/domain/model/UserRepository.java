package edu.csust.demo.identify.domain.model;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


@RepositoryRestResource(excerptProjection = UserExcerpt.class)
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    Iterable<User> findAllByUid(@Param("name") String name);
    Optional<User> findFirstByUid(@Param("uid") String uid);
}
