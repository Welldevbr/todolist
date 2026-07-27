# TodoList API

Uma API REST completa para **gerenciamento e controle de tarefas pessoais e profissionais**. Desenvolvida com Spring Boot e Java 17, oferece autenticação segura, validação robusta de dados e armazenamento persistente.

## 📋 O que é?

A **TodoList API** é um backend robusto que permite:
- 👤 Criar e gerenciar múltiplos usuários com senhas seguras
- ✅ Registrar tarefas com título, descrição, prioridade e prazos
- 🔒 Proteger dados com autenticação HTTP Basic
- 📊 Listar e atualizar tarefas de forma isolada por usuário
- ⏰ Controlar datas de início e término das tarefas

Ideal para aplicações web, mobile ou dashboards que precisam de um backend confiável para gerenciamento de tarefas.

## 🚀 Quick Start

### Requisitos
- Java 17+
- Maven 3.8+

### Executar
```bash
git clone https://github.com/Welldevbr/todolist.git
cd todolist
mvn spring-boot:run
```

A API estará em `http://localhost:8080`

## 📡 Endpoints

### Criar Usuário
```bash
POST /users/
Content-Type: application/json

{
  "username": "joao",
  "name": "João Silva",
  "email": "joao@example.com",
  "password": "senha123"
}
```

### Criar Tarefa
```bash
POST /tasks/
Authorization: Basic {base64(username:password)}
Content-Type: application/json

{
  "title": "Minha Tarefa",
  "description": "Descrição detalhada",
  "priority": "ALTA",
  "startedAt": "2024-08-01T08:00:00",
  "finishedAt": "2024-08-05T17:00:00"
}
```

### Listar Tarefas do Usuário
```bash
GET /tasks/
Authorization: Basic {base64(username:password)}
```

### Atualizar Tarefa
```bash
PUT /tasks/{id}
Authorization: Basic {base64(username:password)}
Content-Type: application/json

{
  "title": "Tarefa Atualizada",
  "priority": "CRÍTICA",
  "description": "Nova descrição"
}
```

## 🔐 Autenticação

Usa **HTTP Basic Auth** com criptografia BCrypt:

```bash
# Codificar credenciais em Base64
echo -n "joao:senha123" | base64
# Resultado: am9hbzpzZW5oYTEyMw==

# Usar no header Authorization
Authorization: Basic am9hbzpzZW5oYTEyMw==
```

**Segurança:**
- Senhas criptografadas com BCrypt (salt 12 rounds)
- Validação em cada requisição de tarefa
- Isolamento de dados por usuário

## 💾 Banco de Dados

H2 in-memory para desenvolvimento. Acesse o console em:
```
http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:todolistdb
User: admin
Password: admin
```

## 🐳 Docker

```bash
docker build -t todolist .
docker run -p 8080:8080 todolist
```

## 📁 Estrutura

```
src/main/java/br/com/application/todolist/
├── user/           → Gerenciamento de usuários
├── task/           → Gerenciamento de tarefas
├── filter/         → Filtro de autenticação HTTP Basic
└── utils/          → Funções utilitárias
```

## ✨ Recursos

- ✅ Autenticação HTTP Basic integrada
- ✅ Criptografia de senhas com BCrypt
- ✅ Validações automáticas de dados
- ✅ Isolamento de tarefas por usuário
- ✅ H2 Database in-memory
- ✅ Console H2 para debug
- ✅ Containerização com Docker

## 📝 Exemplo de Uso Completo

```bash
# 1. Criar usuário
curl -X POST http://localhost:8080/users/ \
  -H "Content-Type: application/json" \
  -d '{
    "username":"admin",
    "password":"123456",
    "name":"Administrador",
    "email":"admin@example.com"
  }'

# 2. Codificar credenciais: admin:123456 → YWRtaW46MTIzNDU2
curl -X POST http://localhost:8080/tasks/ \
  -H "Authorization: Basic YWRtaW46MTIzNDU2" \
  -H "Content-Type: application/json" \
  -d '{
    "title":"Desenvolver API",
    "description":"Criar endpoints REST",
    "priority":"ALTA",
    "startedAt":"2024-08-10T09:00:00",
    "finishedAt":"2024-08-15T17:00:00"
  }'

# 3. Listar tarefas
curl -X GET http://localhost:8080/tasks/ \
  -H "Authorization: Basic YWRtaW46MTIzNDU2"

# 4. Atualizar tarefa
curl -X PUT http://localhost:8080/tasks/{id} \
  -H "Authorization: Basic YWRtaW46MTIzNDU2" \
  -H "Content-Type: application/json" \
  -d '{"title":"API REST Desenvolvida","priority":"BAIXA"}'
```

## 📚 Tecnologias

- **Spring Boot 4.1.0** - Framework web
- **Spring Data JPA** - ORM e persistência
- **H2 Database** - Banco de dados em memória
- **BCrypt** - Criptografia de senhas
- **Lombok** - Redução de boilerplate
- **Maven** - Gerenciador de dependências
- **Java 17** - Linguagem de programação

## 💡 Casos de Uso

- 📱 Backend para aplicativo mobile de tarefas
- 🌐 API para dashboard pessoal
- 📊 Sistema de gestão de projetos
- 👥 Plataforma colaborativa com múltiplos usuários
- 🔄 Integração com outros serviços

---

**Desenvolvido com ❤️ usando Spring Boot**
