package com.MarceloBank.MarceloBank.service;

import com.MarceloBank.MarceloBank.enums.StatusEmprestimo;
import com.MarceloBank.MarceloBank.model.Cliente;
import com.MarceloBank.MarceloBank.model.Conta;
import com.MarceloBank.MarceloBank.model.Emprestimo;
import com.MarceloBank.MarceloBank.repository.ClienteRepository;
import com.MarceloBank.MarceloBank.repository.ContaRepository;
import com.MarceloBank.MarceloBank.repository.EmprestimoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EmprestimoService {
    private final EmprestimoRepository emprestimoRepository;
    private final ClienteRepository clienteRepository;
    private final ContaRepository contaRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, ClienteRepository
                                     clienteRepository,
                             ContaRepository contaRepository)
    {
        this.emprestimoRepository = emprestimoRepository;
        this.clienteRepository = clienteRepository;
        this.contaRepository = contaRepository;
    }

    public Emprestimo solicitarEmprestimo(String cpf, BigDecimal valorSolicitado,
                                          Integer prazoMeses)
    {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setCliente(cliente);
        emprestimo.setValorSolicitado(valorSolicitado);
        emprestimo.setPrazoMeses(prazoMeses);
        emprestimo.setDataSolicitacao(new Date());
        emprestimo.setStatus(StatusEmprestimo.SOLICITADO);
        emprestimo.setTaxaJuros(new BigDecimal("1.5")); // Taxa fixa exemplo

        return emprestimoRepository.save(emprestimo);
    }

    public Emprestimo aprovarEmprestimo(Integer emprestimoId, BigDecimal valorAprovado)
    {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        List<Conta> contas = contaRepository.findByClienteCpf(emprestimo.getCliente().getCpf());
        Conta conta = contas.stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Cliente não possui conta"));

        // Atualiza empréstimo
        emprestimo.setStatus(StatusEmprestimo.APROVADO);
        emprestimo.setValorAprovado(valorAprovado);
        emprestimo.setDataAprovacao(new Date());
        emprestimo.setSaldoDevedor(valorAprovado);

        // Credita na conta
        conta.setSaldo(conta.getSaldo().add(valorAprovado));
        contaRepository.save(conta);

        return emprestimoRepository.save(emprestimo);
    }

    public void pagarParcela(Integer emprestimoId, BigDecimal valorPagamento)
    {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        if (emprestimo.getSaldoDevedor().compareTo(valorPagamento) < 0) {
            throw new RuntimeException("Valor do pagamento maior que saldo devedor");
        }

        emprestimo.setSaldoDevedor(emprestimo.getSaldoDevedor().subtract(valorPagamento));

        if (emprestimo.getSaldoDevedor().compareTo(BigDecimal.ZERO) == 0)
        {
            emprestimo.setStatus(StatusEmprestimo.LIQUIDADO);
        }

        emprestimoRepository.save(emprestimo);
    }

    public BigDecimal getSaldoDevedorTotal(String cpf) {
            List<Object[]> resultados = emprestimoRepository.findSaldoDevedorPorCliente(cpf);

            return resultados.stream()
                    .findFirst()
                    .map(obj -> (BigDecimal) obj[1])
                    .orElse(BigDecimal.ZERO);
    }
}
