package edu.csust.demo.identify.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.csust.demo.identify.domain.service.Utils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users",
uniqueConstraints = {
        @UniqueConstraint(name = "uid_unique", columnNames = "uid")
},
indexes = {
        @Index(name = "uid_idx", columnList = "uid")
})
@Data
public class User extends AbstractAggregateRoot<User> {
    private @GeneratedValue @Id Integer id;
    private String uid;
    private @Embedded Personal personal = new Personal(null, new byte[0], null);
    private @JsonIgnore byte[] password;
    private @ManyToMany List<Role> roles;

    static private final String EMPTY_BASE64 = "Cg==";


    public boolean hasRole(String roleName) {
        return roles.stream()
                .anyMatch(r -> r.getRoleName().equals(roleName));
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }
    public byte[] encryptPassword(String password) {
        final var md5 = Utils.md5Instance();
        return md5.digest(uid.concat(password).getBytes());
    }
    public boolean isValidPassword(String password) {
        return Arrays.equals(encryptPassword(password), this.password);
    }
    public void registerNewPassword(String newPassword) {
        this.password = encryptPassword(newPassword);
    }
    public User() {  }
}
