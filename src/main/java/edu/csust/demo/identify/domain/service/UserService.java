package edu.csust.demo.identify.domain.service;

// .....

import edu.csust.demo.identify.domain.model.*;
import edu.csust.demo.identify.domain.model.events.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final RepositoryEntityLinks links;

    @Bean
    public String defaultAvatar() {
        return "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAA/1BMVEUAAAC2zNS1zNS1y9O5vNi2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS2zNS3zdWzytK0ytK0y9OUtL9FfI9De46Rsr2husGRrLKowMeyydIJU2s+d4uwyNCZs7mKpquyydE/eYw6dYmwx9C3zNSatLuJpaqNr7s4dIiKrbm0y9Kiu8KQq7Gmv8avx9CzydH////amY9IAAAAMnRSTlMAAAAAAAU/ndz23qFEBhOH6u2OF6b+rgOQ6Embp9rk9Pv85p+r6+9Olwe2G5HxmKr9CAiZpSQAAAABYktHRFTkA4ilAAAACXBIWXMAABcSAAAXEgFnn9JSAAAAB3RJTUUH4wQaDQo71RxASgAAAMlJREFUGNNNj2tTgmAQhc9qohh4gcDEUrPCzDRbpKJSvEVaZqX//7/4vtYk58vO88yc3VkAlFLSGVXNHmo6AUhQLl/gXYqGSQTKHVn8F8s2CaX8P4sc61DKMeaig4oYXr/vsX8vzQlOBT88Bk/+88tAiCpqzMMgDEdjnkx95roUs9cwjN7mi3chzpARlY9o+bn6+v4RlQbO5dL12uONL5deQLuMn3WbSBlxcdUCmfaer9uEJJk37i92um2S/1LLuW30emW7eUcH2AKtBSk4jvvrngAAACV0RVh0ZGF0ZTpjcmVhdGUAMjAxOS0wNC0yNlQxMzoxMDo1OSswMjowMCztOIoAAAAldEVYdGRhdGU6bW9kaWZ5ADIwMTktMDQtMjZUMTM6MTA6NTkrMDI6MDBdsIA2AAAAV3pUWHRSYXcgcHJvZmlsZSB0eXBlIGlwdGMAAHic4/IMCHFWKCjKT8vMSeVSAAMjCy5jCxMjE0uTFAMTIESANMNkAyOzVCDL2NTIxMzEHMQHy4BIoEouAOoXEXTyQjWVAAAAAElFTkSuQmCC";
    }

    @Autowired
    public UserService(UserRepository repository, RoleRepository roleRepository, ApplicationEventPublisher eventPublisher, RepositoryEntityLinks links) {
        this.userRepository = repository;
        this.roleRepository = roleRepository;
        this.eventPublisher = eventPublisher;
        this.links = links;
    }

    public UserInformation authorizeUser(String username, String password) {
        final Iterator<User> iterator = userRepository.findAllByUid(username).iterator();
        final Optional<User> user = iterator.hasNext()  ?
                Optional.of(iterator.next()) :
                Optional.empty();
        return user.flatMap(u -> {
            if (u.isValidPassword(password)){
                eventPublisher.publishEvent(new UserAuthorized(this, username));
                return Optional.of(new UserInformation(
                        links.linkToSingleResource(User.class, u.getId())));
            } else {
                eventPublisher.publishEvent(new FailedToAuthorize(this, username));
                return Optional.empty();
            }
        }).orElseThrow(() -> new IllegalArgumentException("No such user or wrong password!"));
    }

    public void registerUser(String username, String initialPassword) {
        if (userRepository.findFirstByUid(username).isPresent()) {
            throw new IllegalArgumentException("This username has been used.");
        }

        var user = new User();
        user.setUid(username);
        user.setPersonal(new Personal(null, Base64Utils.decode(defaultAvatar().getBytes()), null));
        user.setPassword(user.encryptPassword(initialPassword));
        userRepository.save(user);
        eventPublisher.publishEvent(new UserCreated(this, username));
    }

    public void addRole(String uid, String roleName) {
        var user = StreamSupport.stream(userRepository.findAllByUid(uid).spliterator(), false)
                .findAny();
        if (user.isEmpty()) throw new IllegalArgumentException("No such user.");
        var role = roleRepository.findFirstByRoleName(roleName);
        if (role == null) throw new IllegalArgumentException("No such role.");
        user.get().addRole(role);
        eventPublisher.publishEvent(new RoleAdded(this, uid, roleName));
    }

    public void changePassword(String uid, String oldPassword, String newPassword) {
        var maybeUser = userRepository.findFirstByUid(uid);
        if (maybeUser.isEmpty()){
            throw new IllegalArgumentException("No such user: " + uid);
        }
        var user = maybeUser.get();
        if (!user.isValidPassword(oldPassword)) {
            throw new IllegalArgumentException("Wrong password.");
        }
        user.registerNewPassword(newPassword);
        userRepository.save(user);
        eventPublisher.publishEvent(new PasswordChanged(this, user.getUid()));
    }
}
