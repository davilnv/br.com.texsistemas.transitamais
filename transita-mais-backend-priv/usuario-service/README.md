# 🧹 Usuario Service - Transita+

Este microserviço é responsável pela gestão de usuários da plataforma **Transita+**. Ele está preparado para autenticação e autorização via **Keycloak**, e integra-se com os demais serviços da plataforma utilizando autenticação via **JWT**.

---

## 🔧 Tecnologias Utilizadas

- Java 17
- Spring Boot (ultima versão)
- Spring Security (JWT)
- PostgreSQL
- Docker & Docker Compose
- Keycloak
- Flyway (migrations)
- IntelliJ IDEA (ambiente recomendado)
- OpenAPI/Swagger (opcional)

---

## 🚀 Como subir o serviço localmente

### 1. 🔐 Subir o Keycloak com Docker

Edite seu `docker-compose.yml` (na raiz do projeto):

```yaml
version: "3.9"
services:
  db:
    image: postgres:15
    container_name: postgres_Transita+
    restart: always
    ports:
      - "5432:5432"
    environment:
      POSTGRES_DB: Transita+
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    volumes:
      - db_data:/var/lib/postgresql/data

  keycloak:
    image: quay.io/keycloak/keycloak:24.0.3
    container_name: keycloak_Transita+
    command: start-dev
    ports:
      - "8080:8080"
    environment:
      KC_DB: postgres
      KC_DB_URL_HOST: db
      KC_DB_URL_DATABASE: Transita+
      KC_DB_USERNAME: postgres
      KC_DB_PASSWORD: postgres
      KC_HOSTNAME: localhost
      KC_PROXY_HEADERS: none
      KEYCLOAK_ADMIN: admin
      KEYCLOAK_ADMIN_PASSWORD: admin
    depends_on:
      - db

volumes:
  db_data:
```

> **Comando para subir**:
```bash
docker-compose up -d
```

---

### 2. 🏗️ Configurar Keycloak

Após acessar `http://localhost:8080` com `admin/admin`:

1. **Criar um Realm** chamado: `Transita+`
2. **Criar um Client** chamado: `usuario-service`
    - `Client ID`: `usuario-service`
    - `Client authentication`: *OFF* (sem client secret)
    - `Access Type`: `public`
    - `Valid Redirect URIs`: `http://localhost:8081/*`
3. **Criar Roles** no Realm: `USER`, `ADMIN`
4. **Criar um usuário de teste** com a role `USER`

---

### 3. ⚙️ Configuração do `application.yml`

```yaml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/Transita+
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: none
    show-sql: true
    properties:
      hibernate:
        default_schema: usuario
        format_sql: true
  flyway:
    enabled: true
    locations: classpath:db/migration
    schemas: usuario
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8080/realms/Transita+
```

---

## 🔐 Segurança

- A autenticação é feita com JWT emitido pelo Keycloak.
- Os endpoints são protegidos com roles:

| Endpoint                      | Permissão necessária |
|------------------------------|----------------------|
| `/api/v1/usuarios/public/**` | Acesso público       |
| `/api/v1/usuarios/user`      | `ROLE_USER`          |
| `/api/v1/usuarios/admin`     | `ROLE_ADMIN`         |

---

## ✅ Testar autenticação

1. Obtenha o token JWT via:

```bash
curl --location --request POST "http://localhost:8080/realms/Transita+/protocol/openid-connect/token" \
--header "Content-Type: application/x-www-form-urlencoded" \
--data-urlencode "grant_type=password" \
--data-urlencode "client_id=usuario-service" \
--data-urlencode "username=user_test" \
--data-urlencode "password=SUASENHA"
```

2. Use o token para testar:

```bash
curl http://localhost:8081/api/v1/usuarios/user \
  -H "Authorization: Bearer SEU_TOKEN"
```

---

## 🐳 Build & Deploy com Docker

Caso queira criar a imagem do microserviço:

```bash
./mvnw clean package -DskipTests
docker build -t usuario-service:latest .
docker run -p 8081:8081 usuario-service:latest
```

---

## 📆 Produção

No ambiente de produção, certifique-se de:

- Configurar variáveis de ambiente sensíveis (`DB`, `Keycloak`, etc).
- Utilizar HTTPS.
- Ter uma rede docker privada entre Keycloak e os microserviços.
- Monitorar o token JWT e seu tempo de expiração.
- Configurar o client do Keycloak com `Access Type: confidential` se desejar `client secret`.

---

## 🥪 Testes

Inclua testes de integração para garantir que os tokens estão sendo corretamente validados e que os endpoints respeitam as roles.

---

## 👨‍💼 Autor

Desenvolvido por Davi Lima – TEX Sistemas.

---

