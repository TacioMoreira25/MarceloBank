package com.MarceloBank.MarceloBank;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarceloBankApplication
{
    public static void main(String[] args)
    {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        // Carrega as variáveis do .env como propriedades do sistema
        dotenv.entries().forEach(e ->
                System.setProperty(e.getKey(), e.getValue())
        );
        SpringApplication.run(MarceloBankApplication.class, args);
    }
}