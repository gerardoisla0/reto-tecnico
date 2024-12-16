package com.sermaluc.retotecnico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class MainApplication {
    static {
        org.apache.xml.security.Init.init();
    }
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
