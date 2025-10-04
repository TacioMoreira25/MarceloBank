package com.MarceloBank.MarceloBank.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MenuPrincipal {

    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    private ClienteMenu clienteMenu;

    @Autowired
    private ContaMenu contaMenu;

    @Autowired
    private TransacaoMenu transacaoMenu;

    @Autowired
    private EmprestimoMenu emprestimoMenu;

    public void exibir() {
        exibirBanner();

        int opcao = -1;

        while (opcao != 0) {
            exibirOpcoes();
            opcao = lerOpcao();

            if (opcao == 0) {
                encerrar();
            } else {
                processarOpcao(opcao);
            }
        }

        scanner.close();
    }

    private void exibirBanner() {
        limparTela();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║         MARCELOBANK SYSTEM             ║");
        System.out.println("║    Sistema Bancario - Versao 1.0       ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    private void exibirOpcoes() {
        // limparTela();
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.println("│          MENU PRINCIPAL             │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  1 │ Clientes                       │");
        System.out.println("│  2 │ Contas                         │");
        System.out.println("│  3 │ Transacoes                     │");
        System.out.println("│  4 │ Emprestimos                    │");
        System.out.println("│  0 │ Sair                           │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("\n➤ Escolha uma opcao: ");
    }

    private int lerOpcao() {
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();
            return opcao;
        } catch (Exception e) {
            scanner.nextLine();
            System.err.println("\n✗ Erro: Digite um numero valido");
            return -1;
        }
    }

    private void processarOpcao(int opcao) {
        try {
            switch (opcao) {
                case 1 -> clienteMenu.exibir();
                case 2 -> contaMenu.exibir();
                case 3 -> transacaoMenu.exibir();
                case 4 -> emprestimoMenu.exibir();
                default -> {
                    System.err.println("\n✗ Opcao invalida!");
                }
            }
        } catch (Exception e) {
            System.err.println("\n✗ Erro: " + e.getMessage());
        }
    }

    private void encerrar() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║                                        ║");
        System.out.println("║   Obrigado por usar o MarceloBank!     ║");
        System.out.println("║         Ate a proxima!                 ║");
        System.out.println("║                                        ║");
        System.out.println("╚════════════════════════════════════════╝\n");
    }

    private void limparTela() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().
                        start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}
