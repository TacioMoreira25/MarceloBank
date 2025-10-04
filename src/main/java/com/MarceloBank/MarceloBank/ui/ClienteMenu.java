package com.MarceloBank.MarceloBank.ui;

import com.MarceloBank.MarceloBank.model.Agencia;
import com.MarceloBank.MarceloBank.model.Cliente;
import com.MarceloBank.MarceloBank.service.AgenciaService;
import com.MarceloBank.MarceloBank.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Component
public class ClienteMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

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
        limparTela();
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.println("│            CLIENTES                 │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  1 │ Cadastrar Cliente              │");
        System.out.println("│  2 │ Listar Clientes                │");
        System.out.println("│  3 │ Cadastrar Agencia              │");
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
            case 1 -> cadastrar();
            case 2 -> listar();
            case 3 -> cadastrarAgencia();
            case 0 -> {}
            default -> System.err.println("\n✗ Opcao invalida!");
        }
    }

    private void cadastrar() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       CADASTRAR CLIENTE                ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            System.out.print("➤ Nome: ");
            String nome = scanner.nextLine();

            System.out.print("➤ Data de Nascimento: ");
            String dataNascStr = scanner.nextLine();
            Date dataNascimento = dateFormat.parse(dataNascStr);

            System.out.print("➤ CPF: ");
            String cpf = scanner.nextLine();

            System.out.print("➤ Email: ");
            String email = scanner.nextLine();

            System.out.print("➤ Telefone: ");
            String telefone = scanner.nextLine();

            System.out.print("➤ Endereco: ");
            String endereco = scanner.nextLine();

            Cliente cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setCpf(cpf);
            cliente.setEmail(email);
            cliente.setTelefone(telefone);
            cliente.setEndereco(endereco);
            cliente.setDataNascimento(dataNascimento);
            cliente.setDataCadastro(new Date());

            Cliente salvo = clienteService.criarCliente(cliente);
            System.out.println("\n✓ Sucesso! ID: " + salvo.getIdCliente());
            aguardarEnter();

        } catch (Exception e) {
            System.err.println("\n✗ Erro: " + e.getMessage());
            aguardarEnter();
        }
    }

    private void listar() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         LISTA DE CLIENTES              ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            List<Cliente> clientes = clienteService.listarTodos();

            if (clientes.isEmpty()) {
                System.out.println("➤ Nenhum cliente cadastrado");
                aguardarEnter();
                return;
            }

            for (Cliente c : clientes) {
                System.out.println("┌────────────────────────────────────────┐");
                System.out.printf("│ ID: %-34d │\n", c.getIdCliente());
                System.out.printf("│ Nome: %-32s │\n", c.getNome());
                System.out.printf("│ CPF: %-33s │\n", c.getCpf());
                System.out.printf("│ Email: %-31s │\n", c.getEmail());
                System.out.printf("│ Data Nasc: %-27s │\n",
                        c.getDataNascimento() != null ? dateFormat.format(c.getDataNascimento()) : "N/A");
                System.out.println("└────────────────────────────────────────┘\n");
            }

            aguardarEnter();

        } catch (Exception e) {
            System.err.println("\n✗ Erro: " + e.getMessage());
            aguardarEnter();
        }
    }

    private void cadastrarAgencia() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       CADASTRAR AGENCIA                ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            System.out.print("➤ Nome: ");
            String nome = scanner.nextLine();

            System.out.print("➤ Endereco: ");
            String endereco = scanner.nextLine();

            System.out.print("➤ Telefone: ");
            String telefone = scanner.nextLine();

            System.out.print("➤ Gerente: ");
            String gerente = scanner.nextLine();

            Agencia agencia = new Agencia();
            agencia.setNomeAgencia(nome);
            agencia.setEndereco(endereco);
            agencia.setTelefone(telefone);
            agencia.setGerente(gerente);

            Agencia salva = agenciaService.criarAgencia(agencia);
            System.out.println("\n✓ Sucesso! Codigo: " + salva.getCodigoAgencia());
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