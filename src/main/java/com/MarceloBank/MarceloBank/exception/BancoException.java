package com.MarceloBank.MarceloBank.exception;

public class BancoException extends RuntimeException {

    public BancoException(String mensagem) {
        super(mensagem);
    }

    public BancoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

class SaldoInsuficienteException extends BancoException {
    public SaldoInsuficienteException() {
        super("Saldo insuficiente para realizar a operacao");
    }
}

class ContaNaoEncontradaException extends BancoException {
    public ContaNaoEncontradaException(Integer numeroConta) {
        super("Conta nao encontrada: " + numeroConta);
    }
}

class ClienteNaoEncontradoException extends BancoException {
    public ClienteNaoEncontradoException(Integer idCliente) {
        super("Cliente nao encontrado: " + idCliente);
    }
}

class CpfDuplicadoException extends BancoException {
    public CpfDuplicadoException(String cpf) {
        super("CPF ja cadastrado: " + cpf);
    }
}

class ValorInvalidoException extends BancoException {
    public ValorInvalidoException() {
        super("Valor invalido para a operacao");
    }
}