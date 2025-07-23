![Badge Spring](https://raw.githubusercontent.com/PettersonnOliveira/ForumHUB-challenge/main/images/Badge-Spring.png)

# FórumHub - API REST com Spring Boot

Este é um projeto de uma API REST para um fórum, desenvolvido com Spring Boot. A aplicação permite criar, listar, atualizar e deletar tópicos, representando um CRUD completo com uso de DTOs, persistência em banco de dados MySQL, migrações com Flyway e segurança robusta com autenticação JWT.

---

## 🛠 Tecnologias utilizadas

- **Java 17+**: Linguagem de programação.
- **Spring Boot 3.x**: Framework para construção de aplicações Java robustas.
- **Spring Web**: Para construção de APIs RESTful.
- **Spring Data JPA**: Simplifica a interação com o banco de dados.
- **Hibernate**: Implementação de JPA para mapeamento objeto-relacional.
- **MySQL**: Banco de dados relacional para persistência de dados.
- **Flyway**: Ferramenta de migração de banco de dados para controle de versão do esquema.
- **Spring Security**: Framework para autenticação e autorização.
- **JWT (JSON Web Token)**: Padrão para autenticação segura em APIs REST.
- **Lombok**: Biblioteca para reduzir o código boilerplate (getters, setters, construtores).
- **Bean Validation (Jakarta Validation)**: Para validação de dados de entrada.
- **Java-JWT (Auth0)**: Biblioteca para manipulação de JWTs.

---

## ✅ Funcionalidades

- **Autenticação de Usuários** via login e geração de token JWT.
- **Criação de Tópicos (CREATE)** com validações e checagens de unicidade.
- **Listagem de Tópicos (READ)** com paginação e ordenação.
- **Detalhamento de Tópico por ID (READ)**.
- **Atualização de Tópicos (UPDATE)** com validações e tratamento de erro.
- **Inativação de Tópicos (DELETE)** usando soft delete via status.
- **Tratamento Global de Exceções** para erros padronizados (400, 404).

---

## 📁 Estrutura do Projeto

```
src/
├── main
│   ├── java
│   │   └── alura.com.ForumHUB
│   │       ├── ForumHubApplication.java
│   │       ├── Controller/
│   │       │   ├── AutenticacaoController.java
│   │       │   └── TopicoController.java
│   │       ├── domain/
│   │       │   ├── Topico/
│   │       │   │   ├── DadosAtualizarTopicos.java
│   │       │   │   ├── DadosCriarTopicos.java
│   │       │   │   ├── DadosDetalhamentoTopico.java
│   │       │   │   ├── DadosListagemTopicos.java
│   │       │   │   ├── StatusTopico.java
│   │       │   │   ├── Topico.java
│   │       │   │   └── TopicoRepository.java
│   │       │   └── usuario/
│   │       │       ├── DadosAutenticacao.java
│   │       │       ├── DadosTokenJWT.java
│   │       │       └── UsuarioRepository.java
│   │       ├── Infra/Security/
│   │       │   ├── AutenticacaoService.java
│   │       │   ├── SecurityConfigurations.java
│   │       │   ├── SecurityFilter.java
│   │       │   └── TokenService.java
│   │       ├── Security/
│   │       │   └── Usuario.java
│   │       └── Util/
│   │           └── PasswordEncoderUtil.java
│   └── resources/
│       ├── application.properties
│       └── db/
│           └── migration/
│               ├── V1__create_table_topicos.sql
│               └── V2__create_table_usuarios.sql
```

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos

- Java JDK 17+
- Apache Maven 3.x
- Servidor MySQL
- Cliente REST (Insomnia, Postman...)

### Criar Banco de Dados MySQL

```sql
CREATE DATABASE forumhub;
```

### Configurar `application.properties`

```properties
# Servidor
server.port=8080

# Banco de Dados
spring.datasource.url=jdbc:mysql://localhost:3306/forumhub?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Flyway
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true

# JWT
api.security.token.secret=123456456789012
```

---

## 🧱 Migrações Flyway

**V1__create_table_topicos.sql**
```sql
CREATE TABLE topicos (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(255) NOT NULL UNIQUE,
  mensagem TEXT NOT NULL,
  data_criacao DATETIME NOT NULL,
  status VARCHAR(50) NOT NULL,
  autor_id BIGINT NOT NULL,
  curso_id BIGINT NOT NULL
);
```

**V2__create_table_usuarios.sql**
```sql
CREATE TABLE usuarios (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  login VARCHAR(100) NOT NULL UNIQUE,
  senha VARCHAR(255) NOT NULL
);
```

---

## 🔐 Inserir Usuário de Teste

Crie uma classe `PasswordEncoderUtil` para gerar senha criptografada com BCrypt:

```java
public class PasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }
}
```

Depois, execute no MySQL:

```sql
USE forumhub;
INSERT INTO usuarios (login, senha) VALUES ('seu_email@example.com', 'SENHA_BCRYPT_AQUI');
```

---

## ▶️ Executando a Aplicação

```bash
mvn clean install
mvn spring-boot:run
```

API disponível em: `http://localhost:8080`

---

## 📡 Endpoints

### 🔐 Login

`POST /login`  
**Body JSON:**
```json
{
  "login": "seu_email@example.com",
  "senha": "123456"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs..."
}
```

---

### ✏️ Tópicos (requer token JWT no header `Authorization: Bearer <token>`)

- `POST /topicos` – Criar tópico  
- `GET /topicos` – Listar todos com paginação  
- `GET /topicos/{id}` – Detalhar tópico por ID  
- `PUT /topicos/{id}` – Atualizar tópico  
- `DELETE /topicos/{id}` – Inativar tópico

---

## 🔮 Próximos Passos

- ✅ Autorização por perfil (ADMIN, USER)
- ✅ Documentação com Swagger
- ✅ Validações avançadas
- ✅ Filtros de busca e paginação
- ✅ Testes unitários e de integração

---

## 👨‍💻 Autor

Feito por **Petterson Oliveira**  
Aluno do programa **Oracle Next Education - ONE** e **Tecnólogo da faculdade Celso Lisboa** 🚀  
