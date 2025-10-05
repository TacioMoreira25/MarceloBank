package com.MarceloBank.MarceloBank.ui;

import com.MarceloBank.MarceloBank.model.*;
import com.MarceloBank.MarceloBank.service.AgenciaService;
import com.MarceloBank.MarceloBank.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
        System.out.println("│  4 │ Informacoes Completas          │");
        System.out.println("│  5 │ Atualizar Cliente              │");
        System.out.println("│  6 │ Deletar Cliente                │");
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
            case 4 -> informacoesCompletas();
            case 5 -> atualizar();
            case 6 -> deletar();
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
    private void informacoesCompletas() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║    INFORMACOES COMPLETAS CLIENTE       ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            System.out.print("➤ CPF do Cliente: ");
            String cpf  = scanner.nextLine();

            Map<String, Object> info = clienteService.getInfoCompletaCliente(cpf);

            Cliente cliente = (Cliente) info.get("cliente");
            List<Conta> contas = (List<Conta>) info.get("contas");
            List<Cartao> cartoes = (List<Cartao>) info.get("cartoes");
            List<Emprestimo> emprestimos = (List<Emprestimo>) info.get("emprestimos");

            // Informações do Cliente
            System.out.println("\n┌────────────────────────────────────────┐");
            System.out.println("│           DADOS DO CLIENTE             │");
            System.out.println("├────────────────────────────────────────┤");
            System.out.printf("│ ID: %-34d │\n", cliente.getIdCliente());
            System.out.printf("│ Nome: %-32s │\n", cliente.getNome());
            System.out.printf("│ CPF: %-33s │\n", cliente.getCpf());
            System.out.printf("│ Email: %-31s │\n", cliente.getEmail());
            System.out.printf("│ Telefone: %-28s │\n", cliente.getTelefone());
            System.out.println("└────────────────────────────────────────┘");

            // Contas
            System.out.println("\n┌────────────────────────────────────────┐");
            System.out.println("│              CONTAS                    │");
            System.out.println("└────────────────────────────────────────┘");
            if (contas.isEmpty()) {
                System.out.println("  ➤ Nenhuma conta encontrada");
            } else {
                for (Conta c : contas) {
                    System.out.printf("  • Conta: %d | Tipo: %s | Saldo: R$ %.2f\n",
                            c.getNumeroConta(), c.getTipoConta(), c.getSaldo());
                }
            }

            // Cartões
            System.out.println("\n┌────────────────────────────────────────┐");
            System.out.println("│              CARTOES                   │");
            System.out.println("└────────────────────────────────────────┘");
            if (cartoes.isEmpty()) {
                System.out.println("  ➤ Nenhum cartao encontrado");
            } else {
                for (Cartao c : cartoes) {
                    System.out.printf("  • Cartao: %d | Tipo: %s | Limite: R$ %.2f\n",
                            c.getNumeroCartao(), c.getTipoCartao(), c.getLimite());
                }
            }

            // Empréstimos
            System.out.println("\n┌────────────────────────────────────────┐");
            System.out.println("│            EMPRESTIMOS                 │");
            System.out.println("└────────────────────────────────────────┘");
            if (emprestimos.isEmpty()) {
                System.out.println("  ➤ Nenhum emprestimo encontrado");
            } else {
                for (Emprestimo e : emprestimos) {
                    System.out.printf("  • ID: %d | Valor: R$ %.2f | Status: %s | Saldo Devedor: R$ %.2f\n",
                            e.getIdEmprestimo(), e.getValorSolicitado(), e.getStatus(), e.getSaldoDevedor());
                }
            }
            aguardarEnter();

        } catch (Exception e) {
            System.err.println("\n✗ Erro: " + e.getMessage());
            aguardarEnter();
        }
    }
    private void atualizar() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       ATUALIZAR CLIENTE                ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            System.out.print("➤ CPF do Cliente: ");
            String cpf = scanner.nextLine();

            Cliente clienteExistente = clienteService.listarTodos().stream()
                    .filter(c -> c.getCpf().equals(cpf))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

            System.out.print("➤ Novo Nome (deixe vazio para manter): ");
            String nome = scanner.nextLine();
            if (!nome.isBlank()) clienteExistente.setNome(nome);

            System.out.print("➤ Novo Email (deixe vazio para manter): ");
            String email = scanner.nextLine();
            if (!email.isBlank()) clienteExistente.setEmail(email);

            System.out.print("➤ Novo Telefone (deixe vazio para manter): ");
            String telefone = scanner.nextLine();
            if (!telefone.isBlank()) clienteExistente.setTelefone(telefone);

            System.out.print("➤ Novo Endereco (deixe vazio para manter): ");
            String endereco = scanner.nextLine();
            if (!endereco.isBlank()) clienteExistente.setEndereco(endereco);

            Cliente atualizado = clienteService.atualizarCliente(cpf, clienteExistente);
            System.out.println("\n✓ Cliente atualizado com sucesso! CPF: " + atualizado.getCpf());
            aguardarEnter();

        } catch (Exception e) {
            System.err.println("\n✗ Erro: " + e.getMessage());
            aguardarEnter();
        }
    }


    private void deletar() {
        limparTela();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║        DELETAR CLIENTE                 ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        try {
            System.out.print("➤ CPF do Cliente: ");
            String cpf = scanner.nextLine();

            System.out.print("\n⚠ Confirma a exclusao do cliente? (S/N): ");
            String confirmacao = scanner.nextLine().toUpperCase();

            if (confirmacao.equals("S")) {
                clienteService.deletarCliente(cpf);
                System.out.println("\n✓ Cliente deletado com sucesso!");
            } else {
                System.out.println("\n✗ Operacao cancelada");
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