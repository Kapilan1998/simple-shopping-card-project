package com.testShoopingCard.shopping_card.Data;

import com.testShoopingCard.shopping_card.Entity.User;
import com.testShoopingCard.shopping_card.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        for(int i =1 ;i <=5 ;i++){
            String defaultEmail = "myuser"+i+"@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("First");
            user.setLastName("User"+i);
            user.setEmail(defaultEmail);
            user.setPassword("Test_user"+i);
            userRepository.save(user);
            log.info("User created successfully");
        }
    }
}
