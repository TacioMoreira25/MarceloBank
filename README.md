# MarceloBank

Sistema bancário desenvolvido como projeto da disciplina de Banco de Dados do curso de Engenharia da Computação da FAINOR.

## Sobre o Projeto

MarceloBank é uma aplicação completa de gerenciamento bancário que simula operações reais de um banco, incluindo gestão de clientes, contas, cartões, transações e empréstimos. O sistema foi desenvolvido utilizando Spring Boot e MySQL, com foco em boas práticas de desenvolvimento e modelagem de banco de dados.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.5.6
- Spring Data JPA
- MySQL 8
- Lombok
- Maven
- Dotenv Java (gerenciamento de variáveis de ambiente)

## Funcionalidades

### Gestão de Clientes
- Cadastro de clientes com validação de CPF
- Atualização de dados cadastrais
- Consulta de informações completas do cliente
- Listagem de todos os clientes

### Gestão de Agências
- Cadastro de agências bancárias
- Relatórios por agência
- Estatísticas de contas e saldos por agência

### Gestão de Contas
- Abertura de contas (Corrente, Poupança, Salário, Investimento)
- Consulta de saldo
- Listagem de contas por cliente ou agência
- Geração de extratos detalhados

### Operações Bancárias
- Depósitos
- Saques
- Transferências entre contas
- Registro automático de transações

### Gestão de Cartões
- Emissão de cartões (Crédito, Débito, Crédito/Débito, Virtual, Pré-pago)
- Bloqueio de cartões
- Consulta de cartões próximos ao vencimento
- Listagem de cartões por cliente

### Gestão de Empréstimos
- Solicitação de empréstimos
- Aprovação de empréstimos
- Pagamento de parcelas
- Consulta de saldo devedor
- Acompanhamento de empréstimos em atraso

## Modelo de Dados

O sistema possui as seguintes entidades principais:

- **Cliente**: Dados pessoais e cadastrais
- **Agência**: Informações das agências bancárias
- **Conta**: Contas bancárias com diferentes tipos
- **Cartão**: Cartões vinculados às contas
- **Transação**: Registro de todas as operações financeiras
- **Empréstimo**: Gestão de empréstimos e financiamentos

## Configuração do Ambiente

### Pré-requisitos

- JDK 17 ou superior
- MySQL 8.0 ou superior
- Maven 3.9+

### Configuração do Banco de Dados

1. Crie um banco de dados MySQL:

```sql
CREATE DATABASE marcelobank;
```

2. Configure as variáveis de ambiente copiando o arquivo `.env.example` para `.env` e ajustando conforme necessário:

```bash
cp .env.example .env
```

### Executando o Projeto

Linux/Mac:
```bash
./mvnw spring-boot:run
```

Windows:
```bash
mvnw.cmd spring-boot:run
```

## Como Usar

Ao executar a aplicação, um menu interativo será exibido no console com as seguintes opções:

```
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
```
