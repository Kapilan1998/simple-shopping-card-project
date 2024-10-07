package com.testShoopingCard.shopping_card.Data;

import com.testShoopingCard.shopping_card.Entity.Role;
import com.testShoopingCard.shopping_card.Entity.User;
import com.testShoopingCard.shopping_card.Repository.RoleRepository;
import com.testShoopingCard.shopping_card.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("Admin", "Customer");
        createDefaultUserIfNotExists();
        createDefaultRoleIfNotExists(defaultRoles);
        createDefaultAdminIfNotExists();
    }

    // creating default users
    private void createDefaultUserIfNotExists() {
        Role userRole = roleRepository.findByName("Customer").get();
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "myuser" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("First");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("Test_user" + i));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            log.info("User created successfully");
        }
    }

    // create default admin
    private void createDefaultAdminIfNotExists() {
        Role adminRole = roleRepository.findByName("Admin").get();
        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "admin" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin-" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("Admin-0" + i));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            log.info("Admin created successfully");
        }
    }


    // creating default roles
    private void createDefaultRoleIfNotExists(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new)
                .forEach(roleRepository::save);
    }

}
