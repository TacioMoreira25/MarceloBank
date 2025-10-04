package com.MarceloBank.MarceloBank;

import com.MarceloBank.MarceloBank.ui.MenuPrincipal;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarceloBankApplication implements CommandLineRunner {

    private final MenuPrincipal menuPrincipal;

    public MarceloBankApplication(MenuPrincipal menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
    }

    public static void main(String[] args)
    {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        // Carrega as variáveis do .env como propriedades do sistema
        dotenv.entries().forEach(e ->
                System.setProperty(e.getKey(), e.getValue())
        );
        SpringApplication.run(MarceloBankApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        menuPrincipal.exibir();
    }
}