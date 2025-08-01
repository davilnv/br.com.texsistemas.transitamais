# API Transita+ 🚦

Projeto Backend da Aplicação Transita+

## Como subir o ambiente de desenvolvimento 🛠️

Siga os passos abaixo para preparar e executar o ambiente completo:

1. **Compile todos os módulos do projeto** ⚙️

   Execute o comando abaixo na raiz do projeto para gerar os arquivos JAR necessários:
   ```sh
   mvn clean install
   ```

2. **Configure o arquivo .env** 📝

   Crie um arquivo chamado `.env` na raiz do projeto (caso ainda não exista) e defina o segredo do client do Keycloak:

   ```
   KEYCLOAK_CLIENT_SECRET=seu-secret-aqui
   ```

   > ⚠️ **Importante:** Não versionar o arquivo `.env` (adicione ao `.gitignore`).

3. **Gere o arquivo de realm do Keycloak com o secret correto** 🔑

   Execute o script para substituir a variável de ambiente no JSON do realm:

   ```sh
   ./generate-realm-json.sh
   ```

   > ⚠️ **Importante:** É possível utilizar o URL de prod ou dev, para produção utilizar `-prod` ao executar o script. Exemplo: `./generate-realm-json.sh -orod`.

4. **Suba os containers com Docker Compose** 🐳

   Inicie todos os serviços em modo detached:

   ```sh
   docker-compose up -d
   ```

5. **Acesse os serviços** 🌐

   - 🛡️ Keycloak: [http://localhost:8080](http://localhost:8080)
   - 📋 Eureka: [http://localhost:8761](http://localhost:8761)
   - 🚪 API Gateway: [http://localhost:8181](http://localhost:8181)
   - 🧩 Outros microsserviços: consulte as portas no `docker-compose.yml`

---

> 💡 **Dica:** Sempre que alterar o secret no `.env`, execute novamente o script de geração do realm antes de subir o ambiente.