package edu.csust.demo.identify.domain.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.csust.demo.identify.domain.model.UserRepository;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.Wither;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/resources/users")
@Controller
@Slf4j
public class UserApplicationService {
    private final UserService service;
    private final UserRepository userRepository;


    @ToString
    @Getter
    @EqualsAndHashCode
    @Wither
    static class NewUserInformation {
        private final String username;
        private final String password;

        @JsonCreator
        NewUserInformation(
                @JsonProperty(value = "uid", required = true) String username,
                @JsonProperty(value = "password", required = true) String password) {
            this.username = username;
            this.password = password;
        }
    }

    @Value
    @Wither
    static class ChangePasswordInformation
    {
        private final String oldPassword;
        private final String newPassword;
        private final String uid;

        @JsonCreator
        public ChangePasswordInformation(
                @JsonProperty("oldPassword") String oldPassword,
                @JsonProperty("newPassword") String newPassword,
                @JsonProperty("uid") String uid) {
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
            this.uid = uid;
        }
    }

    @Autowired
    public UserApplicationService(UserService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    ResponseEntity<String> register(
            RequestEntity<NewUserInformation> req) {
        try {
            var newUser = req.getBody();
            service.registerUser(newUser.getUsername(), newUser.getPassword());
            return ResponseEntity.noContent().build();
        } catch (NullPointerException | IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/authorize")
    ResponseEntity<Object> authorize(
            @Param("uid") String uid,
            @Param("password") String password
    ) {
        try {
            return ResponseEntity
                    .ok(service.authorizeUser(uid, password));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .header("Content-Type", "text/plain;charset=utf8")
                    .body(e.getMessage());
        }
    }

    @PostMapping("/addRole")
    ResponseEntity<String> addRole(
            @Param("uid") String uid,
            @Param("roleName") String roleName
    ) {
        try {
            service.addRole(uid, roleName);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .header("Content-Type", "text/plain;charset=utf8")
                    .body(e.getMessage());
        }
    }

    @GetMapping("/hasRole")
    ResponseEntity<Map<String, Object>> hasRole(
            @Param("uid") String uid,
            @Param("roleName") String roleName
    ) {
        var user = userRepository.findFirstByUid(uid);
        if (user.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("user", uid,
                            "role", roleName,
                            "result", "no such user"));
        }
        return ResponseEntity.ok(Map.of(
                "user", uid,
                "role", roleName,
                "result", user.get().hasRole(roleName)
        ));
    }

    @PatchMapping("/password")
    ResponseEntity<Map<String, Object>> changePassword(
            RequestEntity<ChangePasswordInformation> cpw
    ) {
        if (!cpw.hasBody()) {
            return ResponseEntity.notFound().build();
        }
        var info = cpw.getBody();
        try {
            assert info != null;
            service.changePassword(info.uid, info.oldPassword, info.newPassword);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    Map.of("message", e.getMessage())
            );
        }
    }
}
