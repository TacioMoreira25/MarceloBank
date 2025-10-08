package com.MarceloBank.MarceloBank.service;

import com.MarceloBank.MarceloBank.enums.StatusTransacao;
import com.MarceloBank.MarceloBank.model.Conta;
import com.MarceloBank.MarceloBank.model.Transacao;
import com.MarceloBank.MarceloBank.repository.ContaRepository;
import com.MarceloBank.MarceloBank.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Transactional
public class ContaService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;

    public ContaService(ContaRepository contaRepository, TransacaoRepository
            transacaoRepository) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public Conta criarConta(Conta conta) {
        conta.setDataAbertura(new Date());
        conta.setStatus("ATIVA");
        if (conta.getSaldo() == null) {
            conta.setSaldo(BigDecimal.ZERO);
        }
        return contaRepository.save(conta);
    }

    private void validarPin(Integer numeroConta, String pin) {
        Conta conta = contaRepository.findById(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (!conta.getPin().equals(pin)) {
            throw new RuntimeException("PIN incorreto");
        }
    }

    public void depositar(Integer numeroConta, BigDecimal valor) {
        Conta conta = contaRepository.findById(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        conta.setSaldo(conta.getSaldo().add(valor));
        contaRepository.save(conta);

        registrarTransacao(null, conta, valor, "DEPÓSITO");
    }

    public void sacar(Integer numeroConta, BigDecimal valor, String pin) {
        validarPin(numeroConta, pin);

        Conta conta = contaRepository.findById(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        conta.setSaldo(conta.getSaldo().subtract(valor));
        contaRepository.save(conta);

        registrarTransacao(conta, null, valor, "SAQUE");
    }

    @Transactional
    public void transferir(Integer contaOrigem, Integer contaDestino, BigDecimal valor, String pin) {
        validarPin(contaOrigem, pin);

        Conta origem = contaRepository.findById(contaOrigem)
                .orElseThrow(() -> new RuntimeException("Conta origem não encontrada"));

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
}