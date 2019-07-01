package edu.csust.demo.identify.domain.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Data
@Table(
        uniqueConstraints =
                @UniqueConstraint(name = "no_replicate_role_name", columnNames = {"roleName"})

)
public class Role {
    @Id @GeneratedValue private UUID id;
    @NotNull private String roleName;
    private String description;
}
