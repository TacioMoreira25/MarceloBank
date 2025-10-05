package com.MarceloBank.MarceloBank.ui;

import com.MarceloBank.MarceloBank.enums.TipoCartao;
import com.MarceloBank.MarceloBank.enums.TipoConta;
import com.MarceloBank.MarceloBank.model.Agencia;
import com.MarceloBank.MarceloBank.model.Cartao;
import com.MarceloBank.MarceloBank.model.Cliente;
import com.MarceloBank.MarceloBank.model.Conta;
import com.MarceloBank.MarceloBank.repository.ClienteRepository;
import com.MarceloBank.MarceloBank.repository.ContaRepository;
import com.MarceloBank.MarceloBank.service.CartaoService;
import com.MarceloBank.MarceloBank.service.ClienteService;
import com.MarceloBank.MarceloBank.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Component
public class ContaMenu {
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    private ContaService contaService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private ClienteRepository clienteRepository;

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
        System.out.println("│             CONTAS                  │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  1 │ Abrir Conta                    │");
        System.out.println("│  2 │ Consultar Saldo                │");
        System.out.println("│  3 │ Listar Contas                  │");
        System.out.println("│  4 │ Emitir Cartao                  │");
        System.out.println("│  5 │ Listar Cartoes                 │");
        System.out.println("│  6 │ Bloquear Cartao                │");
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
            case 1 -> abrir();
            case 2 -> consultarSaldo();
            case 3 -> listar();
            case 4 -> emitirCartao();
            case 5 -> listarCartoes();
            case 6 -> bloquearCartao();
            case 0 -> {}
            default -> System.err.println("\n✗ Opcao invalida!");
        }
    }

    private void abrir() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║          ABRIR CONTA                   ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            System.out.print("➤ Numero da Conta: ");
            Integer numeroConta = scanner.nextInt();
            scanner.nextLine();

            System.out.print("➤ CPF do Cliente: ");
            String cpf = scanner.nextLine();

            Cliente cliente = clienteService.getClientePorCpf(cpf);
            if (cliente == null) {
                System.err.println("\n✗ Erro: Cliente não encontrado. Cadastre o cliente primeiro.");
                aguardarEnter();
                return;
            }

            System.out.print("➤ Codigo da Agencia: ");
            Integer agenciaId = scanner.nextInt();
            scanner.nextLine();

            System.out.println("\n➤ Tipos: CORRENTE, POUPANCA, SALARIO, INVESTIMENTO");
            System.out.print("➤ Tipo: ");
            String tipo = scanner.nextLine().toUpperCase();

            Conta conta = new Conta();
            conta.setNumeroConta(numeroConta);

            cliente.setCpf(cpf);
            conta.setCliente(cliente);

            Agencia agencia = new Agencia();
            agencia.setCodigoAgencia(agenciaId);
            conta.setAgencia(agencia);

            conta.setTipoConta(TipoConta.valueOf(tipo));
            conta.setSaldo(BigDecimal.ZERO);
            conta.setDataAbertura(new Date());
            conta.setStatus("ATIVA");

            Conta salva = contaService.criarConta(conta);
            System.out.println("\n✓ Conta aberta! Numero: " + salva.getNumeroConta());
            aguardarEnter();

        } catch (IllegalArgumentException e) {
            System.err.println("\n✗ Erro: Tipo invalido");
            aguardarEnter();
        } catch (Exception e) {
            System.err.println("\n✗ Erro: " + e.getMessage());
            aguardarEnter();
        }
    }

    private void consultarSaldo() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║        CONSULTAR SALDO                 ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            System.out.print("➤ Numero da Conta: ");
            Integer numeroConta = scanner.nextInt();

            Conta conta = contaService.buscarContaPorNumero(numeroConta);

            System.out.println("\n┌────────────────────────────────────────┐");
            System.out.printf("│ Conta: %-31d │\n", conta.getNumeroConta());
            System.out.printf("│ Tipo: %-32s │\n", conta.getTipoConta());
            System.out.printf("│ Saldo: R$ %-27.2f │\n", conta.getSaldo());
            System.out.printf("│ Status: %-30s │\n", conta.getStatus());
            System.out.println("└────────────────────────────────────────┘");

            aguardarEnter();

        } catch (Exception e) {
            System.err.println("\n✗ Erro: " + e.getMessage());
            aguardarEnter();
        }
    }

    private void listar() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         LISTA DE CONTAS                ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            List<Conta> contas = contaRepository.findAll();

            if (contas.isEmpty()) {
                System.out.println("➤ Nenhuma conta cadastrada");
                aguardarEnter();
                return;
            }

            for (Conta c : contas) {
                System.out.println("┌────────────────────────────────────────┐");
                System.out.printf("│ Conta: %-31d │\n", c.getNumeroConta());
                System.out.printf("│ Cliente: %-29s │\n", c.getCliente().getNome());
                System.out.printf("│ Tipo: %-32s │\n", c.getTipoConta());
                System.out.printf("│ Saldo: R$ %-27.2f │\n", c.getSaldo());
                System.out.println("└────────────────────────────────────────┘\n");
            }

            aguardarEnter();

        } catch (Exception e) {
            System.err.println("\n✗ Erro: " + e.getMessage());
            aguardarEnter();
        }
    }

    private void emitirCartao() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         EMITIR CARTAO                  ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            System.out.print("➤ Numero do Cartao: ");
            Integer numeroCartao = scanner.nextInt();

            System.out.print("➤ Numero da Conta: ");
            Integer numeroConta = scanner.nextInt();
            scanner.nextLine();

            System.out.println("\n➤ Tipos: CREDITO, DEBITO, CREDITO_DEBITO");
            System.out.print("➤ Tipo: ");
            String tipo = scanner.nextLine().toUpperCase();

            System.out.print("➤ Limite: R$ ");
            BigDecimal limite = scanner.nextBigDecimal();

            Cartao cartao = cartaoService.emitirCartao(
                    numeroCartao, numeroConta, TipoCartao.valueOf(tipo), limite
            );

            System.out.println("\n✓ Cartao emitido! Numero: " + cartao.getNumeroCartao());
            aguardarEnter();

        } catch (IllegalArgumentException e) {
            System.err.println("\n✗ Erro: Tipo invalido");
            aguardarEnter();
        } catch (Exception e) {
            System.err.println("\n✗ Erro: " + e.getMessage());
            aguardarEnter();
        }
    }

    private void listarCartoes() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         LISTA DE CARTOES               ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            List<Cartao> cartoes = cartaoService.listarTodosCartoes();

            if (cartoes.isEmpty()) {
                System.out.println("➤ Nenhum cartao cadastrado");
                aguardarEnter();
                return;
            }

            for (Cartao c : cartoes) {
                System.out.println("┌────────────────────────────────────────┐");
                System.out.printf("│ Cartao: %-30d │\n", c.getNumeroCartao());
                System.out.printf("│ Tipo: %-32s │\n", c.getTipoCartao());
                System.out.printf("│ Limite: R$ %-27.2f │\n", c.getLimite());
                System.out.println("└────────────────────────────────────────┘\n");
            }

            aguardarEnter();

        } catch (Exception e) {
            System.err.println("\n✗ Erro: " + e.getMessage());
            aguardarEnter();
        }
    }

    private void bloquearCartao() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         BLOQUEAR CARTAO                ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            System.out.print("➤ Numero do Cartao: ");
            Integer numeroCartao = scanner.nextInt();
            scanner.nextLine();

            cartaoService.bloquearCartao(numeroCartao);

            System.out.println("\n✓ Cartao bloqueado com sucesso!");
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