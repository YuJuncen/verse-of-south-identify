package edu.csust.demo.identify.domain.service;

import edu.csust.demo.identify.domain.model.Role;
import edu.csust.demo.identify.domain.model.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;

import java.util.UUID;

@Slf4j
public class InitUser {
    private static final String ADMIN_ROLE_NAME = "wheel";
    private static final String ADMIN_USERNAME = "Admin";
    @Value("server.port") private String port;

    public ApplicationRunner runner(RoleRepository roleRepository, UserService service) {
        return (args -> {
            try {
                var role = new Role();
                role.setRoleName(ADMIN_ROLE_NAME);
                roleRepository.save(role);
                log.info("{} role inserted.", ADMIN_ROLE_NAME);
            } catch (Exception e) {
                log.warn("Inserting the {} role failed, because: {}.", ADMIN_ROLE_NAME, e.getMessage());
            }

            try {
                var initialPassword = UUID.randomUUID().toString().substring(0, 8);
                log.info("CREATING DEFAULT ADMIN WITH PASSWORD: {}", initialPassword);
                service.registerUser(ADMIN_USERNAME, initialPassword);
            } catch (IllegalArgumentException e) {
                log.warn("Inserting the admin failed, because: {}.", e.getMessage());
            }
        });
    }
}
