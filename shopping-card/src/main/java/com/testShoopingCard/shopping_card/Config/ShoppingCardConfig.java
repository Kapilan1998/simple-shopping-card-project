package com.testShoopingCard.shopping_card.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShoppingCardConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
