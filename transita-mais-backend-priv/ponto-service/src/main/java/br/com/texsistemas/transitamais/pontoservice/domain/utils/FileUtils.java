package br.com.texsistemas.transitamais.pontoservice.domain.utils;

import br.com.texsistemas.transitamais.commons.domain.exception.PontoException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {

    public static Path getPath(String caminho) {
        return Paths.get(caminho);
    }

    public static void copiarArquivo(InputStream inputStream, Path destino, boolean substituir) throws Exception {
        Files.copy(inputStream, destino,
                substituir ? StandardCopyOption.REPLACE_EXISTING
                        : StandardCopyOption.COPY_ATTRIBUTES);
    }

    public static void copiarArquivo(Path origem, Path destino, boolean substituir) throws Exception {
        Files.copy(origem, destino,
                substituir ? StandardCopyOption.REPLACE_EXISTING
                        : StandardCopyOption.COPY_ATTRIBUTES);
    }

    public static Path validarArquivo(String pasta, String nomeArquivo) {
        String caminhoArquivo = pasta
                + nomeArquivo;

        // Monta o caminho do arquivo e verifica se ele existe
        Path caminho = getPath(caminhoArquivo);
        if (!Files.exists(caminho)) {
            throw new PontoException("Arquivo não existe.");
        }

        return caminho;
    }

    public static void moverArquivo(Path origem, Path destino) throws IOException {
        Files.move(origem, destino);
    }
}
