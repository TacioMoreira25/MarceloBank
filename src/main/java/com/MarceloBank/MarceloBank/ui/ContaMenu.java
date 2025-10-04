package com.MarceloBank.MarceloBank.ui;

import com.MarceloBank.MarceloBank.enums.TipoCartao;
import com.MarceloBank.MarceloBank.enums.TipoConta;
import com.MarceloBank.MarceloBank.model.Agencia;
import com.MarceloBank.MarceloBank.model.Cartao;
import com.MarceloBank.MarceloBank.model.Cliente;
import com.MarceloBank.MarceloBank.model.Conta;
import com.MarceloBank.MarceloBank.repository.ContaRepository;
import com.MarceloBank.MarceloBank.service.CartaoService;
import com.MarceloBank.MarceloBank.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Component
public class ContaMenu
{
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    private ContaService contaService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ContaRepository contaRepository;

    public void exibir() {
        int opcao = -1;

        while (opcao != 0) {
            exibirOpcoes();
            opcao = lerOpcao();
            processarOpcao(opcao);
        }
    }

    private void exibirOpcoes() {
        System.out.println("\n=== CONTAS ===");
        System.out.println("1 - Abrir Conta");
        System.out.println("2 - Consultar Saldo");
        System.out.println("3 - Listar Contas");
        System.out.println("4 - Emitir Cartao");
        System.out.println("5 - Listar Cartoes");
        System.out.println("0 - Voltar");
        System.out.print("\nOpcao: ");
    }

    private int lerOpcao()
    {
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
            case 1 -> abrir();
            case 2 -> consultarSaldo();
            case 3 -> listar();
            case 4 -> emitirCartao();
            case 5 -> listarCartoes();
            case 0 -> {}
            default -> System.err.println("\nOpcao invalida!");
        }
    }

    private void abrir() {
        System.out.println("\n=== ABRIR CONTA ===");

        try {
            System.out.print("Numero da Conta: ");
            Integer numeroConta = scanner.nextInt();

            System.out.print("ID do Cliente: ");
            Integer clienteId = scanner.nextInt();

            System.out.print("Codigo da Agencia: ");
            Integer agenciaId = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Tipos: CORRENTE, POUPANCA, SALARIO, INVESTIMENTO");
            System.out.print("Tipo: ");
            String tipo = scanner.nextLine().toUpperCase();

            Conta conta = new Conta();
            conta.setNumeroConta(numeroConta);

            Cliente cliente = new Cliente();
            cliente.setIdCliente(clienteId);
            conta.setCliente(cliente);

            Agencia agencia = new Agencia();
            agencia.setCodigoAgencia(agenciaId);
            conta.setAgencia(agencia);

            conta.setTipoConta(TipoConta.valueOf(tipo));
            conta.setSaldo(BigDecimal.ZERO);
            conta.setDataAbertura(new Date());
            conta.setStatus("ATIVA");

            Conta salva = contaService.criarConta(conta);
            System.out.println("\nConta aberta! Numero: " + salva.getNumeroConta());

        } catch (IllegalArgumentException e) {
            System.err.println("\nErro: Tipo invalido");
        } catch (Exception e) {
            System.err.println("\nErro: " + e.getMessage());
        }
    }

    private void consultarSaldo() {
        System.out.println("\n=== CONSULTAR SALDO ===");

        try {
            System.out.print("Numero da Conta: ");
            Integer numeroConta = scanner.nextInt();

            Conta conta = contaService.buscarContaPorNumero(numeroConta);

            System.out.printf("\nConta: %d\n", conta.getNumeroConta());
            System.out.printf("Tipo: %s\n", conta.getTipoConta());
            System.out.printf("Saldo: R$ %.2f\n", conta.getSaldo());
            System.out.printf("Status: %s\n", conta.getStatus());

        } catch (Exception e) {
            System.err.println("\nErro: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\n=== CONTAS ===");

        try {
            List<Conta> contas = contaRepository.findAll();

            if (contas.isEmpty()) {
                System.out.println("Nenhuma conta cadastrada");
                return;
            }

            for (Conta c : contas) {
                System.out.printf("\nConta: %d\n", c.getNumeroConta());
                System.out.printf("Cliente: %s\n", c.getCliente().getNome());
                System.out.printf("Tipo: %s | Saldo: R$ %.2f\n", c.getTipoConta(),
                        c.getSaldo());
                System.out.println("-".repeat(40));
            }

        } catch (Exception e) {
            System.err.println("\nErro: " + e.getMessage());
        }
    }

    private void emitirCartao() {
        System.out.println("\n=== EMITIR CARTAO ===");

        try {
            System.out.print("Numero do Cartao: ");
            Integer numeroCartao = scanner.nextInt();

            System.out.print("Numero da Conta: ");
            Integer numeroConta = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Tipos: CREDITO, DEBITO, CREDITO_DEBITO");
            System.out.print("Tipo: ");
            String tipo = scanner.nextLine().toUpperCase();

            System.out.print("Limite: R$ ");
            BigDecimal limite = scanner.nextBigDecimal();

            Cartao cartao = cartaoService.emitirCartao(
                    numeroCartao, numeroConta, TipoCartao.valueOf(tipo), limite
            );

            System.out.println("\nCartao emitido! Numero: " + cartao.getNumeroCartao());

        } catch (IllegalArgumentException e) {
            System.err.println("\nErro: Tipo invalido");
        } catch (Exception e) {
            System.err.println("\nErro: " + e.getMessage());
        }
    }

    private void listarCartoes() {
        System.out.println("\n=== CARTOES ===");

        try {
            List<Cartao> cartoes = cartaoService.listarTodosCartoes();

            if (cartoes.isEmpty()) {
                System.out.println("Nenhum cartao cadastrado");
                return;
            }

            for (Cartao c : cartoes) {
                System.out.printf("\nCartao: %d\n", c.getNumeroCartao());
                System.out.printf("Tipo: %s | Limite: R$ %.2f\n",
                        c.getTipoCartao(), c.getLimite());
                System.out.println("-".repeat(40));
            }

        } catch (Exception e) {
            System.err.println("\nErro: " + e.getMessage());
        }
    }
}