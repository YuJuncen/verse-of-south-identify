package edu.csust.demo.identify.domain.model;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "NoPersonal", types = User.class)
public interface NoPersonal {
    String getUid();
    List<Role> getRoles();
}
