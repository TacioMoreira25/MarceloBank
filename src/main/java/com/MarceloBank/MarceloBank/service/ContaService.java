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
import java.util.List;

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

    public Conta criarConta(Conta conta)
    {
        conta.setDataAbertura(new Date());
        conta.setStatus("ATIVA");
        if (conta.getSaldo() == null) {
            conta.setSaldo(BigDecimal.ZERO);
        }
        return contaRepository.save(conta);
    }

    public void depositar(Integer numeroConta, BigDecimal valor)
    {
        Conta conta = contaRepository.findById(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        conta.setSaldo(conta.getSaldo().add(valor));
        contaRepository.save(conta);

        registrarTransacao(null, conta, valor, "DEPÓSITO");
    }

    public void sacar(Integer numeroConta, BigDecimal valor)
    {
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
    public void transferir(Integer contaOrigem, Integer contaDestino, BigDecimal valor)
    {
        sacar(contaOrigem, valor);
        depositar(contaDestino, valor);

        // Registra a transferência completa
        Conta origem = contaRepository.findById(contaOrigem).orElseThrow();
        Conta destino = contaRepository.findById(contaDestino).orElseThrow();

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

    public List<Transacao> extrato(Integer numeroConta)
    {
        return transacaoRepository.findTransacoesByConta(numeroConta);
    }

    private void registrarTransacao(Conta origem, Conta destino, BigDecimal valor,
                                    String tipo)
    {
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

    public Conta buscarContaPorNumero(Integer numeroConta)
    {
        return contaRepository.findById(numeroConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada: " +
                        numeroConta));
    }
}