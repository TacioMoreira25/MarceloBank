package com.MarceloBank.MarceloBank;

import com.MarceloBank.MarceloBank.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarceloBankApplication implements CommandLineRunner {

    private final Principal principal;

    public MarceloBankApplication(Principal principal) {
        this.principal = principal;
    }

    public static void main(String[] args) {
        SpringApplication.run(MarceloBankApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        principal.exibeMenu();
    }
}
