# 🏦 JavaBank

Sistema bancário simples em Java com persistência de dados via MySQL.

---

## 🎯 Visão Geral

JavaBank é um projeto acadêmico que implementa as operações básicas de um banco:
- Criação de contas (corrente, jurídica, salário e poupança)
- Depósito, saque e transferências
- Consulta de extratos (movimentações)

O backend é desenvolvido em Java, organizado em camadas nos pacotes `model`, `service` e `main`, com persistência em banco MySQL via JDBC.

---

## 📂 Estrutura do Projeto
JavaBank/
├── src/main/java/br/com/compass/
│   ├── model
│   │   ├── enums
│   │   │   ├── AccountType — Enum que representa o tipo de conta (CHECKING, SAVINGS, SALARY, BUSINESS)
│   │   │   └── TransactionType — Enum que representa o tipo de transação (DEPOSIT, WITHDRAW, TRANSFER)
│   │   ├── Account.java — Modelo de entidade que representa uma conta bancária
│   │   ├── Transaction.java — Modelo de entidade que representa uma transação financeira
│   │   ├── TransactionId.java — Classe de chave composta usada pela entidade Transaction
│   │   └── User.java — Modelo de entidade que representa um usuário do sistema
│   ├── repository
│   │   ├── AccountRepository.java — Interface de repositório para operações com contas no banco de dados
│   │   ├── UserRepository.java — Interface de repositório para operações com usuários no banco de dados
│   ├── service
│   │   ├── AccountService.java — Classe de serviço que contém as regras de negócio relacionadas às contas
│   │   ├── UserService.java — Classe de serviço responsável pela lógica de criação e gerenciamento de usuários
│   ├── util
│   │   ├── Connection.java — Classe utilitária para gerenciamento da conexão com o banco de dados
│   └── App.java — Classe principal com menu interativo para execução da aplicação no terminal
├── pom.xml — Gerenciador de dependências e build do Maven
└── README.md — Este documento

---


## 🚀 Pré-requisitos

- Java JDK 17
- Maven 3.x
- Banco de dados MySQL
- Driver JDBC (MySQL) disponível

---

## ⚙️ Configuração do Banco de Dados

1. Crie o schema/banco de dados:
   ```sql
   CREATE DATABASE javabank;
   USE javabank;

2. Altere os dados de acesso ao banco no arquivo `persistence.xml`.
---


🧭 Uso
Ao iniciar, um menu no terminal permite:

Criar conta

Acessar uma conta usando email e senha

Sacar

Depositar

Ver saldo

Transferir entre contas

Ver extrato de uma conta

Sair