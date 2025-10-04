# 🏦 MarceloBank

Sistema bancário desenvolvido como projeto da disciplina de Banco de Dados do curso de Engenharia da Computação da FAINOR.

## Sobre o Projeto

Simula operações de um banco real com gestão de clientes, contas, cartões, transações e empréstimos.

## Tecnologias

- **Java 17**
- **Spring Boot 3.5.6**
- **Spring Data JPA**
- **MySQL 8**
- **Lombok**
- **Maven**

##  Funcionalidades

- Cadastro e gerenciamento de clientes
- Cadastro de agências bancárias
- Abertura de contas (Corrente, Poupança, Salário, Investimento)
- Emissão de cartões (Crédito, Débito, Virtual, Pré-pago)
- Operações bancárias: depósito, saque e transferência
- Solicitação e gestão de empréstimos
- Consulta de saldo e extrato
- Relatórios e estatísticas

## Modelo de Dados

- **Cliente**: dados pessoais e cadastrais
- **Agência**: informações das agências
- **Conta**: contas bancárias (corrente, poupança, etc)
- **Cartão**: cartões vinculados às contas
- **Transação**: histórico de operações
- **Empréstimo**: controle de empréstimos

##  Como Executar

### Pré-requisitos

- JDK 17
- MySQL 8.0
- Maven

### Configuração

1. Crie o banco de dados:
```sql
CREATE DATABASE marcelobank;
```

2. Configure o `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/marcelobank
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

3. Execute o projeto:
```bash
./mvnw spring-boot:run
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

## Recursos Avançados

- Consultas JPQL personalizadas
- Sistema de enumerações para tipos e status
- Validações de negócio (saldo, CPF único, etc)
- Registro automático de transações
- Relatórios e estatísticas

##  Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/MarceloBank/MarceloBank/
│   │   ├── enums/          # Enumerações
│   │   ├── model/          # Entidades JPA
│   │   ├── repository/     # Repositórios Spring Data
│   │   ├── service/        # Camada de serviços
│   │   └── principal/      # Interface do usuário
│   └── resources/
│       └── application.properties
└── test/
```

##  Autor

Projeto acadêmico - Disciplina de Banco de Dados  
**Curso**: Engenharia da Computação - FAINOR

##  Licença

Projeto acadêmico para fins educacionais.

---

Desenvolvido com Spring Boot e MySQL 
