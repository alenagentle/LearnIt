package ru.irlix.learnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableFeignClients
public class LearnItApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnItApplication.class, args);
    }
}
