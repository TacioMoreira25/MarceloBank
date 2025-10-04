package com.MarceloBank.MarceloBank.ui;

import com.MarceloBank.MarceloBank.model.Emprestimo;
import com.MarceloBank.MarceloBank.model.Transacao;
import com.MarceloBank.MarceloBank.repository.EmprestimoRepository;
import com.MarceloBank.MarceloBank.repository.TransacaoRepository;
import com.MarceloBank.MarceloBank.service.ContaService;
import com.MarceloBank.MarceloBank.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

@Component
public class TransacaoMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Autowired
    private ContaService contaService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private EmprestimoService emprestimoService ;

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
        System.out.println("│           TRANSACOES                │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  1 │ Deposito                       │");
        System.out.println("│  2 │ Saque                          │");
        System.out.println("│  3 │ Transferencia                  │");
        System.out.println("│  4 │ Extrato                        │");
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
            case 1 -> realizarDeposito();
            case 2 -> realizarSaque();
            case 3 -> realizarTransferencia();
            case 4 -> gerarExtrato();
            case 0 -> {}
            default -> System.err.println("\n✗ Opcao invalida!");
        }
    }

    private void realizarDeposito() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║            DEPOSITO                    ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            System.out.print("➤ Conta: ");
            Integer numeroConta = scanner.nextInt();

            System.out.print("➤ Valor: R$ ");
            BigDecimal valor = scanner.nextBigDecimal();

            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                System.err.println("\n✗ Erro: Valor deve ser maior que zero");
                aguardarEnter();
                return;
            }

            contaService.depositar(numeroConta, valor);
            System.out.printf("\n✓ Deposito realizado! R$ %.2f\n", valor);
            aguardarEnter();

        } catch (Exception e) {
            System.err.println("\n✗ Erro: " + e.getMessage());
            aguardarEnter();
        }
    }

    private void realizarSaque() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║             SAQUE                      ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            System.out.print("➤ Conta: ");
            Integer numeroConta = scanner.nextInt();

            System.out.print("➤ Valor: R$ ");
            BigDecimal valor = scanner.nextBigDecimal();

            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                System.err.println("\n✗ Erro: Valor deve ser maior que zero");
                aguardarEnter();
                return;
            }

            contaService.sacar(numeroConta, valor);
            System.out.printf("\n✓ Saque realizado! R$ %.2f\n", valor);
            aguardarEnter();

        } catch (Exception e) {
            System.err.println("\n✗ Erro: " + e.getMessage());
            aguardarEnter();
        }
    }

    private void realizarTransferencia() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         TRANSFERENCIA                  ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            System.out.print("➤ Conta Origem: ");
            Integer contaOrigem = scanner.nextInt();

            System.out.print("➤ Conta Destino: ");
            Integer contaDestino = scanner.nextInt();

            System.out.print("➤ Valor: R$ ");
            BigDecimal valor = scanner.nextBigDecimal();

            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                System.err.println("\n✗ Erro: Valor deve ser maior que zero");
                aguardarEnter();
                return;
            }

            if (contaOrigem.equals(contaDestino)) {
                System.err.println("\n✗ Erro: Contas nao podem ser iguais");
                aguardarEnter();
                return;
            }

            contaService.transferir(contaOrigem, contaDestino, valor);
            System.out.printf("\n✓ Transferencia realizada! R$ %.2f\n", valor);
            aguardarEnter();

        } catch (Exception e) {
            System.err.println("\n✗ Erro: " + e.getMessage());
            aguardarEnter();
        }
    }

    private void gerarExtrato() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║            EXTRATO                     ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            System.out.print("➤ Conta: ");
            Integer numeroConta = scanner.nextInt();

            List<Transacao> transacoes = transacaoRepository.
                    findTransacoesByConta(numeroConta);

            if (transacoes.isEmpty()) {
                System.out.println("\n➤ Nenhuma transacao encontrada");
                aguardarEnter();
                return;
            }

            System.out.println("\n┌──────────────────────────────────────────────────────────────┐");
            System.out.println("│ Data/Hora       │ Tipo           │ Valor       │ Status      │");
            System.out.println("├──────────────────────────────────────────────────────────────┤");

            for (Transacao t : transacoes) {
                System.out.printf("│ %-15s │ %-14s │ R$ %8.2f │ %-11s │\n",
                        dateFormat.format(t.getDataTransacao()),
                        t.getTipoTransacao(),
                        t.getValor(),
                        t.getStatus());
            }

            System.out.println("└──────────────────────────────────────────────────────────────┘");
            System.out.println("\n➤ Total: " + transacoes.size() + " transacao(oes)");
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