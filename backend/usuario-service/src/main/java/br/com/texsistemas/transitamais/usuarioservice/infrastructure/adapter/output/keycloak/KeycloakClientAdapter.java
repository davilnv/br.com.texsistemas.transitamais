package br.com.texsistemas.transitamais.usuarioservice.infrastructure.adapter.output.keycloak;

import br.com.texsistemas.transitamais.commons.domain.exception.KeycloakException;
import br.com.texsistemas.transitamais.usuarioservice.application.port.output.ProvedorAutenticacaoPort;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import br.com.texsistemas.transitamais.commons.api.dto.usuario.AuthTokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakClientAdapter implements ProvedorAutenticacaoPort {

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    /**
     * Cria um usuário no Keycloak com as informações fornecidas.
     *
     * @param nome  Nome do usuário
     * @param email Email
     * @param senha Senha
     * @param roles Permissões
     */
    public void criarUsuario(String nome, String sobrenome, String email, String senha, List<String> roles) {
        // Obtem o token de admin do Keycloak
        String token = obterTokenAdmin();

        // Cria o JSON payload para criar o usuário
        JsonObject payload = fillJsonObject(nome, sobrenome, email, senha);

        ResponseEntity<String> response = sendRequest(
                keycloakServerUrl + "/admin/realms/" + realm + "/users",
                token,
                MediaType.APPLICATION_JSON,
                payload.toString(),
                HttpMethod.POST
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new KeycloakException("Erro ao criar usuário no Keycloak: " + response.getBody());
        }

        // Obtem o userId do usuário criado
        String userId = buscarUserId(email, token);

        // Associa as roles ao usuário
        associarRolesAoUsuario(userId, roles, token);
    }

    private String buscarUserId(String email, String token) {
        ResponseEntity<String> response = sendRequest(
                keycloakServerUrl + "/admin/realms/" + realm + "/users?email=" + email,
                token,
                null,
                null,
                HttpMethod.GET
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new KeycloakException("Erro ao buscar usuário no Keycloak: " + response.getBody());
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            if (jsonNode.isArray() && !jsonNode.isEmpty()) {
                return jsonNode.get(0).get("id").asText();
            } else {
                throw new KeycloakException("Usuário não encontrado no Keycloak.");
            }
        } catch (Exception e) {
            throw new KeycloakException("Erro ao processar resposta da busca de usuário: " + e.getMessage(), e);
        }
    }

    /**
     * Associar as permissões ao usuário
     *
     * @param userId Identificador do usuário
     * @param roles  Lista de permissões
     * @param token  Token de autenticação
     */
    private void associarRolesAoUsuario(String userId, List<String> roles, String token) {
        JsonArray rolesPayload = buscarRoles(roles, token);

        ResponseEntity<String> response = sendRequest(
                keycloakServerUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm",
                token,
                MediaType.APPLICATION_JSON,
                rolesPayload.toString(),
                HttpMethod.POST
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new KeycloakException("Erro ao associar roles ao usuário no Keycloak: " + response.getBody());
        }
    }

    /**
     * Busca as permissões do usuário
     *
     * @param roles Lista de permissões
     * @param token Token de autenticação
     * @return Retorna um JsonArray com as permissões
     */
    private JsonArray buscarRoles(List<String> roles, String token) {
        JsonArray rolesPayload = new JsonArray();

        for (String roleName : roles) {
            // Busca os detalhes da role
            ResponseEntity<String> roleResponse = sendRequest(
                    keycloakServerUrl + "/admin/realms/" + realm + "/roles/" + roleName,
                    token,
                    null,
                    null,
                    HttpMethod.GET
            );

            if (!roleResponse.getStatusCode().is2xxSuccessful()) {
                throw new KeycloakException("Erro ao buscar detalhes da role: " + roleResponse.getBody());
            }

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode roleDetails = objectMapper.readTree(roleResponse.getBody());

                JsonObject roleObj = new JsonObject();
                roleObj.addProperty("id", roleDetails.get("id").asText());
                roleObj.addProperty("name", roleDetails.get("name").asText());
                rolesPayload.add(roleObj);
            } catch (Exception e) {
                throw new KeycloakException("Erro ao processar detalhes da role: " + e.getMessage(), e);
            }
        }
        return rolesPayload;
    }

    /**
     * Obter o ID do Client
     *
     * @param token Token de autenticação
     * @return Retorna o ID do Client
     */
    private String obterClientUuid(String token) {
        ResponseEntity<String> response = sendRequest(
                keycloakServerUrl + "/admin/realms/" + realm + "/clients?clientId=" + clientId,
                token,
                null,
                null,
                HttpMethod.GET
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new KeycloakException("Erro ao obter client UUID do Keycloak");
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return jsonNode.get(0).get("id").asText();
        } catch (Exception e) {
            throw new KeycloakException("Erro ao processar resposta do client UUID", e);
        }
    }

    public AuthTokenDTO obterTokenUser(String email, String senha) {
        String body = "grant_type=password" +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&username=" + email +
                "&password=" + senha;

        ResponseEntity<String> response = sendRequest(
                keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token",
                null,
                MediaType.APPLICATION_FORM_URLENCODED,
                body,
                HttpMethod.POST
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new KeycloakException("Erro ao obter token do Keycloak");
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return new AuthTokenDTO(
                    jsonNode.get("access_token").asText(),
                    jsonNode.get("refresh_token") != null
                            ? jsonNode.get("refresh_token").asText()
                            : null,
                    jsonNode.get("token_type").asText(),
                    jsonNode.get("expires_in").asInt(),
                    jsonNode.get("refresh_expires_in").asInt(),
                    jsonNode.get("scope").asText()
            );
        } catch (Exception e) {
            throw new KeycloakException("Erro ao processar resposta do token", e);
        }
    }

    /**
     * Obter o Token de autenticação de ADMIN
     *
     * @return Retorna o token de autenticação
     */
    private String obterTokenAdmin() {
        String body = "grant_type=client_credentials" +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret;

        ResponseEntity<String> response = sendRequest(
                keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token",
                null,
                MediaType.APPLICATION_FORM_URLENCODED,
                body,
                HttpMethod.POST
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new KeycloakException("Erro ao obter token de admin do Keycloak");
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            throw new KeycloakException("Erro ao processar resposta do token", e);
        }
    }

    /**
     * Monta o objeto do Json de usuário
     *
     * @param nome  Nome do usuário
     * @param email E-mail
     * @param senha Senha
     * @return Retorna um objeto do usuário
     */
    private JsonObject fillJsonObject(String nome, String sobrenome, String email, String senha) {
        JsonObject cred = new JsonObject();
        cred.addProperty("type", "password");
        cred.addProperty("value", senha);
        cred.addProperty("temporary", false);

        JsonArray credentials = new JsonArray();
        credentials.add(cred);

        JsonObject payload = new JsonObject();
        payload.addProperty("username", email);
        payload.addProperty("email", email);
        payload.addProperty("enabled", true);
        payload.addProperty("firstName", nome);
        payload.addProperty("lastName", sobrenome);
        payload.add("credentials", credentials);

        return payload;
    }

    /**
     * Monta o cabeçalho da requisição HTTP
     *
     * @param token     Token de autenticação
     * @param mediaType Tipo de Midia a ser enviado
     * @return Retorna o cabeçalho montado
     */
    private HttpHeaders fillHttpHeaders(String token, MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        if (mediaType != null)
            headers.setContentType(mediaType);
        if (token != null)
            headers.setBearerAuth(token);
        return headers;
    }

    /**
     * Envia requisição para a API do Keycloak
     *
     * @param url        URL da requisição HTTP
     * @param token      Token de autenticação
     * @param mediaType  Tipo de Midia a ser enviado
     * @param body       Corpo da requisição caso exista
     * @param httpMethod Method da requisiçao
     * @return Retorna uma resposta relacionada a requisição
     */
    private ResponseEntity<String> sendRequest(String url, String token, MediaType mediaType, String body, HttpMethod httpMethod) {
        HttpHeaders headers = fillHttpHeaders(token, mediaType);

        HttpEntity<String> entity = body != null ? new HttpEntity<>(body, headers) : new HttpEntity<>(headers);

        if (httpMethod != null && httpMethod == HttpMethod.GET) {
            return restTemplate.exchange(url, httpMethod, entity, String.class);
        } else {
            return restTemplate.postForEntity(url, entity, String.class);
        }
    }
}
