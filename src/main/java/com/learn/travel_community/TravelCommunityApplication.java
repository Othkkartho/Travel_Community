package com.learn.travel_community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TravelCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelCommunityApplication.class, args);
    }

}
