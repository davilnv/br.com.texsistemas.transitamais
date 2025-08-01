# ✅ Adicionando Role a um Usuário no Keycloak via Terminal (Docker + `kcadm.sh`)

Este guia descreve o processo para adicionar uma role (papel) existente a um usuário em um **realm específico** do Keycloak, via terminal, utilizando o script `kcadm.sh` dentro de um contêiner Docker.

---

## 🧩 Pré-requisitos

- Keycloak rodando em Docker
- Acesso à máquina com o Docker
- Um usuário `admin` com permissões no **realm `master`**
- Nome do `realm` de destino (exemplo: `transitamais`)
- Nome da `role` (exemplo: `ADMIN`)
- E-mail do usuário alvo (exemplo: `sup7@admin.com`)

---

## 🐳 Acessando o contêiner do Keycloak

```bash
docker exec -it <nome-do-container> /bin/bash
```

Substitua `<nome-do-container>` pelo nome ou ID do contêiner do Keycloak.

---

## 📁 Acessando o diretório do `kcadm.sh`

```bash
cd /opt/keycloak/bin
```

---

## 🔐 Autenticando no Keycloak

**Atenção**: Para Keycloak versão 17 ou superior, **não use `/auth` na URL**.

```bash
./kcadm.sh config credentials --server http://localhost:8080   --realm master --user admin --password admin
```

Se a autenticação for bem-sucedida, você verá:

```
Logged into http://localhost:8080 as admin of realm master
```

---

## 🔍 Obtendo o ID do usuário pelo e-mail

```bash
./kcadm.sh get users -r transitamais -q email=sup7@admin.com
```

Copie o valor de `"id"` do usuário retornado (ex: `1234-abc-xyz`).

---

## 🔎 Verificando a existência da role "ADMIN"

```bash
./kcadm.sh get roles -r transitamais | grep -B 5 '"name" : "ADMIN"'
```

Ou apenas:

```bash
./kcadm.sh get roles -r transitamais
```

E procure pela role `"name": "ADMIN"`.

---

## ➕ Adicionando a role ao usuário

Utilize o e-mail do usuário com a flag `--uusername`:

```bash
./kcadm.sh add-roles -r transitamais --uusername sup7@admin.com   --rolename ADMIN
```

Se preferir usar o ID diretamente:

```bash
./kcadm.sh add-roles -r transitamais --uid <user-id> --rolename ADMIN
```

---

## ✅ Verificando se a role foi adicionada

```bash
./kcadm.sh get users/<user-id>/role-mappings/realm -r transitamais
```

Você verá algo como:

```json
[
  {
    "id": "....",
    "name": "ADMIN",
    "description": "Administrador",
    ...
  }
]
```

---

## 🧼 Logout (opcional)

```bash
./kcadm.sh config unset
```

---

## 📝 Observações

- O script `kcadm.sh` só funciona dentro do contêiner (ou com as dependências instaladas localmente).
- Em versões modernas do Keycloak (17+), o caminho `/auth` foi removido da URL base.
- Roles são case-sensitive: `ADMIN` ≠ `admin`.

---

© 2025 – TEX Sistemas / DevOps