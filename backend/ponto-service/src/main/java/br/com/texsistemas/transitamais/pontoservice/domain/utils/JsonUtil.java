package br.com.texsistemas.transitamais.pontoservice.domain.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonUtil {

    public static void gerarArquivoJson(String nomeArquivo, Object objeto) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File arquivoJson = new File(nomeArquivo);
        // Serializa o objeto em JSON e escreve no arquivo
        objectMapper.writeValue(arquivoJson, objeto);
    }

    public static <T> T lerJson(String json, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Deserializa o JSON em um objeto da classe especificada
        return objectMapper.readValue(json, clazz);
    }
}
