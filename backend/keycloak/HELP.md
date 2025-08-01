# Como executar o script import-realm.sh no container do Keycloak

## Requisitos

- Docker instalado
- O container do Keycloak em execução com volume montado:

  ```yaml
  volumes:
    - ./keycloak/scripts:/opt/keycloak/data/scripts
  ```

## Passo a passo

- **Verifique se o container `keycloak` está rodando**:

```bash
docker ps
```

Você deve ver algo como:

```bash
CONTAINER ID   IMAGE                  NAME       ...
abcd1234efgh   quay.io/keycloak/...   keycloak   ...
```

- **Execute o comando abaixo** para acessar o script e executá-lo diretamente dentro do container:

```bash
docker exec -it keycloak bash -c "cd /opt/keycloak/data/scripts && ./import-realm.sh"
```

- Observação, no Windows usando WSL, ao baixar o projeto a quebra de linha do arquivo .sh não é reconhecida, então para que funcione corretamente, basta abrir o arquivo no VSCODE e mudar de CRLF para LF e subir os containers novamente.

## O que este comando faz?

- Acessa o container `keycloak`
- Navega até a pasta `/opt/keycloak/data/scripts` onde o script foi montado
- Executa o script `import-realm.sh`
