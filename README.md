# 📅 Agendador de Tarefas

Microsserviço responsável pelo gerenciamento de tarefas e eventos da aplicação de agendamento.

Desenvolvido utilizando **Java e Spring Boot**, o serviço disponibiliza uma API REST para criação, consulta, atualização e exclusão de tarefas, além do controle do status dos eventos.

O serviço também realiza comunicação com o microsserviço de usuários para obter informações relacionadas ao usuário autenticado.

---

## 🎯 Objetivo

O objetivo deste microsserviço é gerenciar as tarefas e eventos cadastrados pelos usuários da aplicação.

Cada tarefa possui informações como:

- Nome da tarefa
- Descrição
- Data de criação
- Data do evento
- E-mail do usuário
- Data de alteração
- Status da tarefa

O serviço utiliza **MongoDB** para persistência dos dados.

---

## 🚀 Funcionalidades

### 📋 Gerenciamento de tarefas

- Criar tarefas
- Consultar tarefas do usuário autenticado
- Consultar tarefas por período
- Atualizar tarefas
- Alterar status de uma tarefa
- Excluir tarefas

### 🔐 Segurança

A API utiliza **Spring Security** e **JWT** para autenticação.

O token enviado pelo cliente é utilizado para identificar o usuário e controlar o acesso às operações relacionadas às tarefas.

### 🔗 Comunicação entre microsserviços

O serviço utiliza **OpenFeign** para realizar comunicação com o microsserviço de usuários.

Essa integração permite consultar os dados do usuário através de uma API interna.

---

## 🏗️ Arquitetura

O `agendador-tarefas` faz parte de uma arquitetura baseada em microsserviços.

```text
                         ┌───────────────┐
                         │    Cliente    │
                         └───────┬───────┘
                                 │
                                 ▼
                         ┌───────────────┐
                         │      BFF      │
                         │    :8084      │
                         └───────┬───────┘
                                 │
                                 ▼
                    ┌────────────────────────┐
                    │   Agendador de Tarefas │
                    │         :8081          │
                    └───────────┬────────────┘
                                │
                    ┌───────────┴───────────┐
                    │                       │
                    ▼                       ▼
             ┌──────────────┐       ┌──────────────┐
             │   MongoDB    │       │    Usuário   │
             │              │       │    :8080     │
             └──────────────┘       └──────────────┘
                                          │
                                          ▼
                                   ┌──────────────┐
                                   │  PostgreSQL  │
                                   └──────────────┘
```

### Comunicação com o serviço de usuários

O microsserviço utiliza **OpenFeign** através do `UsuarioClient` para realizar chamadas ao serviço de usuários.

```text
Agendador de Tarefas
        │
        │ HTTP / OpenFeign
        ▼
Microsserviço Usuário
        │
        ▼
   PostgreSQL
```

---

## 🛠️ Tecnologias utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Web MVC**
- **Spring Security**
- **Spring Data MongoDB**
- **MongoDB**
- **JWT**
- **OpenFeign**
- **MapStruct**
- **Lombok**
- **Gradle**
- **Docker**
- **GitHub Actions**

---

## 🔐 Autenticação

A autenticação utiliza **JWT (JSON Web Token)**.

O cliente deve enviar o token no header das requisições protegidas:

```http
Authorization: Bearer <token>
```

O `JwtRequestFilter` intercepta as requisições e:

1. Obtém o token do header `Authorization`.
2. Extrai o e-mail do usuário através do JWT.
3. Consulta o microsserviço de usuários.
4. Valida o token.
5. Define o usuário autenticado no contexto do Spring Security.

---

## 🗄️ Banco de dados

O microsserviço utiliza **MongoDB** para armazenar as tarefas.

A entidade principal é:

```text
TarefaEntity
```

Coleção utilizada:

```text
tarefa
```

Cada tarefa possui:

```text
id
nomeTarefa
descricao
dataCriacao
dataEvento
emailUsuario
dataAlteracao
statusTarefaEnum
```

---

## 📌 Status das tarefas

As tarefas possuem três possíveis estados:

```text
PENDENTE
NOTIFICADO
CANCELADO
```

Representados pelo enum:

```java
StatusTarefaEnum
```

---

## 📡 Endpoints

### Criar tarefa

```http
POST /tarefa
```

Cria uma nova tarefa para o usuário autenticado.

🔐 Requer JWT.

---

### Buscar tarefas do usuário

```http
GET /tarefa
```

Retorna as tarefas relacionadas ao usuário autenticado.

🔐 Requer JWT.

---

### Buscar tarefas por período

```http
GET /tarefa/eventos
```

Permite consultar tarefas dentro de um intervalo de datas.

Parâmetros:

```text
dataInicial
dataFinal
```

Exemplo:

```text
GET /tarefa/eventos?dataInicial=2026-09-01T00:00:00&dataFinal=2026-09-30T23:59:59
```

---

### Excluir tarefa

```http
DELETE /tarefa?id={id}
```

Remove uma tarefa pelo seu identificador.

---

### Alterar status

```http
PATCH /tarefa?status={status}&id={id}
```

Altera o status de uma tarefa.

Exemplo:

```text
PATCH /tarefa?status=NOTIFICADO&id=123
```

Status disponíveis:

```text
PENDENTE
NOTIFICADO
CANCELADO
```

---

### Atualizar tarefa

```http
PUT /tarefa?id={id}
```

Atualiza os dados de uma tarefa existente.

Exemplo:

```json
{
  "nomeTarefa": "Estudar Java",
  "descricao": "Estudar Spring Boot",
  "dataEvento": "15-09-2026 19:00:00"
}
```

---

## 🔄 Fluxo de uma tarefa

```text
Usuário
   │
   │ JWT
   ▼
Agendador de Tarefas
   │
   ├── Valida token
   │
   ├── Identifica usuário
   │
   ├── Consulta usuário via OpenFeign
   │
   └── Persiste tarefa
           │
           ▼
        MongoDB
```

---

## 🧩 Organização do projeto

O projeto está organizado separando responsabilidades entre controllers, regras de negócio, DTOs, entidades, repositórios, segurança e integração com outros serviços.

```text
src/main/java/com/victor/agendadortarefas/

├── bussines/
│   ├── TarefaService.java
│   ├── dto/
│   │   ├── TarefaDTO.java
│   │   └── UsuarioDTO.java
│   └── mapper/
│       ├── TarefaConverter.java
│       └── TarefaUpdateConverter.java
│
├── controller/
│   ├── TarefaController.java
│   └── GlobalExeceptionHandler.java
│
└── infrastructure/
    ├── client/
    │   └── UsuarioClient.java
    ├── entity/
    │   └── TarefaEntity.java
    ├── enuns/
    │   └── StatusTarefaEnum.java
    ├── exceptions/
    ├── repository/
    │   └── TarefasRepository.java
    └── security/
        ├── JwtRequestFilter.java
        ├── JwtUtil.java
        ├── SecurityConfig.java
        └── UserDetailsServiceImpl.java
```

---

## ⚙️ Configuração

Atualmente, a aplicação utiliza as seguintes configurações:

```properties
spring.application.name=agendador-tarefas

spring.data.mongodb.uri=mongodb://localhost:27017/db_agendador

usuario.url=localhost:8080

server.port=8081
```

O microsserviço de tarefas utiliza:

```text
MongoDB → localhost:27017
Usuário → localhost:8080
Agendador → localhost:8081
```

---

## 🐳 Executando o projeto

Com o MongoDB disponível localmente, execute a aplicação através da IDE ou utilizando o Gradle.

Para gerar o projeto:

```powershell
.\gradlew build
```

Para executar os testes:

```powershell
.\gradlew test
```

A aplicação será iniciada na porta:

```text
8081
```

---

## 🧪 Testes

O projeto possui estrutura inicial para testes utilizando o ambiente de testes do Spring Boot.

Os testes podem ser executados com:

```powershell
.\gradlew test
```

---

## 🔄 Integração com outros microsserviços

O `agendador-tarefas` não funciona isoladamente dentro da arquitetura completa.

Ele possui integração com o:

### 👤 Microsserviço de Usuários

Responsável pelo gerenciamento dos usuários e seus dados.

```text
Usuário
Porta: 8080
Banco: PostgreSQL
```

### 📅 Microsserviço de Tarefas

Responsável pelo gerenciamento das tarefas.

```text
Agendador de Tarefas
Porta: 8081
Banco: MongoDB
```

---

## 📌 Projeto completo

Este microsserviço faz parte do projeto:

**Agendador de Tarefas — Arquitetura de Microsserviços**

A aplicação é dividida em diferentes serviços, cada um responsável por uma parte específica do sistema.

---

## 🚧 Próximos passos

Algumas melhorias planejadas para evolução do projeto:

- [ ] Expandir testes unitários
- [ ] Adicionar testes de integração
- [ ] Melhorar tratamento global de exceções
- [ ] Externalizar configurações sensíveis
- [ ] Evoluir documentação da API
- [ ] Melhorar observabilidade e logs
- [ ] Evoluir pipeline de CI/CD
- [ ] Containerizar o ambiente completo
- [ ] Integrar completamente com o serviço de notificações

---

## 👨‍💻 Autor

**Victor Raupp**

Estudante de Engenharia de Software e desenvolvedor em formação com foco em:

- Java
- Spring Boot
- APIs REST
- Banco de dados
- Microsserviços
- Spring Security
- JWT