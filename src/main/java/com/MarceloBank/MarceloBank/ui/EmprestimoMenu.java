package com.MarceloBank.MarceloBank.ui;

import com.MarceloBank.MarceloBank.model.Emprestimo;
import com.MarceloBank.MarceloBank.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class EmprestimoMenu
{
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
        System.out.println("\n=== EMPRESTIMOS ===");
        System.out.println("1 - Solicitar Emprestimo");
        System.out.println("2 - Consultar Saldo Devedor");
        System.out.println("0 - Voltar");
        System.out.print("\nOpcao: ");
    }

    private int lerOpcao() {
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();
            return opcao;
        } catch (Exception e) {
            scanner.nextLine();
            System.err.println("\nErro: Digite um numero valido");
            return -1;
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> solicitar();
            case 2 -> consultarSaldoDevedor();
            case 0 -> {}
            default -> System.err.println("\nOpcao invalida!");
        }
    }

    private void solicitar() {
        System.out.println("\n=== SOLICITAR EMPRESTIMO ===");

        try {
            System.out.print("ID do Cliente: ");
            Integer clienteId = scanner.nextInt();

            System.out.print("Valor: R$ ");
            BigDecimal valor = scanner.nextBigDecimal();

            System.out.print("Prazo (meses): ");
            Integer prazo = scanner.nextInt();

            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                System.err.println("\nErro: Valor deve ser maior que zero");
                return;
            }

            if (prazo <= 0) {
                System.err.println("\nErro: Prazo deve ser maior que zero");
                return;
            }

            Emprestimo emprestimo = emprestimoService.solicitarEmprestimo(
                    clienteId, valor, prazo
            );

            System.out.println("\nEmprestimo solicitado!");
            System.out.println("ID: " + emprestimo.getIdEmprestimo());
            System.out.printf("Valor: R$ %.2f\n", emprestimo.getValorSolicitado());
            System.out.printf("Prazo: %d meses\n", emprestimo.getPrazoMeses());
            System.out.println("Status: " + emprestimo.getStatus());

        } catch (Exception e) {
            System.err.println("\nErro: " + e.getMessage());
        }
    }

    private void consultarSaldoDevedor() {
        System.out.println("\n=== SALDO DEVEDOR ===");

        try {
            System.out.print("ID do Cliente: ");
            Integer clienteId = scanner.nextInt();

            BigDecimal saldo = emprestimoService.getSaldoDevedorTotal(clienteId);

            System.out.printf("\nCliente: %d\n", clienteId);
            System.out.printf("Saldo Devedor: R$ %.2f\n", saldo);

            if (saldo.compareTo(BigDecimal.ZERO) == 0) {
                System.out.println("Sem dividas");
            }

        } catch (Exception e) {
            System.err.println("\nErro: " + e.getMessage());
        }
    }
}