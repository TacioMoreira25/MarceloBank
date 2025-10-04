package com.MarceloBank.MarceloBank.ui;

import com.MarceloBank.MarceloBank.model.Emprestimo;
import com.MarceloBank.MarceloBank.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class EmprestimoMenu {
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    private EmprestimoService emprestimoService;

    public void exibir() {
        int opcao = -1;

        while (opcao != 0) {
            exibirOpcoes();
            opcao = lerOpcao();
            processarOpcao(opcao);
        }
    }

    private void exibirOpcoes() {
        limparTela();
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.println("│          EMPRESTIMOS                │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  1 │ Solicitar Emprestimo           │");
        System.out.println("│  2 │ Consultar Saldo Devedor        │");
        System.out.println("│  0 │ Voltar                         │");
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
        switch (opcao) {
            case 1 -> solicitar();
            case 2 -> consultarSaldoDevedor();
            case 0 -> {}
            default -> System.err.println("\n✗ Opcao invalida!");
        }
    }

    private void solicitar() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║      SOLICITAR EMPRESTIMO              ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            System.out.print("➤ ID do Cliente: ");
            Integer clienteId = scanner.nextInt();

            System.out.print("➤ Valor: R$ ");
            BigDecimal valor = scanner.nextBigDecimal();

            System.out.print("➤ Prazo (meses): ");
            Integer prazo = scanner.nextInt();

            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                System.err.println("\n✗ Erro: Valor deve ser maior que zero");
                aguardarEnter();
                return;
            }

            if (prazo <= 0) {
                System.err.println("\n✗ Erro: Prazo deve ser maior que zero");
                aguardarEnter();
                return;
            }

            Emprestimo emprestimo = emprestimoService.solicitarEmprestimo(
                    clienteId, valor, prazo
            );

            System.out.println("\n✓ Emprestimo solicitado!");
            System.out.println("\n┌────────────────────────────────────────┐");
            System.out.printf("│ ID: %-34d │\n", emprestimo.getIdEmprestimo());
            System.out.printf("│ Valor: R$ %-27.2f │\n", emprestimo.getValorSolicitado());
            System.out.printf("│ Prazo: %-30d │\n", emprestimo.getPrazoMeses());
            System.out.printf("│ Status: %-30s │\n", emprestimo.getStatus());
            System.out.println("└────────────────────────────────────────┘");
            aguardarEnter();

        } catch (Exception e) {
            System.err.println("\n✗ Erro: " + e.getMessage());
            aguardarEnter();
        }
    }

    private void consultarSaldoDevedor() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║        SALDO DEVEDOR                   ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            System.out.print("➤ ID do Cliente: ");
            Integer clienteId = scanner.nextInt();

            BigDecimal saldo = emprestimoService.getSaldoDevedorTotal(clienteId);

            System.out.println("\n┌────────────────────────────────────────┐");
            System.out.printf("│ Cliente: %-29d │\n", clienteId);
            System.out.printf("│ Saldo Devedor: R$ %-19.2f │\n", saldo);
            System.out.println("└────────────────────────────────────────┘");

            if (saldo.compareTo(BigDecimal.ZERO) == 0) {
                System.out.println("\n✓ Sem dividas");
            }

            aguardarEnter();

        } catch (Exception e) {
            System.err.println("\n✗ Erro: " + e.getMessage());
            aguardarEnter();
        }
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

    private void aguardarEnter() {
        System.out.print("\n➤ Pressione ENTER para continuar...");
        scanner.nextLine();
    }
}