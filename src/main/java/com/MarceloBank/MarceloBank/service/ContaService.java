package com.MarceloBank.MarceloBank.service;

import com.MarceloBank.MarceloBank.dto.CriarContaDTO;
import com.MarceloBank.MarceloBank.enums.StatusTransacao;
import com.MarceloBank.MarceloBank.model.*;
import com.MarceloBank.MarceloBank.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;
import java.security.SecureRandom;

@Service
@Transactional
public class ContaService
{

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;
    private final AgenciaRepository agenciaRepository;
    private final ClienteRepository clienteRepository;
    private final SecureRandom random = new SecureRandom();

    public ContaService(ContaRepository contaRepository,
                        TransacaoRepository transacaoRepository,
                        AgenciaRepository agenciaRepository,
                        ClienteRepository clienteRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
        this.agenciaRepository = agenciaRepository;
        this.clienteRepository = clienteRepository;
    }

    public Conta criarConta(CriarContaDTO dto)
    {
        Agencia agencia = agenciaRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Agência padrão (1) não encontrada"));

        Cliente cliente = clienteRepository.findByCpf(dto.getCpf())
                .orElseThrow(() -> new RuntimeException("Cliente com CPF não encontrado: "
                        + dto.getCpf()));

        Integer numeroGerado = generateNumeroConta();

        Conta conta = new Conta();
        conta.setNumeroConta(numeroGerado);
        conta.setAgencia(agencia);
        conta.setCliente(cliente);
        conta.setTipoConta(dto.getTipoConta());
        conta.setPin(dto.getPin());
        conta.setDataAbertura(new Date());
        conta.setStatus("ATIVA");
        conta.setSaldo(dto.getSaldoInicial() != null ? dto.getSaldoInicial() : BigDecimal.ZERO);

        return contaRepository.save(conta);
    }

    private Integer generateNumeroConta() {
        for (int i = 0; i < 30; i++) {
            int numero = random.nextInt(999999) + 1;
            if (!contaRepository.existsById(numero)) {
                return numero;
            }
        }
        throw new RuntimeException("Não foi possível gerar número de conta único. T" +
                "ente novamente.");
    }

    public Conta autenticar(String cpf, String pin) {
        return autenticar(cpf, pin, null);
    }

    public Conta autenticar(String cpf, String pin, Integer numeroConta)
    {
        if (numeroConta != null) {
            Conta conta = contaRepository.findById(numeroConta)
                    .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

            if (conta.getCliente() == null || !cpf.equals(conta.getCliente().getCpf())) {
                throw new RuntimeException("CPF não corresponde à conta");
            }

            if (conta.getPin() == null || !conta.getPin().equals(pin)) {
                throw new RuntimeException("PIN incorreto");
            }
            return conta;
        }

        List<Conta> contas = contaRepository.findByClienteCpf(cpf);
        if (contas.isEmpty()) {
            throw new RuntimeException("Cliente não encontrado");
        }

        for (Conta c : contas) {
            if (c.getPin() != null && c.getPin().equals(pin)) {
                return c;
            }
        }
        throw new RuntimeException("PIN incorreto");
    }
    private void validarPin(Integer numeroConta, String pin) {

        Conta conta = contaRepository.findById(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (conta.getPin() == null || !conta.getPin().equals(pin)) {
            throw new RuntimeException("PIN incorreto");
        }
    }

    public void depositar(Integer numeroConta, BigDecimal valor)
    {
        Conta conta = contaRepository.findById(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor deve ser maior que zero");
        }

        conta.setSaldo(conta.getSaldo().add(valor));
        contaRepository.save(conta);

        registrarTransacao(null, conta, valor, "DEPÓSITO");
    }

    public void sacar(Integer numeroConta, BigDecimal valor, String pin) {
        validarPin(numeroConta, pin);

        Conta conta = contaRepository.findById(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor deve ser maior que zero");
        }

        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        conta.setSaldo(conta.getSaldo().subtract(valor));
        contaRepository.save(conta);

        registrarTransacao(conta, null, valor, "SAQUE");
    }

    @Transactional
    public void transferir(Integer contaOrigem, Integer contaDestino, BigDecimal valor,
                           String pin)
    {
        validarPin(contaOrigem, pin);

        Conta origem = contaRepository.findById(contaOrigem)
                .orElseThrow(() -> new RuntimeException("Conta origem não encontrada"));

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor deve ser maior que zero");
        }

        if (contaOrigem.equals(contaDestino)) {
            throw new RuntimeException("Contas não podem ser iguais");
        }

        if (origem.getSaldo().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        Conta destino = contaRepository.findById(contaDestino)
                .orElseThrow(() -> new RuntimeException("Conta destino não encontrada"));

        origem.setSaldo(origem.getSaldo().subtract(valor));
        destino.setSaldo(destino.getSaldo().add(valor));

        contaRepository.save(origem);
        contaRepository.save(destino);

        Transacao transacao = new Transacao();
        transacao.setContaOrigem(origem);
        transacao.setContaDestino(destino);
        transacao.setValor(valor);
        transacao.setTipoTransacao("TRANSFERÊNCIA");
        transacao.setDataTransacao(new Date());
        transacao.setHoraTransacao(new Date());
        transacao.setStatus(StatusTransacao.CONCLUIDA);
        transacao.setDescricao("Transferência entre contas");

        transacaoRepository.save(transacao);
    }

    private void registrarTransacao(Conta origem, Conta destino, BigDecimal valor,
                                    String tipo) {
        Transacao transacao = new Transacao();
        transacao.setContaOrigem(origem);
        transacao.setContaDestino(destino);
        transacao.setValor(valor);
        transacao.setTipoTransacao(tipo);
        transacao.setDataTransacao(new Date());
        transacao.setHoraTransacao(new Date());
        transacao.setStatus(StatusTransacao.CONCLUIDA);

        transacaoRepository.save(transacao);
    }

    public Conta buscarContaPorNumero(Integer numeroConta) {
        return contaRepository.findById(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada: " +
                        numeroConta));
    }

    public List<Conta> buscarContasPorCpf(String cpf) {
        List<Conta> contas = contaRepository.findByClienteCpf(cpf);

        if (contas.isEmpty()) {
            throw new RuntimeException("Nenhuma conta encontrada para este CPF");
        }
        return contas;
    }

    public void deletarConta(Integer numeroConta) {
        Conta conta = contaRepository.findById(numeroConta)
            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (!transacaoRepository.findTransacoesByConta(numeroConta).isEmpty()) {
            throw new RuntimeException("Não é possível deletar a conta: existem transações vinculadas.");
        }

        boolean possuiCartoes = conta.getCartoes() != null && !conta.getCartoes().isEmpty();
        if (possuiCartoes) {
            throw new RuntimeException("Não é possível deletar a conta: existem cartões vinculados.");
        }

        contaRepository.delete(conta);
    }
}