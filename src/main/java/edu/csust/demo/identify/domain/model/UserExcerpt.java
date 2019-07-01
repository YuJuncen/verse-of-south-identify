package edu.csust.demo.identify.domain.model;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "UserExcerpt", types = User.class)
public interface UserExcerpt {
    String getUid();
    Personal getPersonal();
    List<Role> getRoles();
}
