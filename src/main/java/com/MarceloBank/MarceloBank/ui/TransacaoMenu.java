package com.MarceloBank.MarceloBank.ui;

import com.MarceloBank.MarceloBank.model.Transacao;
import com.MarceloBank.MarceloBank.repository.TransacaoRepository;
import com.MarceloBank.MarceloBank.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

@Component
public class TransacaoMenu
{
    private final Scanner scanner = new Scanner(System.in);
    private final SimpleDateFormat dateFormat = new
            SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Autowired
    private ContaService contaService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    public void exibir() {
        int opcao = -1;

        while (opcao != 0) {
            exibirOpcoes();
            opcao = lerOpcao();
            processarOpcao(opcao);
        }
    }

    private void exibirOpcoes() {
        System.out.println("\n=== TRANSACOES ===");
        System.out.println("1 - Deposito");
        System.out.println("2 - Saque");
        System.out.println("3 - Transferencia");
        System.out.println("4 - Extrato");
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
            case 1 -> realizarDeposito();
            case 2 -> realizarSaque();
            case 3 -> realizarTransferencia();
            case 4 -> gerarExtrato();
            case 0 -> {}
            default -> System.err.println("\nOpcao invalida!");
        }
    }

    private void realizarDeposito() {
        System.out.println("\n=== DEPOSITO ===");

        try {
            System.out.print("Conta: ");
            Integer numeroConta = scanner.nextInt();

            System.out.print("Valor: R$ ");
            BigDecimal valor = scanner.nextBigDecimal();

            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                System.err.println("\nErro: Valor deve ser maior que zero");
                return;
            }

            contaService.depositar(numeroConta, valor);
            System.out.printf("\nDeposito realizado! R$ %.2f\n", valor);

        } catch (Exception e) {
            System.err.println("\nErro: " + e.getMessage());
        }
    }

    private void realizarSaque() {
        System.out.println("\n=== SAQUE ===");

        try {
            System.out.print("Conta: ");
            Integer numeroConta = scanner.nextInt();

            System.out.print("Valor: R$ ");
            BigDecimal valor = scanner.nextBigDecimal();

            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                System.err.println("\nErro: Valor deve ser maior que zero");
                return;
            }

            contaService.sacar(numeroConta, valor);
            System.out.printf("\nSaque realizado! R$ %.2f\n", valor);

        } catch (Exception e) {
            System.err.println("\nErro: " + e.getMessage());
        }
    }

    private void realizarTransferencia() {
        System.out.println("\n=== TRANSFERENCIA ===");

        try {
            System.out.print("Conta Origem: ");
            Integer contaOrigem = scanner.nextInt();

            System.out.print("Conta Destino: ");
            Integer contaDestino = scanner.nextInt();

            System.out.print("Valor: R$ ");
            BigDecimal valor = scanner.nextBigDecimal();

            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                System.err.println("\nErro: Valor deve ser maior que zero");
                return;
            }

            if (contaOrigem.equals(contaDestino)) {
                System.err.println("\nErro: Contas nao podem ser iguais");
                return;
            }

            contaService.transferir(contaOrigem, contaDestino, valor);
            System.out.printf("\nTransferencia realizada! R$ %.2f\n", valor);

        } catch (Exception e) {
            System.err.println("\nErro: " + e.getMessage());
        }
    }

    private void gerarExtrato() {
        System.out.println("\n=== EXTRATO ===");

        try {
            System.out.print("Conta: ");
            Integer numeroConta = scanner.nextInt();

            List<Transacao> transacoes = transacaoRepository.
                    findTransacoesByConta(numeroConta);

            if (transacoes.isEmpty()) {
                System.out.println("Nenhuma transacao encontrada");
                return;
            }

            System.out.println();
            for (Transacao t : transacoes) {
                System.out.printf("%s | %s | R$ %.2f | %s\n",
                        dateFormat.format(t.getDataTransacao()),
                        t.getTipoTransacao(),
                        t.getValor(),
                        t.getStatus());
            }
            System.out.println("\nTotal: " + transacoes.size() + " transacao(oes)");

        } catch (Exception e) {
            System.err.println("\nErro: " + e.getMessage());
        }
    }
}