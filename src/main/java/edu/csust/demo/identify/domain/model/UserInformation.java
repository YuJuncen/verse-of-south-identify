package edu.csust.demo.identify.domain.model;

import lombok.Value;
import lombok.experimental.Wither;
import org.springframework.hateoas.Link;

@Value
@Wither
public class UserInformation {
    private Link _links;
}
