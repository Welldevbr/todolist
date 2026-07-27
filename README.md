# TodoList API

API REST para gerenciamento de tarefas com Spring Boot e Java 17.

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
  "description": "Descrição",
  "priority": "ALTA",
  "startedAt": "2024-08-01T08:00:00",
  "finishedAt": "2024-08-05T17:00:00"
}
```

### Listar Tarefas
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
  "priority": "CRÍTICA"
}
```

## 🔐 Autenticação

Usa **HTTP Basic Auth**. Codifique `username:password` em Base64:

```bash
echo -n "joao:senha123" | base64
# Resultado: am9hbzpzZW5oYTEyMw==

# Use no header Authorization
Authorization: Basic am9hbzpzZW5oYTEyMw==
```

## 💾 Banco de Dados

H2 in-memory. Acesse o console em:
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
├── user/           → Usuários
├── task/           → Tarefas
├── filter/         → Autenticação
└── utils/          → Utilitários
```

## 🔒 Recursos

- ✅ Autenticação HTTP Basic
- ✅ Senhas com BCrypt
- ✅ Validação de dados
- ✅ Isolamento por usuário
- ✅ H2 Database

## 📝 Exemplo Completo

```bash
# 1. Criar usuário
curl -X POST http://localhost:8080/users/ \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123","name":"Admin"}'

# 2. Criar tarefa (usar credenciais em Base64)
curl -X POST http://localhost:8080/tasks/ \
  -H "Authorization: Basic YWRtaW46MTIz" \
  -H "Content-Type: application/json" \
  -d '{"title":"Tarefa 1","priority":"ALTA","startedAt":"2024-08-10T09:00:00","finishedAt":"2024-08-15T17:00:00"}'

# 3. Listar tarefas
curl -X GET http://localhost:8080/tasks/ \
  -H "Authorization: Basic YWRtaW46MTIz"
```

## 📚 Tecnologias

- Spring Boot 4.1.0
- Spring Data JPA
- H2 Database
- BCrypt
- Lombok
- Maven

---

**Desenvolvido usando Spring Boot**
