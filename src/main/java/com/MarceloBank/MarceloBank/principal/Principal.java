package com.MarceloBank.MarceloBank.principal;

import com.MarceloBank.MarceloBank.repository.*;
import com.MarceloBank.MarceloBank.enums.*;
import com.MarceloBank.MarceloBank.model.*;
import com.MarceloBank.MarceloBank.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private Scanner leitura = new Scanner(System.in);

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ContaService contaService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private EmprestimoService emprestimoService;

    @Autowired
    private AgenciaService agenciaService;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                    *** SISTEMA BANCARIO ***
                    1- Cadastrar Cliente
                    2- Cadastrar Agencia
                    3- Abrir Conta
                    4- Emitir Cartao
                    5- Realizar Deposito
                    6- Realizar Saque
                    7- Realizar Transferencia
                    8- Solicitar Emprestimo
                    9- Consultar Saldo
                    10- Gerar Extrato
                    11- Listar Clientes
                    12- Listar Contas
                    13- Listar Cartoes
                    14- Consultar Saldo Devedor
                    0 - Sair
                    Escolha uma opcao: """;

            System.out.print(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    cadastrarAgencia();
                    break;
                case 3:
                    abrirConta();
                    break;
                case 4:
                    emitirCartao();
                    break;
                case 5:
                    realizarDeposito();
                    break;
                case 6:
                    realizarSaque();
                    break;
                case 7:
                    realizarTransferencia();
                    break;
                case 8:
                    solicitarEmprestimo();
                    break;
                case 9:
                    consultarSaldo();
                    break;
                case 10:
                    gerarExtrato();
                    break;
                case 11:
                    listarClientes();
                    break;
                case 12:
                    listarContas();
                    break;
                case 13:
                    listarCartoes();
                    break;
                case 14:
                    consultarSaldoDevedor();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        }
    }

    private void cadastrarCliente() {
        System.out.println("CADASTRAR CLIENTE");

        System.out.print("Nome: ");
        String nome = leitura.nextLine();

        System.out.print("CPF: ");
        String cpf = leitura.nextLine();

        System.out.print("Email: ");
        String email = leitura.nextLine();

        System.out.print("Telefone: ");
        String telefone = leitura.nextLine();

        System.out.print("Endereco: ");
        String endereco = leitura.nextLine();

        try {
            Cliente cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setCpf(cpf);
            cliente.setEmail(email);
            cliente.setTelefone(telefone);
            cliente.setEndereco(endereco);
            cliente.setDataCadastro(new Date());

            Cliente clienteSalvo = clienteService.criarCliente(cliente);
            System.out.println("Cliente cadastrado com ID: " + clienteSalvo.getIdCliente());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void cadastrarAgencia() {
        System.out.println("CADASTRAR AGENCIA");

        System.out.print("Nome da Agencia: ");
        String nome = leitura.nextLine();

        System.out.print("Endereco: ");
        String endereco = leitura.nextLine();

        System.out.print("Telefone: ");
        String telefone = leitura.nextLine();

        System.out.print("Gerente: ");
        String gerente = leitura.nextLine();

        try {
            Agencia agencia = new Agencia();
            agencia.setNomeAgencia(nome);
            agencia.setEndereco(endereco);
            agencia.setTelefone(telefone);
            agencia.setGerente(gerente);

            Agencia agenciaSalva = agenciaService.criarAgencia(agencia);
            System.out.println("Agencia cadastrada com codigo: " + agenciaSalva.getCodigoAgencia());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void abrirConta() {
        System.out.println("ABRIR CONTA");

        System.out.print("Numero da Conta: ");
        Integer numeroConta = leitura.nextInt();

        System.out.print("ID do Cliente: ");
        Integer clienteId = leitura.nextInt();

        System.out.print("Codigo da Agencia: ");
        Integer agenciaId = leitura.nextInt();
        leitura.nextLine();

        System.out.println("Tipos de conta: CORRENTE, POUPANCA, SALARIO, INVESTIMENTO");
        System.out.print("Tipo da Conta: ");
        String tipoContaStr = leitura.nextLine().toUpperCase();

        try {
            Conta conta = new Conta();
            conta.setNumeroConta(numeroConta);

            Cliente cliente = new Cliente();
            cliente.setIdCliente(clienteId);
            conta.setCliente(cliente);

            Agencia agencia = new Agencia();
            agencia.setCodigoAgencia(agenciaId);
            conta.setAgencia(agencia);

            conta.setTipoConta(TipoConta.valueOf(tipoContaStr));
            conta.setSaldo(BigDecimal.ZERO);
            conta.setDataAbertura(new Date());
            conta.setStatus("ATIVA");

            Conta contaSalva = contaService.criarConta(conta);
            System.out.println("Conta aberta: " + contaSalva.getNumeroConta());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void emitirCartao() {
        System.out.println("EMITIR CARTAO");

        System.out.print("Numero do Cartao: ");
        Integer numeroCartao = leitura.nextInt();

        System.out.print("Numero da Conta: ");
        Integer numeroConta = leitura.nextInt();
        leitura.nextLine();

        System.out.println("Tipos de cartao: CREDITO, DEBITO, CREDITO_DEBITO");
        System.out.print("Tipo do Cartao: ");
        String tipoCartaoStr = leitura.nextLine().toUpperCase();

        System.out.print("Limite: R$ ");
        BigDecimal limite = leitura.nextBigDecimal();

        try {
            Cartao cartao = cartaoService.emitirCartao(numeroCartao, numeroConta, TipoCartao.valueOf(tipoCartaoStr), limite);
            System.out.println("Cartao emitido: " + cartao.getNumeroCartao());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void realizarDeposito() {
        System.out.println("REALIZAR DEPOSITO");

        System.out.print("Numero da Conta: ");
        Integer numeroConta = leitura.nextInt();

        System.out.print("Valor: R$ ");
        BigDecimal valor = leitura.nextBigDecimal();

        try {
            contaService.depositar(numeroConta, valor);
            System.out.println("Deposito realizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void realizarSaque() {
        System.out.println("REALIZAR SAQUE");

        System.out.print("Numero da Conta: ");
        Integer numeroConta = leitura.nextInt();

        System.out.print("Valor: R$ ");
        BigDecimal valor = leitura.nextBigDecimal();

        try {
            contaService.sacar(numeroConta, valor);
            System.out.println("Saque realizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void realizarTransferencia() {
        System.out.println("REALIZAR TRANSFERENCIA");

        System.out.print("Conta de Origem: ");
        Integer contaOrigem = leitura.nextInt();

        System.out.print("Conta de Destino: ");
        Integer contaDestino = leitura.nextInt();

        System.out.print("Valor: R$ ");
        BigDecimal valor = leitura.nextBigDecimal();

        try {
            contaService.transferir(contaOrigem, contaDestino, valor);
            System.out.println("Transferencia realizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void solicitarEmprestimo() {
        System.out.println("SOLICITAR EMPRESTIMO");

        System.out.print("ID do Cliente: ");
        Integer clienteId = leitura.nextInt();

        System.out.print("Valor Solicitado: R$ ");
        BigDecimal valorSolicitado = leitura.nextBigDecimal();

        System.out.print("Prazo (meses): ");
        Integer prazoMeses = leitura.nextInt();

        try {
            Emprestimo emprestimo = emprestimoService.solicitarEmprestimo(clienteId, valorSolicitado, prazoMeses);
            System.out.println("Emprestimo solicitado! ID: " + emprestimo.getIdEmprestimo());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void consultarSaldo() {
        System.out.println("CONSULTAR SALDO");

        System.out.print("Numero da Conta: ");
        Integer numeroConta = leitura.nextInt();

        try {
            // Assumindo que ContaService tem um método consultarSaldo
            Conta conta = contaService.buscarContaPorNumero(numeroConta);
            System.out.println("Saldo: R$ " + conta.getSaldo());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void gerarExtrato() {
        System.out.println("GERAR EXTRATO");

        System.out.print("Numero da Conta: ");
        Integer numeroConta = leitura.nextInt();

        try {
            List<Transacao> transacoes = transacaoRepository.findTransacoesByConta(numeroConta);

            if (transacoes.isEmpty()) {
                System.out.println("Nenhuma transacao encontrada");
                return;
            }

            System.out.println("EXTRATO:");
            for (Transacao t : transacoes) {
                System.out.printf("%s | %s | R$ %s | %s\n",
                        t.getDataTransacao(), t.getTipoTransacao(), t.getValor(), t.getStatus());
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarClientes() {
        System.out.println("LISTAR CLIENTES");

        try {
            List<Cliente> clientes = clienteService.listarTodos();

            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado");
                return;
            }

            System.out.println("CLIENTES:");
            for (Cliente c : clientes) {
                System.out.printf("ID: %d | Nome: %s | CPF: %s\n",
                        c.getIdCliente(), c.getNome(), c.getCpf());
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarContas() {
        System.out.println("LISTAR CONTAS");

        try {
            List<Conta> contas = contaRepository.findAll();

            if (contas.isEmpty()) {
                System.out.println("Nenhuma conta cadastrada");
                return;
            }

            System.out.println("CONTAS:");
            for (Conta c : contas) {
                System.out.printf("N: %d | Saldo: R$ %s | Tipo: %s\n",
                        c.getNumeroConta(), c.getSaldo(), c.getTipoConta());
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarCartoes() {
        System.out.println("LISTAR CARTOES");

        try {
            List<Cartao> cartoes = cartaoService.listarTodosCartoes();

            if (cartoes.isEmpty()) {
                System.out.println("Nenhum cartao cadastrado");
                return;
            }

            System.out.println("CARTOES:");
            for (Cartao c : cartoes) {
                System.out.printf("N: %d | Tipo: %s | Limite: R$ %s\n",
                        c.getNumeroCartao(), c.getTipoCartao(), c.getLimite());
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void consultarSaldoDevedor() {
        System.out.println("CONSULTAR SALDO DEVEDOR");

        System.out.print("ID do Cliente: ");
        Integer clienteId = leitura.nextInt();

        try {
            BigDecimal saldoDevedor = emprestimoService.getSaldoDevedorTotal(clienteId);
            System.out.println("Saldo devedor: R$ " + saldoDevedor);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
