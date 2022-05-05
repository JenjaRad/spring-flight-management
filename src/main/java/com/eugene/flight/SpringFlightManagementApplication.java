package com.eugene.flight;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class SpringFlightManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringFlightManagementApplication.class, args);
    }
}
