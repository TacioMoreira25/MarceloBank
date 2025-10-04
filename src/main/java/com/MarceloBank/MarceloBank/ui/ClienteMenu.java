package com.MarceloBank.MarceloBank.ui;

import com.MarceloBank.MarceloBank.model.Agencia;
import com.MarceloBank.MarceloBank.model.Cliente;
import com.MarceloBank.MarceloBank.service.AgenciaService;
import com.MarceloBank.MarceloBank.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Component
public class ClienteMenu {

    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AgenciaService agenciaService;

    public void exibir() {
        int opcao = -1;

        while (opcao != 0) {
            exibirOpcoes();
            opcao = lerOpcao();
            processarOpcao(opcao);
        }
    }

    private void exibirOpcoes() {
        System.out.println("\n=== CLIENTES ===");
        System.out.println("1 - Cadastrar Cliente");
        System.out.println("2 - Listar Clientes");
        System.out.println("3 - Cadastrar Agencia");
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
            case 1 -> cadastrar();
            case 2 -> listar();
            case 3 -> cadastrarAgencia();
            case 0 -> {}
            default -> System.err.println("\nOpcao invalida!");
        }
    }

    private void cadastrar() {
        System.out.println("\n=== CADASTRAR CLIENTE ===");

        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("CPF: ");
            String cpf = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();

            System.out.print("Endereco: ");
            String endereco = scanner.nextLine();

            Cliente cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setCpf(cpf);
            cliente.setEmail(email);
            cliente.setTelefone(telefone);
            cliente.setEndereco(endereco);
            cliente.setDataCadastro(new Date());

            Cliente salvo = clienteService.criarCliente(cliente);
            System.out.println("\nSucesso! ID: " + salvo.getIdCliente());

        } catch (Exception e) {
            System.err.println("\nErro: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\n=== CLIENTES ===");

        try {
            List<Cliente> clientes = clienteService.listarTodos();

            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado");
                return;
            }

            for (Cliente c : clientes) {
                System.out.printf("\nID: %d\nNome: %s\nCPF: %s\nEmail: %s\n",
                        c.getIdCliente(), c.getNome(), c.getCpf(), c.getEmail());
                System.out.println("-".repeat(40));
            }

        } catch (Exception e) {
            System.err.println("\nErro: " + e.getMessage());
        }
    }

    private void cadastrarAgencia() {
        System.out.println("\n=== CADASTRAR AGENCIA ===");

        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Endereco: ");
            String endereco = scanner.nextLine();

            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();

            System.out.print("Gerente: ");
            String gerente = scanner.nextLine();

            Agencia agencia = new Agencia();
            agencia.setNomeAgencia(nome);
            agencia.setEndereco(endereco);
            agencia.setTelefone(telefone);
            agencia.setGerente(gerente);

            Agencia salva = agenciaService.criarAgencia(agencia);
            System.out.println("\nSucesso! Codigo: " + salva.getCodigoAgencia());

        } catch (Exception e) {
            System.err.println("\nErro: " + e.getMessage());
        }
    }
}