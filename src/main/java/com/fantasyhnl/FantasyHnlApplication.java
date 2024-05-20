package com.fantasyhnl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class FantasyHnlApplication {

    public static void main(String[] args) {
        SpringApplication.run(FantasyHnlApplication.class, args);
    }

}
